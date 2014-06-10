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

import com.gmarciani.gmparser.models.commons.set.GSet;
import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;
import com.gmarciani.gmparser.models.grammar.production.Member;
import com.gmarciani.gmparser.models.grammar.production.Production;

public class ItemLookAhead implements Comparable<ItemLookAhead> {
	
	public static final String DOT_SEPARATOR = ".";
	
	private final Production production;
	private final Integer dot;
	private final Alphabet lookAhead;

	public ItemLookAhead(Production production, Integer dot, Alphabet lookAhead) {
		this.production = production;
		this.dot = dot;
		this.lookAhead = lookAhead;
	}

	public ItemLookAhead(Member left, Member right, Integer dot, Alphabet lookAhead) {
		this(new Production(left, right), dot, lookAhead);
	}
	
	public ItemLookAhead(String left, String right, Integer dot, Alphabet lookAhead) {
		this(new Member(left), new Member(right), dot, lookAhead);
	}
	
	public Production getProduction() {
		return this.production;
	}
	
	public Integer getDot() {
		return this.dot;
	}
	
	public boolean isComplete() {
		return this.getDot().equals(Integer.valueOf(this.getProduction().getRightSize()));
	}
	
	public Alphabet getLookAhead() {
		return this.lookAhead;
	}
	
	@Override public String toString() {
		String lhs = this.getProduction().getLeft().toString();
		String rhs = this.getProduction().getRight().toString().substring(0, this.getDot()) + 
				DOT_SEPARATOR + 
				this.getProduction().getRight().toString().substring(this.getDot());
		
		return lhs + Production.MEMBER_SEPARATOR + rhs;
	}	
	
	@Override public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass())
			return false;

		ItemLookAhead other = (ItemLookAhead) obj;
		
		return (this.getProduction().equals(other.getProduction()))
				&& this.getDot().equals(other.getDot())
				&& this.getLookAhead().equals(other.getLookAhead());
	}	
	
	@Override public int compareTo(ItemLookAhead other) {
		int byProduction = this.getProduction().compareTo(other.getProduction());
		if (byProduction == 0)
			return this.getDot().compareTo(other.getDot());
		return byProduction;	
	}
	
	@Override public int hashCode() {
		return Objects.hash(this.getProduction(),
							this.getDot());
	}

	public static GSet<ItemLookAhead> generateItems(Production production2,
			Grammar grammar) {
		// TODO Auto-generated method stub
		return null;
	}

}
