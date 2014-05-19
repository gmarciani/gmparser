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

package com.gmarciani.gmparser.models.grammar;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class GrammarBuilder {
	
	private static GrammarBuilder instance = new GrammarBuilder();
	
	private static Map<Character, Set<String>> productions;	
	private static Character axiom;
	private static String empty;
	
	private GrammarBuilder() {
		axiom = Grammar.DEFAULT_AXIOM;
		empty = Grammar.DEFAULT_EMPTY_STRING;
		productions = new HashMap<Character, Set<String>>();
	}
	
	private static void reset() {
		axiom = Grammar.DEFAULT_AXIOM;
		empty = Grammar.DEFAULT_EMPTY_STRING;
		productions.clear();
	}
	
	public static GrammarBuilder hasProduction(Character nonTerminalSymbol, String sentential) {
		Set<String> sententials = productions.get(nonTerminalSymbol);
		if (sententials == null) {
			productions.put(nonTerminalSymbol, new LinkedHashSet<String>());
		}
		
		productions.get(nonTerminalSymbol).add(sentential);
		return instance;
	}
	
	//A->Aa
	public static GrammarBuilder hasProduction(String production, String memberSeparator) {
		String productionAsArray[] = production.split(memberSeparator);
		Character nonTerminalSymbol = productionAsArray[0].charAt(0);
		String sentential = productionAsArray[1];
		hasProduction(nonTerminalSymbol, sentential);
		
		return instance;
	}
	
	//A->Aa|Ba
	public static GrammarBuilder hasProductions(String productions, String memberSeparator, String infixSeparator) {
		String productionsAsArray[] = productions.split(memberSeparator);		
		Character nonTerminalSymbol = productionsAsArray[0].charAt(0);
		String sententials[] = productionsAsArray[1].split(infixSeparator);
		
		for (String sentential : sententials) {
			hasProduction(nonTerminalSymbol + memberSeparator + sentential, memberSeparator);
		}		
		
		return instance;
	}
	
	//A->Aa|Ba;B->Bb|b
	public static GrammarBuilder hasProductions(String productions, String memberSeparator, String infixSeparator, String productionSeparator) {
		String productionsDiff[] = productions.split(productionSeparator);
		
		for (String productionsSame : productionsDiff) {
			hasProductions(productionsSame, memberSeparator, infixSeparator);
		}
		
		return instance;
	}
	
	public static GrammarBuilder withAxiom(Character symbol) {
		axiom = symbol;
		return instance;
	}
	
	public static GrammarBuilder withEmpty(String emptyString) {
		empty = emptyString;
		return instance;
	}
	
	public static Grammar create() {
		Grammar grammar = new Grammar();
		grammar.setAxiom(axiom);
		grammar.setEmpty(empty);
		
		for (Map.Entry<Character, Set<String>> production : productions.entrySet()) {
			Character nonTerminalSymbol = production.getKey();
			grammar.addNonTerminalSymbol(nonTerminalSymbol);
			
			if (!grammar.getProductions().containsKey(nonTerminalSymbol)) {
				grammar.getProductions().put(nonTerminalSymbol, new LinkedHashSet<String>());
			}
			
			for (String sentential : productions.get(nonTerminalSymbol)) {
				char[] symbols = sentential.toCharArray();
				for (char symbol : symbols) {
					if (Character.isUpperCase(symbol)) {
						grammar.addNonTerminalSymbol(symbol);
						if (!grammar.getProductions().containsKey(symbol)) {
							grammar.getProductions().put(symbol, new LinkedHashSet<String>());
						}
					} else {
						grammar.addTerminalSymbol(symbol);
					}
				}
				grammar.addProduction(nonTerminalSymbol, sentential);
			}
		}
		
		reset();
		
		return grammar;
	}

}