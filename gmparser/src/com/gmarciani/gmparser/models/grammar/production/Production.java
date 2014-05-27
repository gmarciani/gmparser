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

import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;

public class Production implements Comparable<Production> {
	
	private Member left;
	private Member right;
	
	public static final String MEMBER_SEPARATOR = "->";

	public Production() {
		
	}
	
	public Production(Production production) {
		this.setLeft(production.getLeft());
		this.setRight(production.getRight());
	}
	
	public Production(Member left, Member right) {
		this.setLeft(left);
		this.setRight(right);
	}
	
	public Production(String left, String right) {
		Member lhs = new Member(left);
		Member rhs = new Member(right);
		this.setLeft(lhs);
		this.setRight(rhs);
	}
	
	public Production(Character left, String right) {
		Member lhs = new Member(left);
		Member rhs = new Member(right);
		this.setLeft(lhs);
		this.setRight(rhs);
	}

	public Member getLeft() {
		return this.left;
	}

	public void setLeft(Member left) {
		this.left = left;
	}
	
	public void setLeft(String left) {
		if (this.getLeft() == null)
			this.setLeft(new Member());
		this.getLeft().setValue(left);
	}
	
	public void setLeft(Character left) {
		if (this.getLeft() == null)
			this.setLeft(new Member());
		this.getLeft().setValue(left);
	}

	public Member getRight() {
		return this.right;
	}

	public void setRight(Member right) {
		this.right = right;
	}
	
	public void setRight(String right) {
		if (this.getRight() == null)
			this.setRight(new Member());
		this.getRight().setValue(right);
	}
	
	public void setRight(Character right) {
		if (this.getRight() == null)
			this.setRight(new Member());
		this.getRight().setValue(right);
	}
	
	public boolean isWithin(Alphabet alphabet) {
		return (this.getLeft().isWithin(alphabet)
				&& this.getRight().isWithin(alphabet));
	}
	
	public boolean isLeftWithin(Alphabet alphabet) {
		return (this.getLeft().isWithin(alphabet));
	}
	
	public boolean isRightWithin(Alphabet alphabet) {
		return (this.getRight().isWithin(alphabet));
	}
	
	public boolean isEpsilonProduction() {
		return this.getRight().isEpsilon();
	}	
	
	public boolean isUnitProduction(Alphabet nonTerminals) {
		return(this.getLeft().getSize() == 1
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
	
	@Override
	public int compareTo(Production other) {
		int byLeft = this.getLeft().compareTo(other.getLeft());
		if (byLeft == 0)
			return this.getRight().compareTo(other.getRight());
		return byLeft;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass())
			return false;

		Production other = (Production) obj;
		
		return (this.getLeft().equals(other.getLeft()) 
				&& this.getRight().equals(other.getRight()));
	}	
	
	@Override
	public String toString() {
		String s = this.getLeft() + MEMBER_SEPARATOR + this.getRight();
		return s;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.getLeft() == null) ? 0 : this.getRight().hashCode());
		result = prime * result + ((this.getRight() == null) ? 0 : this.getRight().hashCode());
		return result;
	}
	
}
