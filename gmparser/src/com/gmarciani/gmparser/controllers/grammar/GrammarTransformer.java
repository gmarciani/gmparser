/*	The MIT License (MIT)
 *
 *	Copyright (c) 2014 Giacomo Marciani
 *	
 *	Permission is hereby granted, free of charge, to any person obtaining a copy
 *	of this software and associated documentation files (the "Software"), to deal
 *	in the Software without restriction, including without limitation the rights
 *	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *	copies of the Software, and to permit persons to whom the Software is
 *	furnished to do so, subject to the following conditions:
 *	
 *	The above copyright notice and this permission notice shall be included in all
 *	copies or substantial portions of the Software.
 *	
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *	SOFTWARE.
*/

package com.gmarciani.gmparser.controllers.grammar;

import com.gmarciani.gmparser.controllers.ui.Listener;
import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.GrammarForm;
import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;
import com.gmarciani.gmparser.models.grammar.production.Production;
import com.gmarciani.gmparser.models.grammar.production.Productions;

public class GrammarTransformer {
	
	@SuppressWarnings("unused")
	private static Listener output;	
	
	public static void setOutput(Listener listener) {
		output = listener;
	}

	public static Grammar transform(Grammar grammar, GrammarForm grammarForm) {
		return null;
	}
	
	public static void removeUngenerativeSymbols(Grammar grammar) {	
		
		//Vn'={}
		Alphabet acceptedNonTerminals = new Alphabet();
		
		//(Vt U Vn')
		Alphabet acceptedAlphabet = new Alphabet();
		acceptedAlphabet.addAll(grammar.getTerminals());
		acceptedAlphabet.addAll(acceptedNonTerminals);
		
		boolean loop = true;
		while(loop) {
			loop = false;		
			
			acceptedAlphabet.addAll(acceptedNonTerminals);
			
			Productions productions = grammar.getProductions();
			
			for (Production production : productions) {
				if (production.isRightWithin(acceptedAlphabet)) {
					loop = acceptedNonTerminals.add(production.getLeft());
				}		
			}			
		}		
		
		Productions acceptedProductions = grammar.getProductions().getProductionsWithin(acceptedAlphabet);
		grammar.getProductions().retainAll(acceptedProductions);
	}
	
	public static void removeUnreacheableSymbols(Grammar grammar) {
		//Vt
		Alphabet originaryTerminals = grammar.getTerminals();
		//Vn
		Alphabet originaryNonTerminals = grammar.getNonTerminals();
		
		//Vt'={}
		Alphabet acceptedTerminals = new Alphabet();
		//Vn'={S}
		Alphabet acceptedNonTerminals = new Alphabet();	
		acceptedNonTerminals.add(grammar.getAxiom());
		
		//(Vt U Vn)
		Alphabet originaryAlphabet = new Alphabet();
		originaryAlphabet.addAll(originaryTerminals);
		originaryAlphabet.addAll(originaryNonTerminals);		
		
		boolean loop = true;
		while(loop) {
			loop = false;
			
			Productions productions = grammar.getProductions();
			
			for (Production production : productions) {	
				if (production.isLeftWithin(acceptedNonTerminals)
						&& acceptedNonTerminals.containsAll(production.getRightNonTerminals())) {
					loop = acceptedNonTerminals.add(production.getRightNonTerminals());
				}
				
				if (production.isLeftWithin(acceptedNonTerminals)) {
					acceptedTerminals.add(production.getRightTerminals());
				}
			}
		}		
		
		Alphabet acceptedAlphabet = new Alphabet();
		acceptedAlphabet.addAll(acceptedTerminals);
		acceptedAlphabet.addAll(acceptedNonTerminals);
		
		Productions acceptedProductions = grammar.getProductions().getProductionsWithin(acceptedAlphabet);
		grammar.getProductions().retainAll(acceptedProductions);
	}
	
	public static void removeUselessSymbols(Grammar grammar) {
		removeUngenerativeSymbols(grammar);
		removeUnreacheableSymbols(grammar);
	}
	
	public static void removeEpsilonProductions(Grammar grammar) {
		//
		removeUselessSymbols(grammar);
	}
	
	public static void removeUnitProductions(Grammar grammar) {
		removeEpsilonProductions(grammar);
		//
		removeUselessSymbols(grammar);
	}	
	
	public static void removeLeftRecursion(Grammar grammar) {
		//
		removeUnitProductions(grammar);
	}

}
