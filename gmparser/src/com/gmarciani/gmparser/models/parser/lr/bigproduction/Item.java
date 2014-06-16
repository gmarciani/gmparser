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

package com.gmarciani.gmparser.models.parser.lr.bigproduction;

import java.util.Objects;

import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;
import com.gmarciani.gmparser.models.grammar.production.Production;

public class Item implements Comparable<Item> {
	
	public static final Character DOT_SEPARATOR = '.';
	public static final Character END_MARKER = '$';
	
	private final Production production;
	private final int dot;
	private Alphabet lookAhead;
	
	public Item(Production production, int dot, Alphabet lookAhead) {
		this.production = production;
		this.dot = dot;
		this.lookAhead = (lookAhead != null) ? lookAhead : new Alphabet();
	}
	
	public Item(Production production, int dot) {
		this(production, dot, null);
	}
	
	public Production getProduction() {
		return this.production;
	}
	
	public int getDot() {
		return this.dot;
	}
	
	public Alphabet getLookAhead() {
		return this.lookAhead;
	}
	
	public Character getJustReadCharacter() {
		if (this.hasJustReadCharacter())
			return this.getProduction().getRight().getValue().charAt(this.getDot() - 1);
		return null;
	}
	
	public boolean hasJustReadCharacter() {
		return this.getDot() >= 1;
	}
	
	public Character getNextCharacter() {
		return this.getNextCharacter(0);
	}
	
	public Character getNextCharacter(int index) {
		if (this.hasNextCharacter(index))
			return this.getProduction().getRight().getValue().charAt(this.getDot() + index);
		return null;
	}
	
	public boolean hasNextCharacter() {
		return this.hasNextCharacter(0);
	}
	
	public boolean hasNextCharacter(int index) {
		return this.getProduction().getRightSize() >= this.getDot() + index + 1;
	}
	
	public boolean isStart() {
		return (this.getDot() == 0
				|| (this.getProduction().isEpsilonProduction() && this.isComplete()));
	}
	
	public boolean isComplete() {
		return this.getDot() == this.getProduction().getRightSize();
	}	
	
	@Override public String toString() {
		String lhs = this.getProduction().getLeft().toString();
		String rhs = this.getProduction().getRight().toString().substring(0, this.getDot()) + 
				DOT_SEPARATOR + 
				this.getProduction().getRight().toString().substring(this.getDot());
		
		return "(" + lhs + Production.MEMBER_SEPARATOR + rhs + "," + this.getLookAhead() + ")";
	}	
	
	@Override public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass())
			return false;

		Item other = (Item) obj;
		
		return (this.getProduction().equals(other.getProduction()))
				&& this.getDot() == other.getDot()
				&& this.getLookAhead().equals(other.getLookAhead());
	}	
	
	@Override public int compareTo(Item other) {
		int byProduction = this.getProduction().compareTo(other.getProduction());
		if (byProduction == 0)
			return Integer.valueOf(this.getDot()).compareTo(other.getDot());
		return byProduction;	
	}
	
	@Override public int hashCode() {
		return Objects.hash(this.getProduction(),
							this.getDot());
	}	

}
