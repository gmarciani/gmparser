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

import com.gmarciani.gmparser.models.grammar.production.Member;
import com.gmarciani.gmparser.models.grammar.production.Production;
import com.gmarciani.gmparser.models.grammar.production.Productions;
import com.gmarciani.gmparser.models.grammar.production.ProductionFactory;


public class GrammarFactory {
	
	private static GrammarFactory instance;
	
	private Productions grammarProductions;
	private Character axiom;
	private Character epsilon;
	
	private GrammarFactory() {
		this.grammarProductions = new Productions();
		this.axiom = Grammar.AXIOM;
		this.epsilon = Grammar.EPSILON;
	}
	
	public static GrammarFactory getInstance() {
		if (instance == null) {
			instance = new GrammarFactory();
		}
		
		return instance;
	}
	
	private void reset() {
		this.grammarProductions.clear();
		this.axiom = Grammar.AXIOM;
		this.epsilon = Grammar.EPSILON;
	}
	
	//[(S,Aa),(A,a)]
	public GrammarFactory hasProductions(Productions productions) {
		Productions created = ProductionFactory.getInstance()
				.hasProductions(productions)
				.create();
		
		for (Production prod : created)
			this.grammarProductions.add(prod);
		
		return instance;
	}
	
	//(S,Aa)
	public GrammarFactory hasProduction(Production production) {
		Productions created = ProductionFactory.getInstance()
				.hasProduction(production)
				.create();
		
		for (Production prod : created)
			this.grammarProductions.add(prod);
		
		return instance;
	}
	
	//(S,Aa)
	public GrammarFactory hasProduction(Member left, Member right) {
		Productions created = ProductionFactory.getInstance()
				.hasProduction(left, right)
				.create();
			
		for (Production prod : created)
			this.grammarProductions.add(prod);
			
		return instance;
	}	
	
	//(S,Aa)
	public GrammarFactory hasProduction(String left, String right) {
		Productions created = ProductionFactory.getInstance()
				.hasProduction(left, right)
				.create();
		
		for (Production prod : created)
			this.grammarProductions.add(prod);
		
		return instance;
	}	
	
	//S->Aa|a;A->a. (default separators)
	public GrammarFactory hasProductions(String productions) {
		Productions created = ProductionFactory.getInstance()
				.hasProductions(productions)
				.create();
		
		for (Production prod : created)
			this.grammarProductions.add(prod);
		
		Character axiom = productions.charAt(0);
		this.withAxiom(axiom);
		
		return instance;
	}
	
	//S->Aa|a;A->a.
	public GrammarFactory hasProductions(String productions, String memberSeparator, String infixSeparator, String productionSeparator, String productionEnder) {
		Productions created = ProductionFactory.getInstance()
				.hasProductions(productions, memberSeparator, infixSeparator, productionSeparator, productionEnder)
				.create();
		
		for (Production prod : created)
			this.grammarProductions.add(prod);
		
		return instance;
	}	
	
	public GrammarFactory withAxiom(Character axiom) {
		this.axiom = axiom;
		
		return instance;
	}
	
	public GrammarFactory withEpsilon(Character epsilon) {
		this.epsilon = epsilon;
		
		return instance;
	}
	
	public Grammar create() {
		Grammar grammar = new Grammar(this.axiom, this.epsilon);
		
		for (Production production : this.grammarProductions) {
			grammar.addProduction(production);
		}
		
		reset();
		
		return grammar;
	}

}
