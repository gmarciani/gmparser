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

import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.production.Member;
import com.gmarciani.gmparser.models.grammar.production.Production;
import com.gmarciani.gmparser.models.grammar.production.Productions;

/**
 * <p>Grammar factory model.<p>
 * <p>The grammar factory provides useful methods for correct grammar creation.<p>
 * 
 * @see com.gmarciani.gmparser.models.grammar.Grammar
 * @see com.gmarciani.gmparser.models.grammar.factory.ProductionFactory
 * @author Giacomo Marciani
 * @version 1.0
 */
public class GrammarFactory {
	
	private static GrammarFactory instance;
	
	private Productions grammarProductions;
	private Character axiom;
	private Character epsilon;
	
	/**
	 * Initializes the grammar factory.
	 */
	private GrammarFactory() {
		this.grammarProductions = new Productions();
		this.axiom = Grammar.AXIOM;
		this.epsilon = Grammar.EPSILON;
	}
	
	/**
	 * Returns the instance of the grammar factory singleton.
	 * 
	 * @return the instance of the factory singleton.
	 */
	public static GrammarFactory getInstance() {
		if (instance == null)
			instance = new GrammarFactory();		
		return instance;
	}
	
	/**
	 * Reinitializes the grammar factory.
	 */
	private void reset() {
		this.grammarProductions.clear();
		this.axiom = Grammar.AXIOM;
		this.epsilon = Grammar.EPSILON;
	}
	
	/**
	 * Adds to the grammar the specified set of productions.
	 * 
	 * @param productions the set of productions.
	 * 
	 * @return the instance of the factory singleton.
	 */
	public GrammarFactory hasProductions(Productions productions) {
		Productions created = ProductionFactory.getInstance()
				.hasProductions(productions)
				.create();		
		for (Production prod : created)
			this.grammarProductions.add(prod);		
		return instance;
	}
	
	/**
	 * Adds to the grammar the specified production.
	 * 
	 * @param production the production.
	 * 
	 * @return the instance of the factory singleton.
	 */
	public GrammarFactory hasProduction(Production production) {
		Productions created = ProductionFactory.getInstance()
				.hasProduction(production)
				.create();		
		for (Production prod : created)
			this.grammarProductions.add(prod);		
		return instance;
	}
	
	/**
	 * Adds to the grammar the specified production, by its LHS/RHS members.
	 * 
	 * @param left the LHS production member.
	 * @param right the RHS production member.
	 * 
	 * @return the instance of the factory singleton.
	 */
	public GrammarFactory hasProduction(Member left, Member right) {
		Productions created = ProductionFactory.getInstance()
				.hasProduction(left, right)
				.create();			
		for (Production prod : created)
			this.grammarProductions.add(prod);			
		return instance;
	}	
	
	/**
	 * Adds to the grammar the specified production, by its LHS/RHS member, represented as a string.
	 * 
	 * @param left the LHS production member, represented as a string.
	 * @param right the RHS production member, represented as a string.
	 * 
	 * @return the instance of the factory singleton.
	 */
	public GrammarFactory hasProduction(String left, String right) {
		Productions created = ProductionFactory.getInstance()
				.hasProduction(left, right)
				.create();		
		for (Production prod : created)
			this.grammarProductions.add(prod);		
		return instance;
	}	
	
	/**
	 * Adds to the grammar the specified set of productions, represented as a string.
	 * 
	 * @param productions the set of productions, represented as a string.
	 * 
	 * @return the instance of the factory singleton.
	 */
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
	
	/**
	 * Adds to the grammar the specified set of productions, represented as a string, with the specified separators/markers.
	 * 
	 * @param productions the set of productions, represented as a string.
	 * @param memberSeparator the LHS/RHS production member separator.
	 * @param infixSeparator the production for same non terminal symbol separator.
	 * @param productionSeparator the production separator.
	 * @param productionEnder the end marker for the set of productions.
	 * 
	 * @return the instance of the factory singleton.
	 */
	public GrammarFactory hasProductions(String productions, String memberSeparator, String infixSeparator, String productionSeparator, String productionEnder) {
		Productions created = ProductionFactory.getInstance()
				.hasProductions(productions, memberSeparator, infixSeparator, productionSeparator, productionEnder)
				.create();		
		for (Production prod : created)
			this.grammarProductions.add(prod);		
		return instance;
	}	
	
	/**
	 * Sets the grammar axiom.
	 * 
	 * @param axiom the axiom of the grammar.
	 * 
	 * @return the instance of the factory singleton.
	 */
	public GrammarFactory withAxiom(Character axiom) {
		this.axiom = axiom;		
		return instance;
	}
	
	/**
	 * Sets the grammar epsilon representation.
	 * 
	 * @param epsilon the epsilon representation of the grammar.
	 * 
	 * @return the instance of the factory singleton.
	 */
	public GrammarFactory withEpsilon(Character epsilon) {
		this.epsilon = epsilon;		
		return instance;
	}
	
	/**
	 * Returns the new grammar.
	 * 
	 * @return the new grammar.
	 */
	public Grammar create() {
		Grammar grammar = new Grammar(this.axiom, this.epsilon);		
		for (Production production : this.grammarProductions)
			grammar.addProduction(production);
		reset();		
		return grammar;
	}

}
