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

import java.util.regex.Pattern;

public class GrammarBuilder {
	
	private static GrammarBuilder instance = new GrammarBuilder();
	
	private static Productions productions;	
	private static Character axiom;
	private static String empty;
	
	private GrammarBuilder() {
		axiom = Grammar.AXIOM;
		empty = Grammar.EMPTY_STRING;
		productions = new Productions();
	}
	
	private static void reset() {
		axiom = Grammar.AXIOM;
		empty = Grammar.EMPTY_STRING;
		productions.clear();
	}
	
	public static GrammarBuilder hasProduction(Production production) {
		productions.add(production);
		
		return instance;
	}
	
	public static GrammarBuilder hasProduction(String left, String right) {
		Production production = new Production(left, right);
		productions.add(production);

		return instance;
	}	
	
	//A->Aa
	public static GrammarBuilder hasProductionAsString(String productions, String memberSeparator) {
		String productionAsArray[] = productions.split(Pattern.quote(memberSeparator));
		String left = productionAsArray[0];
		String right = productionAsArray[1];
		hasProduction(left, right);
		
		return instance;
	}
	
	//A->Aa|Ba
	public static GrammarBuilder hasProductionsAsString(String productions, String memberSeparator, String infixSeparator) {
		String productionsAsArray[] = productions.split(Pattern.quote(memberSeparator));		
		String left = productionsAsArray[0];
		String rights[] = productionsAsArray[1].split(Pattern.quote(infixSeparator));
		
		for (String right : rights) {
			hasProductionAsString(left + memberSeparator + right, memberSeparator);
		}		
		
		return instance;
	}
	
	//A->Aa|Ba;B->Bb|b
	public static GrammarBuilder hasProductionsAsString(String productions, String memberSeparator, String infixSeparator, String productionSeparator) {
		String productionsAsArray[] = productions.split(Pattern.quote(productionSeparator));
		
		for (String productionsSameNonTerminal : productionsAsArray) {
			hasProductionsAsString(productionsSameNonTerminal, memberSeparator, infixSeparator);
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
		Grammar grammar = new Grammar(axiom, empty);
		
		for (Production production : productions) {
			grammar.addProduction(production);
		}
		
		reset();
		
		return grammar;
	}

}
