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

import java.util.LinkedHashSet;
import java.util.Set;

import com.gmarciani.gmparser.controllers.ui.Listener;
import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.GrammarForm;
import com.gmarciani.gmparser.models.grammar.PatternBuilder;
import com.gmarciani.gmparser.models.grammar.Production;
import com.gmarciani.gmparser.models.grammar.ProductionPattern;
import com.gmarciani.gmparser.models.grammar.ProductionPatternBuilder;

public class GrammarTransformer {
	
	private static Listener output;	
	
	public static void setOutput(Listener listener) {
		output = listener;
	}

	public static Grammar transform(Grammar grammar, GrammarForm grammarForm) {
		return null;
	}
	
	@SuppressWarnings("static-access")
	public static void removeUngenerativeSymbols(Grammar grammar) {
		Set<Character> terminals = grammar.getTerminals();
		Set<Character> nonTerminals = grammar.getTerminals();
		
		Set<Character> acceptedNonTerminals = new LinkedHashSet<Character>();
		Set<Character> acceptedAlphabet = new LinkedHashSet<Character>();
		acceptedAlphabet.addAll(terminals);
		
		boolean loop = true;
		while(loop) {
			loop = false;		
			
			acceptedAlphabet.addAll(acceptedNonTerminals);
			
			ProductionPattern pattern = ProductionPatternBuilder.hasPatternAsString("A->a*", "->")
					.withItem('A', nonTerminals)
					.withItem('a', acceptedAlphabet)
					.create();
			
			for (Production production : grammar.getProductions()) {
				if (pattern.match(production)) {
					Character acceptedNonTerminal = pattern.extract('A', production);
					acceptedNonTerminals.add(acceptedNonTerminal);
					loop = true;
					break;
				}					
			}			
		}		
		
		Set<Production> newProductions = grammar.getProductions().getProductionsWithSymbolsIn(acceptedAlphabet);
		grammar.getProductions().restrictTo(newProductions);
	}
	
	@SuppressWarnings("static-access")
	public static void removeUnreacheableSymbols(Grammar grammar) {
		Set<Character> terminals = grammar.getTerminals();
		Set<Character> nonTerminals = grammar.getNonTerminals();
		
		Set<Character> alphabet = new LinkedHashSet<Character>();
		alphabet.addAll(terminals);
		alphabet.addAll(nonTerminals);
		
		Set<Character> acceptedTerminals = new LinkedHashSet<Character>();
		Set<Character> acceptedNonTerminals = new LinkedHashSet<Character>(grammar.getAxiom());	
		
		boolean loop = true;
		while(loop) {
			loop = false;
			
			for (Production production : grammar.getProductions()) {
				ProductionPattern patternOne = ProductionPatternBuilder.hasPatternAsString("A->B*CD*", "->")
						.withItem('A', acceptedNonTerminals)
						.withItem('B', alphabet)
						.withItem('C', nonTerminals)
						.withItem('D', alphabet)
						.create();
				
				ProductionPattern patternTwo = ProductionPatternBuilder.hasPatternAsString("A->B*CD*", "->")
						.withItem('A', acceptedNonTerminals)
						.withItem('B', alphabet)
						.withItem('C', terminals)
						.withItem('D', alphabet)
						.create();
				
				if (patternOne.match(production)) {
					Character acceptedNonTerminal = patternOne.extract('C', production);
					acceptedNonTerminals.add(acceptedNonTerminal);
					loop = true;
					break;
				}
				
				if (patternTwo.match(production)) {
					Character acceptedTerminal = patternTwo.extract('C', production);
					acceptedTerminals.add(acceptedTerminal);
					loop = true;
					break;
				}
			}
		}		
		
		Set<Character> acceptedSymbols = new LinkedHashSet<Character>();
		acceptedSymbols.addAll(acceptedTerminals);
		acceptedSymbols.addAll(acceptedNonTerminals);
		
		Set<Production> newProductions = grammar.getProductions().getProductionsWithSymbolsIn(acceptedSymbols);
		grammar.getProductions().restrictTo(newProductions);
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
