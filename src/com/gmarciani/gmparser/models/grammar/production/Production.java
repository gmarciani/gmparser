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

import java.util.Objects;

import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;
import com.gmarciani.gmparser.models.grammar.analysis.Type;

/**
 * <p>Production model.<p>
 * <p>A production is a pair of members: a LHS member, and a RHS member.<p>
 * 
 * @see com.gmarciani.gmparser.models.grammar.Grammar
 * @see com.gmarciani.gmparser.models.grammar.production.Productions
 * @see com.gmarciani.gmparser.models.grammar.production.Member
 * 
 * @author Giacomo Marciani
 * @version 1.0
 */
public class Production implements Comparable<Production> {
	
	private Member left;
	private Member right;
	
	public static final String MEMBER_SEPARATOR = "->";	
	
	/**
	 * Creates a new production with the specified LHS/RHS members.
	 * 
	 * @param left the LHS member.
	 * @param right the RHS member.
	 */
	public Production(Member left, Member right) {
		this.setLeft(left);
		this.setRight(right);
	}
	
	/**
	 * Creates a new production as a copy of the specified production.
	 * 
	 * @param production the production to copy.
	 */
	public Production(Production production) {
		this.setLeft(production.getLeft());
		this.setRight(production.getRight());
	}

	/**
	 * Return the LHS member of the production.
	 * 
	 * @return the LHS member of the production.
	 */
	public Member getLeft() {
		return this.left;
	}

	/**
	 * Sets the LHS member of the production.
	 * 
	 * @param left the LHS member of the production.
	 */
	public void setLeft(Member left) {
		this.left = left;
	}

	/**
	 * Returns the RHS member of the production.
	 * 
	 * @return the RHS member of the production.
	 */
	public Member getRight() {
		return this.right;
	}

	/**
	 * Sets the RHS member of the production.
	 * 
	 * @param right the RHS member of the production.
	 */
	public void setRight(Member right) {
		this.right = right;
	}
	
	/**
	 * Returns the non terminal alphabet of the production.
	 * 
	 * @return the non terminal alphabet of the production.
	 */
	public Alphabet getNonTerminalAlphabet() {
		Alphabet target = new Alphabet();		
		target.addAll(this.getLeft().getNonTerminalAlphabet());
		target.addAll(this.getRight().getNonTerminalAlphabet());		
		return target;
	}
	
	/**
	 * Returns the terminal alphabet of the production.
	 * 
	 * @return the terminal alphabet of the production.
	 */
	public Alphabet getTerminalAlphabet() {
		Alphabet target = new Alphabet();		
		target.addAll(this.getLeft().getTerminalAlphabet());
		target.addAll(this.getRight().getTerminalAlphabet());		
		return target;
	}
	
	/**
	 * Checks if both the LHS and RHS members are within the specified alphabet.
	 * 
	 * @param alphabet the alphabet
	 * 
	 * @return true if both the LHS and RHS members are within the specified alphabet; false, otherwise.
	 */
	public boolean isWithin(Alphabet alphabet) {
		return (this.getLeft().isWithin(alphabet)
				&& this.getRight().isWithin(alphabet));
	}
	
	/**
	 * Checks if both the LHS and RHS members contain the specified alphabet.
	 * 
	 * @param alphabet the alphabet.
	 * 
	 * @return true if both the LHS and RHS members contain the specified alphabet; false, otherwise.
	 */
	public boolean isContaining(Alphabet alphabet) {
		return (this.getLeft().isContaining(alphabet)
				&& this.getRight().isContaining(alphabet));
	}
	
	/**
	 * Checks if both the LHS and RHS members contain the specified symbol.
	 * 
	 * @param symbol the symbol.
	 * 
	 * @return true if both the LHS and RHS members contain the specified symbol; false, otherwise.
	 */
	public boolean isContaining(Character symbol) {
		return (this.getLeft().isContaining(symbol)
				&& this.getRight().isContaining(symbol));
	}
	
	/**
	 * Returns the production type, with respect to the Chomsky hierarchy.
	 * 
	 * @param nonTerminals the non terminal alphabet.
	 * @param terminals the terminal alphabet.
	 * 
	 * @return the production type, with respect to the Chomsky hierarchy.
	 */
	public Type getType(Alphabet nonTerminals, Alphabet terminals) {
		if (this.isRegular(nonTerminals, terminals))
			return Type.REGULAR;		
		if (this.isContextFree(nonTerminals, terminals))
			return Type.CONTEXT_FREE;		
		if (this.isContextSensitive(nonTerminals, terminals))
			return Type.CONTEXT_SENSITIVE;		
		if (this.isUnrestricted(nonTerminals, terminals))
			return Type.UNRESTRICTED;		
		return Type.UNKNOWN;
	}
	
	/**
	 * Checks if the production is unrestricted.
	 * 
	 * @param nonTerminals the non terminal alphabet.
	 * @param terminals the terminal alphabet.
	 * 
	 * @return true if the production is unrestricted; false, otherwise.
	 */
	public boolean isUnrestricted(Alphabet nonTerminals, Alphabet terminals) {
		Alphabet acceptedAlphabet = new Alphabet(nonTerminals, terminals);
		return (this.getLeft().isWithin(acceptedAlphabet)
				&& (this.getRight().isWithin(acceptedAlphabet) || this.getRight().isEpsilon()));
	}

	/**
	 * Checks if the production is context-sensitive.
	 * 
	 * @param nonTerminals the non terminal alphabet.
	 * @param terminals the terminal alphabet.
	 * 
	 * @return true if the production is context-sensitive; false, otherwise.
	 */
	public boolean isContextSensitive(Alphabet nonTerminals, Alphabet terminals) {
		Alphabet acceptedAlphabet = new Alphabet(nonTerminals, terminals);
		return (this.getLeft().isWithin(acceptedAlphabet)
				&& this.getRight().isWithin(acceptedAlphabet)
				&& this.getLeft().getSize() <= this.getRight().getSize());
	}
	
	/**
	 * Checks if the production is context-free.
	 * 
	 * @param nonTerminals the non terminal alphabet.
	 * @param terminals the terminal alphabet.
	 * 
	 * @return true if the production is context-free; false, otherwise.
	 */
	public boolean isContextFree(Alphabet nonTerminals, Alphabet terminals) {
		Alphabet acceptedAlphabet = new Alphabet(nonTerminals, terminals);
		return (this.getLeft().isWithin(nonTerminals)
				&& this.getLeft().getSize() == 1
				&& this.getRight().isWithin(acceptedAlphabet));
	}
	
	/**
	 * Checks if the production is regular.
	 * 
	 * @param nonTerminals the non terminal alphabet.
	 * @param terminals the terminal alphabet.
	 * 
	 * @return true if the production is regular; false, otherwise.
	 */
	public boolean isRegular(Alphabet nonTerminals, Alphabet terminals) {
		return (this.isRegularLeftLinear(nonTerminals, terminals)
				|| this.isRegularRightLinear(nonTerminals, terminals));
	}
	
	/**
	 * Checks if the production is left linear.
	 * 
	 * @param nonTerminals the non terminal alphabet.
	 * @param terminals the terminal alphabet.
	 * 
	 * @return true if the production is left linear; false, otherwise.
	 */
	public boolean isRegularLeftLinear(Alphabet nonTerminals, Alphabet terminals) {
		return (this.getLeft().isWithin(nonTerminals)
				&& this.getLeft().getSize() == 1
				&& this.getRight().matches("^" + nonTerminals.getUnionRegex() + "{0,1}" + terminals.getUnionRegex() + "$"));
	}
	
	/**
	 * Checks if the production is right linear.
	 * 
	 * @param nonTerminals the non terminal alphabet.
	 * @param terminals the terminal alphabet.
	 * 
	 * @return true if the production is right linear; false, otherwise.
	 */
	public boolean isRegularRightLinear(Alphabet nonTerminals, Alphabet terminals) {
		return (this.getLeft().isWithin(nonTerminals)
				&& this.getLeft().getSize() == 1
				&& this.getRight().matches("^" + terminals.getUnionRegex() + nonTerminals.getUnionRegex() + "{0,1}$"));
	}		

	/**
	 * Checks if the production is in Chomsky Normal Form.
	 * 
	 * @param nonTerminals the non terminal alphabet.
	 * @param terminals the terminal alphabet.
	 * 
	 * @return true if the production is in Chomsky Normal Form; false, otherwise.
	 */
	public boolean isChomskyNormalForm(Alphabet nonTerminals, Alphabet terminals) {
		if (this.getLeft().isWithin(nonTerminals) && this.getLeft().getSize() == 1
				&& this.getRight().isWithin(nonTerminals) && this.getRight().getSize() == 2)
			return true;		
		if (this.getLeft().isWithin(nonTerminals) && this.getLeft().getSize() == 1
				&& this.getRight().isWithin(terminals) && this.getRight().getSize() == 1)
			return true;			
		return false;
	}
	
	/**
	 * Checks if the production is in Greibach Normal Form.
	 * 
	 * @param nonTerminals the non terminal alphabet.
	 * @param terminals the terminal alphabet.
	 * 
	 * @return true if the production is in Greibach Normal Form; false, otherwise.
	 */
	public boolean isGreibachNormalForm(Alphabet nonTerminals, Alphabet terminals) {		
		if (this.getLeft().isWithin(nonTerminals) && this.getLeft().getSize() == 1
				&& this.getRight().matches("^" + terminals.getUnionRegex() + nonTerminals.getUnionRegex() + "*$"))
			return true;		
		return false;
	}

	/**
	 * Checks if the production is an epsilon production.
	 * 
	 * @return true if the production is an epsilon production; false, otherwise.
	 */
	public boolean isEpsilonProduction() {
		return (this.getRight().isEpsilon());
	}	
	
	/**
	 * Checks if the production is a unit production.
	 * 
	 * @param nonTerminals the non terminal alphabet.
	 * 
	 * @return true if the production is a unit production; false, otherwise.
	 */
	public boolean isUnitProduction(Alphabet nonTerminals) {
		return (this.getLeft().getSize() == 1
				&& this.getRight().getSize() == 1
				&& this.getLeft().isWithin(nonTerminals)
				&& this.getRight().isWithin(nonTerminals));
	}
	
	/**
	 * Checks if the production is a trivial unit production.
	 * 
	 * @param nonTerminals the non terminal alphabet.
	 * 
	 * @return true if the production is a trivial unit production; false, otherwise.
	 */
	public boolean isTrivialUnitProduction(Alphabet nonTerminals) {
		return (this.isUnitProduction(nonTerminals)
				&& this.getLeft().equals(this.getRight()));
	}
	
	/**
	 * Checks if the production is a non trivial unit production.
	 * 
	 * @param nonTerminals the non terminal alphabet.
	 * 
	 * @return true if the production is a non trivial unit production; false, otherwise.
	 */
	public boolean isNonTrivialUnitProduction(Alphabet nonTerminals) {
		return (this.isUnitProduction(nonTerminals)
				&& !this.getLeft().equals(this.getRight()));
	}	
	
	@Override public String toString() {
		String s = this.getLeft() + MEMBER_SEPARATOR + this.getRight();
		return s;
	}	
	
	@Override public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass())
			return false;

		Production other = (Production) obj;
		
		return (this.getLeft().equals(other.getLeft()) 
				&& this.getRight().equals(other.getRight()));
	}	
	
	@Override public int compareTo(Production other) {
		int byLeft = this.getLeft().compareTo(other.getLeft());
		if (byLeft == 0)
			return this.getRight().compareTo(other.getRight());
		return byLeft;
	}

	@Override public int hashCode() {
		return Objects.hash(this.getLeft(), 
							this.getRight());
	}
	
}
