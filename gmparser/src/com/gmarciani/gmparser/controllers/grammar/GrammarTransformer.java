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
import com.gmarciani.gmparser.models.grammar.pattern.ProductionPattern;
import com.gmarciani.gmparser.models.grammar.pattern.ProductionPatternBuilder;
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
	
	@SuppressWarnings("static-access")
	public static void removeUngenerativeSymbols(Grammar grammar) {		
		//Vt
		Alphabet originaryTerminals = grammar.getTerminals();
		//Vn
		Alphabet originaryNonTerminals = grammar.getNonTerminals();
		
		//Vn'={}
		Alphabet acceptedNonTerminals = new Alphabet();
		
		//(Vt U Vn')
		Alphabet acceptedAlphabet = new Alphabet();
		acceptedAlphabet.addAll(originaryTerminals);
		acceptedAlphabet.addAll(acceptedNonTerminals);
		
		boolean loop = true;
		while(loop) {
			loop = false;		
			
			acceptedAlphabet.addAll(acceptedNonTerminals);
			
			ProductionPattern pattern = ProductionPatternBuilder
					.hasPattern("A->B*.")
					.withItem('A', originaryNonTerminals)
					.withItem('B', acceptedAlphabet)
					.create();
			
			Productions productions = grammar.getProductions();
			
			for (Production production : productions) {
				if (pattern.match(production)) {
					Character acceptedNonTerminal = pattern.extract('A', production).toCharArray()[0];
					acceptedNonTerminals.add(acceptedNonTerminal);
					loop = true;
				}					
			}			
		}		
		
		Productions acceptedProductions = grammar.getProductions().getProductionsWithSymbolsIn(acceptedAlphabet);
		grammar.getProductions().retainAll(acceptedProductions);
	}
	
	@SuppressWarnings("static-access")
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
			System.out.println("inside loop");
			
			ProductionPattern patternOne = ProductionPatternBuilder
					.hasPattern("A->B*CD*.")
					.withItem('A', acceptedNonTerminals)
					.withItem('B', originaryAlphabet)
					.withItem('C', originaryNonTerminals)
					.withItem('D', originaryAlphabet)
					.create();
			
			ProductionPattern patternTwo = ProductionPatternBuilder
					.hasPattern("A->B*CD*.")
					.withItem('A', acceptedNonTerminals)
					.withItem('B', originaryAlphabet)
					.withItem('C', originaryTerminals)
					.withItem('D', originaryAlphabet)
					.create();
			
			Productions productions = grammar.getProductions();
			
			for (Production production : productions) {						
				
				if (patternOne.match(production)) {
					System.out.println(production + " match pattern " + patternOne);
					Character acceptedNonTerminal = patternOne.extract('C', production).toCharArray()[0];
					System.out.println("\textracted symbol: " + acceptedNonTerminal);
					acceptedNonTerminals.add(acceptedNonTerminal);
					loop = true;
				}
				
				if (patternTwo.match(production)) {
					Character acceptedTerminal = patternTwo.extract('C', production).toCharArray()[0];
					acceptedTerminals.add(acceptedTerminal);
					loop = true;
				}
			}
		}		
		
		Alphabet acceptedAlphabet = new Alphabet();
		acceptedAlphabet.addAll(acceptedTerminals);
		acceptedAlphabet.addAll(acceptedNonTerminals);
		
		Productions acceptedProductions = grammar.getProductions().getProductionsWithSymbolsIn(acceptedAlphabet);
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
