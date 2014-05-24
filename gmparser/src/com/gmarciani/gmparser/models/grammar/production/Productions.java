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

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;

public class Productions extends LinkedHashSet<Production> {

	private static final long serialVersionUID = 9062194540654802369L;
	
	public static final String MEMBER_SEPARATOR = Production.MEMBER_SEPARATOR; 
	public static final String INFIX_SEPARATOR = "|";
	public static final String PRODUCTION_SEPARATOR = ";";
	public static final String PRODUCTION_ENDER = ".";

	public Productions() {
		super();
	}
	
	public Productions(Productions productions) {
		super(productions);
	}
		
	public boolean add(Character left, String right) {
		Production production = new Production(left, right);
		return this.add(production);
	}
	
	public Productions getProductionsWithin(Alphabet alphabet) {
		Productions target = new Productions();
		
		for (Production production : this) {
			if (production.isWithin(alphabet))
				target.add(production);
		}
		
		return target;
	}
	
	public Productions getProductionsForNonTerminal(Character nonTerminal) {
		Productions target = new Productions();
		
		for (Production production : this) {
			if (production.getLeft().equals(nonTerminal))
				target.add(production);
		}
		
		return target;
	}
	
	public Set<String> getSententialsForNonTerminal(Character nonTerminal) {
		Set<String> target = new LinkedHashSet<String>();
		
		for (Production production : this) {
			if (production.getLeft().equals(nonTerminal))
				target.add(production.getRight());
		}
		
		return target;
	}
	
	public Alphabet getLeftNonTerminals() {
		Alphabet target = new Alphabet();
		
		for (Production production : this) {
			target.add(production.getLeftNonTerminals());
		}
		
		return target;
	}
	
	public Alphabet getRightNonTerminals() {
		Alphabet target = new Alphabet();
		
		for (Production production : this) {
			target.add(production.getRightNonTerminals());
		}
		
		return target;
	}
	
	public Alphabet getRightTerminals() {
		Alphabet target = new Alphabet();
		
		for (Production production : this) {
			target.add(production.getRightTerminals());
		}
		
		return target;
	}

	@Override
	public String toString() {
		String s = "[";
		
		Iterator<Character> nonTerminals = this.getLeftNonTerminals().iterator();
		while(nonTerminals.hasNext()) {
			Character nonTerminal = nonTerminals.next();
			s += nonTerminal + Production.MEMBER_SEPARATOR;
			Iterator<String> sententials = this.getSententialsForNonTerminal(nonTerminal).iterator();
			while(sententials.hasNext()) {
				String sentential = sententials.next();
				s += sentential + ((sententials.hasNext()) ? INFIX_SEPARATOR : "");
			}
			s += ((nonTerminals.hasNext()) ? PRODUCTION_SEPARATOR : "");
		}
		
		s += "]";
		
		return s;
	}	

}
