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

package com.gmarciani.gmparser.models.grammar.factory;

import java.util.regex.Pattern;

import com.gmarciani.gmparser.models.grammar.production.Member;
import com.gmarciani.gmparser.models.grammar.production.Production;
import com.gmarciani.gmparser.models.grammar.production.Productions;

/**
 * <p>Produciton factory model.<p>
 * <p>The production factory provides useful methods for correct production creation.<p>
 * 
 * @see com.gmarciani.gmparser.models.grammar.production.Productions
 * @see com.gmarciani.gmparser.models.grammar.production.Production
 * @see com.gmarciani.gmparser.models.grammar.factory.GrammarFactory
 * 
 * @author Giacomo Marciani
 * @version 1.0
 */
public class ProductionFactory {

	private static ProductionFactory instance;
	
	private Productions productions;
	
	/**
	 * Initializes the productions factory.
	 */
	private ProductionFactory() {
		this.productions = new Productions();
	}
	
	/**
	 * Returns the instance of the factory singleton.
	 * 
	 * @return the instance of the factory singleton.
	 */
	public static ProductionFactory getInstance() {
		if (instance == null)
			instance = new ProductionFactory();
		return instance;
	}
	
	/**
	 * Reinitializes the production factory.
	 */
	private void reset() {
		this.productions.clear();
	}
	
	/**
	 * Adds to the set of productions the specified set of productions.
	 * 
	 * @param productions the set of productions.
	 * 
	 * @return the instance of the factory singleton.
	 */
	public ProductionFactory hasProductions(Productions productions) {
		for (Production production : productions)
			hasProduction(production);
		return instance;
	}
	
	/**
	 * Adds to the set of productions the specified production.
	 * 
	 * @param production the production.
	 * 
	 * @return the instance of the factory singleton.
	 */
	public ProductionFactory hasProduction(Production production) {
		this.productions.add(production);		
		return instance;
	}
	
	/**
	 * Adds to the set of productions the specified production, by its LHS/RHS members.
	 * 
	 * @param left the LHS production member.
	 * @param right the RHS production member.
	 * 
	 * @return the instance of the factory singleton.
	 */
	public ProductionFactory hasProduction(Member left, Member right) {
		Production production = new Production(left, right);
		this.productions.add(production);
		return instance;
	}	
	
	/**
	 * Adds to the set of productions the specified production, by its LHS/RHS members, represented as a string.
	 * 
	 * @param left the LHS production member, represented as a string.
	 * @param right the RHS production member, represented as a string.
	 * 
	 * @return the instance of the factory singleton.
	 */
	public ProductionFactory hasProduction(String left, String right) {
		Production production = new Production(new Member(left), new Member(right));
		this.productions.add(production);
		return instance;
	}	
	
	/**
	 * Adds to the set of productions the specified set of productions, represented as a string.
	 * 
	 * @param productions the set of productions, represented as a string.
	 * 
	 * @return the instance of the factory singleton.
	 */
	public ProductionFactory hasProductions(String productions) {
		hasProductions(productions, Productions.MEMBER_SEPARATOR, Productions.INFIX_SEPARATOR, Productions.PRODUCTION_SEPARATOR, Productions.PRODUCTION_ENDER);
		return instance;
	}
	
	/**
	 * Adds to the set of productions the specified set of productions, represented as a string, with the specified separators/markers.
	 * 
	 * @param productions the set of productions, represented as a string.
	 * @param memberSeparator the LHS/RHS production member separator.
	 * @param infixSeparator the production for the same non terminal symbol separator.
	 * @param productionSeparator the production for different non terminal symbol separator.
	 * @param productionsEnder the end marker for the set of productions.
	 * 
	 * @return the instance of the factory singleton.
	 */
	public ProductionFactory hasProductions(String productions, String memberSeparator, String infixSeparator, String productionSeparator, String productionsEnder) {
		String prodsArray[] = productions.split(Pattern.quote(productionsEnder));
		String prods = prodsArray[0];
		hasProductionsAsString(prods, memberSeparator, infixSeparator, productionSeparator);		
		return instance;
	}
	
	/**
	 * Adds to the set of productions the specified set of productions, represented as a string, with the specified separators/markers.
	 * 
	 * @param productions the set of productions, represented as a string.
	 * @param memberSeparator the LHS/RHS production member separator.
	 * @param infixSeparator the production for the same non terminal symbol separator.
	 * @param productionSeparator the production for different non terminal symbol separator.
	 * 
	 * @return the instance of the factory singleton.
	 */
	private ProductionFactory hasProductionsAsString(String productions, String memberSeparator, String infixSeparator, String productionSeparator) {
		String productionsAsArray[] = productions.split(Pattern.quote(productionSeparator));			
		for (String productionsSameNonTerminal : productionsAsArray)
			hasProductionsAsString(productionsSameNonTerminal, memberSeparator, infixSeparator);			
		return instance;
	}
	
	/**
	 * Adds to the set of productions the specified set of productions, represented as a string, with the specified separators/markers.
	 * 
	 * @param productions the set of productions, represented as a string.
	 * @param memberSeparator the LHS/RHS production member separator.
	 * @param infixSeparator the production for the same non terminal symbol separator.
	 * 
	 * @return the instance of the factory singleton.
	 */
	private ProductionFactory hasProductionsAsString(String productions, String memberSeparator, String infixSeparator) {
		String productionsAsArray[] = productions.split(Pattern.quote(memberSeparator));		
		String left = productionsAsArray[0];
		String rights[] = productionsAsArray[1].split(Pattern.quote(infixSeparator));			
		for (String right : rights)
			hasProductionAsString(left + memberSeparator + right, memberSeparator);
		return instance;
	}
	
	/**
	 * Adds to the set of productions the specified set of productions, represented as a string, with the specified separators/markers.
	 * 
	 * @param productions the set of productions, represented as a string.
	 * @param memberSeparator the LHS/RHS production member separator.
	 * 
	 * @return the instance of the factory singleton.
	 */
	private ProductionFactory hasProductionAsString(String productions, String memberSeparator) {
		String productionAsArray[] = productions.split(Pattern.quote(memberSeparator));
		String left = productionAsArray[0];
		String right = productionAsArray[1];
		hasProduction(left, right);		
		return instance;
	}	
	
	/**
	 * Returns the new set of production.
	 * 
	 * @return the new set of production.
	 */
	public Productions create() {
		Productions target = new Productions();		
		for (Production production : this.productions)
			target.add(production);		
		reset();		
		return target;
	}

}
