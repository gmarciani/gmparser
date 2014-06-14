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

import com.gmarciani.gmparser.models.grammar.Type;
import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;
import com.gmarciani.gmparser.models.grammar.alphabet.AlphabetType;

/**
 * LHS/RHS member of a production.
 * 
 * @see com.gmarciani.gmparser.models.grammar.Grammar
 * @see com.gmarciani.gmparser.models.grammar.production.Productions
 * @see com.gmarciani.gmparser.models.grammar.production.Member
 * @see com.gmarciani.gmparser.models.alphabet.Alphabet
 * 
 * @author Giacomo Marciani
 * @version 1.0
 */
public class Production implements Comparable<Production> {
	
	private Member left;
	private Member right;
	
	public static final String MEMBER_SEPARATOR = "->";	
	
	public Production(Member left, Member right) {
		this.setLeft(left);
		this.setRight(right);
	}
	
	public Production(Production production) {
		this.setLeft(production.getLeft());
		this.setRight(production.getRight());
	}

	public Member getLeft() {
		return this.left;
	}

	public void setLeft(Member left) {
		this.left = left;
	}

	public Member getRight() {
		return this.right;
	}

	public void setRight(Member right) {
		this.right = right;
	}
	
	public int getLeftSize() {
		return (this.getLeft().getSize());
	}
	
	public int getRightSize() {
		return (this.getRight().getSize());
	}
	
	public Alphabet getAlphabet() {
		Alphabet target = new Alphabet();		
		target.addAll(this.getNonTerminalAlphabet());
		target.addAll(this.getTerminalAlphabet());		
		return target;
	}
	
	public Alphabet getNonTerminalAlphabet() {
		Alphabet target = new Alphabet(AlphabetType.NON_TERMINAL);		
		target.addAll(this.getLeft().getNonTerminalAlphabet());
		target.addAll(this.getRight().getNonTerminalAlphabet());		
		return target;
	}
	
	public Alphabet getTerminalAlphabet() {
		Alphabet target = new Alphabet(AlphabetType.TERMINAL);		
		target.addAll(this.getLeft().getTerminalAlphabet());
		target.addAll(this.getRight().getTerminalAlphabet());		
		return target;
	}
	
	public boolean isWithin(Alphabet alphabet) {
		return (this.isLeftWithin(alphabet)
				&& this.isRightWithin(alphabet));
	}
	
	public boolean isLeftWithin(Alphabet alphabet) {
		return this.getLeft().isWithin(alphabet);
	}
	
	public boolean isRightWithin(Alphabet alphabet) {
		return this.getRight().isWithin(alphabet);
	}	
	
	public boolean isContaining(Alphabet alphabet) {
		return (this.isLeftContaining(alphabet)
				&& this.isRightContaining(alphabet));
	}
	
	public boolean isLeftContaining(Alphabet alphabet) {
		return this.getLeft().isContaining(alphabet);
	}
	
	public boolean isRightContaining(Alphabet alphabet) {
		return this.getRight().isContaining(alphabet);
	}
	
	public boolean isContaining(Character symbol) {
		return (this.isLeftContaining(symbol)
				&& this.isRightContaining(symbol));
	}
	
	public boolean isLeftContaining(Character symbol) {
		return this.getLeft().isContaining(symbol);
	}
	
	public boolean isRightContaining(Character symbol) {
		return this.getRight().isContaining(symbol);
	}
	
	public boolean isLeftEpsilon() {
		return (this.getLeft().isEpsilon());
	}
	
	public boolean isRightEpsilon() {
		return (this.getRight().isEpsilon());
	}
	
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
	
	public boolean isUnrestricted(Alphabet nonTerminals, Alphabet terminals) {
		Alphabet acceptedAlphabet = new Alphabet(nonTerminals, terminals);
		return (this.isLeftWithin(acceptedAlphabet)
				&& (this.isRightWithin(acceptedAlphabet) || this.isRightEpsilon()));
	}

	public boolean isContextSensitive(Alphabet nonTerminals, Alphabet terminals) {
		Alphabet acceptedAlphabet = new Alphabet(nonTerminals, terminals);
		return (this.isLeftWithin(acceptedAlphabet)
				&& this.isRightWithin(acceptedAlphabet)
				&& this.getLeftSize() <= this.getRightSize());
	}
	
	public boolean isContextFree(Alphabet nonTerminals, Alphabet terminals) {
		Alphabet acceptedAlphabet = new Alphabet(nonTerminals, terminals);
		return (this.isLeftWithin(nonTerminals)
				&& this.getLeftSize() == 1
				&& this.isRightWithin(acceptedAlphabet));
	}
	
	public boolean isRegular(Alphabet nonTerminals, Alphabet terminals) {
		return (this.isRegularLeftLinear(nonTerminals, terminals)
				|| this.isRegularRightLinear(nonTerminals, terminals));
	}
	
	public boolean isRegularLeftLinear(Alphabet nonTerminals, Alphabet terminals) {
		return (this.isLeftWithin(nonTerminals)
				&& this.getLeftSize() == 1
				&& this.getRight().matches("^" + nonTerminals.getUnionRegex() + "{0,1}" + terminals.getUnionRegex() + "$"));
	}
	
	public boolean isRegularRightLinear(Alphabet nonTerminals, Alphabet terminals) {
		return (this.isLeftWithin(nonTerminals)
				&& this.getLeftSize() == 1
				&& this.getRight().matches("^" + terminals.getUnionRegex() + nonTerminals.getUnionRegex() + "{0,1}$"));
	}		

	public boolean isChomskyNormalForm(Alphabet nonTerminals, Alphabet terminals) {
		if (this.isLeftWithin(nonTerminals) && this.getLeft().getSize() == 1
				&& this.isRightWithin(nonTerminals) && this.getRight().getSize() == 2)
			return true;		
		if (this.isLeftWithin(nonTerminals) && this.getLeft().getSize() == 1
				&& this.isRightWithin(terminals) && this.getRight().getSize() == 1)
			return true;		
		//aggiungere S->empty e S non sta nel rhs di alcuna produzione		
		return false;
	}
	
	public boolean isGreibachNormalForm(Alphabet nonTerminals, Alphabet terminals) {		
		if (this.isLeftWithin(nonTerminals) && this.getLeft().getSize() == 1
				&& this.getRight().matches("^" + terminals.getUnionRegex() + nonTerminals.getUnionRegex() + "*$"))
			return true;		
		//aggiungere S->empty		
		return false;
	}

	public boolean isEpsilonProduction() {
		return (this.isRightEpsilon());
	}	
	
	public boolean isUnitProduction(Alphabet nonTerminals) {
		return (this.getLeft().getSize() == 1
				&& this.getRight().getSize() == 1
				&& this.getLeft().isWithin(nonTerminals)
				&& this.getRight().isWithin(nonTerminals));
	}
	
	public boolean isTrivialUnitProduction(Alphabet nonTerminals) {
		return (this.isUnitProduction(nonTerminals)
				&& this.getLeft().equals(this.getRight()));
	}
	
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
