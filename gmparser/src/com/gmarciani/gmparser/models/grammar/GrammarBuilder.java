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

import com.gmarciani.gmparser.models.grammar.production.Production;
import com.gmarciani.gmparser.models.grammar.production.Productions;
import com.gmarciani.gmparser.models.grammar.production.ProductionsBuilder;


public class GrammarBuilder {
	
	private static GrammarBuilder instance = new GrammarBuilder();
	
	private static Productions grammarProductions;
	private static Character axiom;
	private static String empty;
	
	private GrammarBuilder() {
		grammarProductions = new Productions();
		axiom = Grammar.AXIOM;
		empty = Grammar.EMPTY;
	}
	
	private static void reset() {
		grammarProductions.clear();
		axiom = Grammar.AXIOM;
		empty = Grammar.EMPTY;
	}
	
	//[(S,Aa),(A,a)]
	@SuppressWarnings("static-access")
	public static GrammarBuilder hasProductions(Productions productions) {
		Productions created = ProductionsBuilder
				.hasProductions(productions)
				.create();
		
		for (Production prod : created)
			grammarProductions.add(prod);
		
		return instance;
	}
	
	//(S,Aa)
	@SuppressWarnings("static-access")
	public static GrammarBuilder hasProduction(Production production) {
		Productions created = ProductionsBuilder
				.hasProduction(production)
				.create();
		
		for (Production prod : created)
			grammarProductions.add(prod);
		
		return instance;
	}
	
	//(S,Aa)
	@SuppressWarnings("static-access")
	public static GrammarBuilder hasProduction(Character left, String right) {
		Productions created = ProductionsBuilder
				.hasProduction(left, right)
				.create();
		
		for (Production prod : created)
			grammarProductions.add(prod);
		
		return instance;
	}	
	
	//S->Aa|a;A->a. (default separators)
	@SuppressWarnings("static-access")
	public static GrammarBuilder hasProductions(String productions) {
		Productions created = ProductionsBuilder
				.hasProductions(productions)
				.create();
		
		for (Production prod : created)
			grammarProductions.add(prod);
		
		return instance;
	}
	
	//S->Aa|a;A->a.
	@SuppressWarnings("static-access")
	public static GrammarBuilder hasProductions(String productions, String memberSeparator, String infixSeparator, String productionSeparator, String productionEnder) {
		Productions created = ProductionsBuilder
				.hasProductions(productions, memberSeparator, infixSeparator, productionSeparator, productionEnder)
				.create();
		
		for (Production prod : created)
			grammarProductions.add(prod);
		
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
		
		for (Production production : grammarProductions) {
			grammar.addProduction(production);
		}
		
		reset();
		
		return grammar;
	}

}
