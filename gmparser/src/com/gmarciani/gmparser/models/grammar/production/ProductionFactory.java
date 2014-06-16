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

package com.gmarciani.gmparser.models.grammar.production;

import java.util.regex.Pattern;

/**
 * @author Giacomo Marciani
 * @version 1.0
 */
public class ProductionFactory {

	private static ProductionFactory instance;
	
	private Productions productions;
	
	private ProductionFactory() {
		this.productions = new Productions();
	}
	
	public static ProductionFactory getInstance() {
		if (instance == null) {
			instance = new ProductionFactory();
		}
		
		return instance;
	}
	
	private void reset() {
		this.productions.clear();
	}
	
	//[(S,Aa),(A,a)]
	public ProductionFactory hasProductions(Productions productions) {
		for (Production production : productions) {
			hasProduction(production);
		}
		
		return instance;
	}
	
	//(S,Aa)
	public ProductionFactory hasProduction(Production production) {
		this.productions.add(production);
		
		return instance;
	}
	
	//(S,Aa)
	public ProductionFactory hasProduction(Member left, Member right) {
		Production production = new Production(left, right);
		this.productions.add(production);

		return instance;
	}	
	
	//(S,Aa)
	public ProductionFactory hasProduction(String left, String right) {
		Production production = new Production(new Member(left), new Member(right));
		this.productions.add(production);

		return instance;
	}	
	
	//S->Aa|a;A->a. (default separators)
	public ProductionFactory hasProductions(String productions) {
		hasProductions(productions, Productions.MEMBER_SEPARATOR, Productions.INFIX_SEPARATOR, Productions.PRODUCTION_SEPARATOR, Productions.PRODUCTION_ENDER);
		return instance;
	}
	
	//S->Aa|a;A->a.
	public ProductionFactory hasProductions(String productions, String memberSeparator, String infixSeparator, String productionSeparator, String productionsEnder) {
		String prodsArray[] = productions.split(Pattern.quote(productionsEnder));
		String prods = prodsArray[0];
		hasProductionsAsString(prods, memberSeparator, infixSeparator, productionSeparator);
		
		return instance;
	}
	
	//S->Aa|a;A->a
	private ProductionFactory hasProductionsAsString(String productions, String memberSeparator, String infixSeparator, String productionSeparator) {
		String productionsAsArray[] = productions.split(Pattern.quote(productionSeparator));
			
		for (String productionsSameNonTerminal : productionsAsArray) {
			hasProductionsAsString(productionsSameNonTerminal, memberSeparator, infixSeparator);
		}
			
		return instance;
	}
	
	//S->Aa|a
	private ProductionFactory hasProductionsAsString(String productions, String memberSeparator, String infixSeparator) {
		String productionsAsArray[] = productions.split(Pattern.quote(memberSeparator));		
		String left = productionsAsArray[0];
		String rights[] = productionsAsArray[1].split(Pattern.quote(infixSeparator));
			
		for (String right : rights) {
			hasProductionAsString(left + memberSeparator + right, memberSeparator);
		}		
			
		return instance;
	}
	
	//S->Aa
	private ProductionFactory hasProductionAsString(String productions, String memberSeparator) {
		String productionAsArray[] = productions.split(Pattern.quote(memberSeparator));
		String left = productionAsArray[0];
		String right = productionAsArray[1];
		hasProduction(left, right);
		
		return instance;
	}	
	
	public Productions create() {
		Productions target = new Productions();
		
		for (Production production : this.productions)
			target.add(production);
		
		reset();
		
		return target;
	}

}
