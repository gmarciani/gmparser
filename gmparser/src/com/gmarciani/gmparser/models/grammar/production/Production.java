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
	
	private String left;
	private String right;
	
	public static final String MEMBER_SEPARATOR = "->";

	public Production() {
		
	}
	
	public Production(String left, String right) {
		this.left = left;
		this.right = right;
	}

	public String getLeft() {
		return this.left;
	}

	public void setLeft(String left) {
		this.left = left;
	}

	public String getRight() {
		return this.right;
	}

	public void setRight(String right) {
		this.right = right;
	}
	
	public Alphabet getSymbols() {
		Alphabet target = new Alphabet();
		String symbols = this.left + this.right;
		
		for (Character symbol : symbols.toCharArray()) {
			target.add(symbol);
		}
		
		return target;
	}
	
	public Alphabet getLeftSymbols() {
		Alphabet target = new Alphabet();
		
		for (Character symbol : this.left.toCharArray()) {
			target.add(symbol);
		}
		
		return target;
	}
	
	public Alphabet getRightSymbols() {
		Alphabet target = new Alphabet();
		
		for (Character symbol : this.right.toCharArray()) {
			target.add(symbol);
		}
		
		return target;
	}
	
	public boolean isWithin(Alphabet alphabet) {
		return (this.isLeftWithin(alphabet)
				&& this.isRightWithin(alphabet));
	}
	
	public boolean isLeftWithin(Alphabet alphabet) {
		return (alphabet.containsAll(this.getLeftSymbols()));
	}
	
	public boolean isRightWithin(Alphabet alphabet) {
		return (alphabet.containsAll(this.getRightSymbols()));
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
