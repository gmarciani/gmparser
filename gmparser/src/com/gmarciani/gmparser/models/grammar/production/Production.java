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

import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;
import com.gmarciani.gmparser.models.grammar.alphabet.AlphabetType;

public class Production implements Comparable<Production> {
	
	private Character left;
	private String right;
	
	public static final String MEMBER_SEPARATOR = "->";

	public Production() {
		
	}
	
	public Production(Production production) {
		this.setLeft(production.getLeft());
		this.setRight(production.getRight());
	}
	
	public Production(Character left, String right) {
		this.setLeft(left);
		this.setRight(right);
	}

	public Character getLeft() {
		return this.left;
	}

	public void setLeft(Character left) {
		this.left = left;
	}

	public String getRight() {
		return this.right;
	}

	public void setRight(String right) {
		this.right = right;
		this.rebuild();
	}
	
	private void rebuild() {
		if (this.isEpsilonProduction()) {
			this.right = Grammar.EMPTY;
		} else {
			this.right = this.right.replace(Grammar.EMPTY, "");
		}			
	}
	
	public int getLeftSize() {
		return 1;
	}
	
	public int getRightSize() {
		return this.right.length();
	}
	
	public Alphabet getLeftAlphabet() {
		Alphabet target = new Alphabet();
		target.add(this.getLeftNonTerminals());
		
		return target;
	}
	
	public Alphabet getRightAlphabet() {
		Alphabet target = new Alphabet();
		target.add(this.getRightNonTerminals());
		target.add(this.getRightTerminals());
		
		return target;
	}	
	
	public Alphabet getLeftNonTerminals() {
		Alphabet target = new Alphabet(AlphabetType.NON_TERMINAL);
		Character lhs = this.getLeft();
		if (Alphabet.isNonTerminal(lhs))
			target.add(this.getLeft());
		
		return target;
	}
	
	public Alphabet getRightNonTerminals() {
		Alphabet target = new Alphabet(AlphabetType.NON_TERMINAL);
		
		String rhs = this.getRight();
		
		for (Character symbol : rhs.toCharArray()) {
			if (Alphabet.isNonTerminal(symbol)) 
				target.add(symbol);
		}
		
		return target;
	}

	public Alphabet getRightTerminals() {
		Alphabet target = new Alphabet(AlphabetType.TERMINAL);
		
		String rhs = this.getRight();
		
		for (Character symbol : rhs.toCharArray()) {
			if (Alphabet.isTerminal(symbol)) 
				target.add(symbol);
		}
		
		return target;
	}
	
	public boolean isWithin(Alphabet alphabet) {
		return (this.isLeftWithin(alphabet)
				&& this.isRightWithin(alphabet));
	}

	public boolean isLeftWithin(Alphabet alphabet) {
		return (alphabet.containsAll(this.getLeftAlphabet()));
	}
	
	public boolean isRightWithin(Alphabet alphabet) {
		return (alphabet.containsAll(this.getRightAlphabet()));
	}
	
	public boolean isEpsilonProduction() {
		return this.getRight().matches("^" + Grammar.EMPTY + "+$");
	}	
	
	public boolean isUnitProduction(Alphabet nonTerminals) {
		return(this.getLeftSize() == 1
				&& this.getRightSize() == 1
				&& this.isLeftWithin(nonTerminals)
				&& this.isRightWithin(nonTerminals));
	}
	
	public boolean isTrivialUnitProduction(Alphabet nonTerminals) {
		return (this.isUnitProduction(nonTerminals)
				&& this.getLeft().equals(this.getRight().charAt(0)));
	}
	
	public boolean isNonTrivialUnitProduction(Alphabet nonTerminals) {
		return (this.isUnitProduction(nonTerminals)
				&& !this.getLeft().equals(this.getRight()));
	}
	
	@Override
	public String toString() {
		String s = this.getLeft() + MEMBER_SEPARATOR + this.getRight();
		return s;
	}
	
	@Override
	public int compareTo(Production other) {
		int byLeft = this.left.compareTo(other.left);
		if (byLeft == 0)
			return this.right.compareTo(other.right);
		return byLeft;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass())
			return false;

		Production other = (Production) obj;
		
		return (this.left.equals(other.left) 
				&& this.right.equals(other.right));
	}	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.left == null) ? 0 : this.left.hashCode());
		result = prime * result + ((this.right == null) ? 0 : this.right.hashCode());
		return result;
	}
}