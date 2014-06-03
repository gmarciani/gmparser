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

package com.gmarciani.gmparser.models.grammar.alphabet;

import java.util.Objects;
import java.util.concurrent.ConcurrentSkipListSet;

public class Alphabet extends ConcurrentSkipListSet<Character> {

	private static final long serialVersionUID = 86933392974869837L;
	
	private static final Alphabet STANDARD_NON_TERMINAL_ALPHABET = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
	
	private AlphabetType type = null;
	
	public Alphabet() {
		super();
	}
	
	public Alphabet(Alphabet alphabet) {
		super(alphabet);
	}
	
	public Alphabet(Alphabet ... alphabets) {
		super();
		for (Alphabet alphabet : alphabets)
			this.addAll(alphabet);
	}
	
	public Alphabet(AlphabetType type) {
		super();
		this.type = type;
	}
	
	public Alphabet(Character symbol) {
		super();
		this.add(symbol);
	}	
	
	public Alphabet(String symbols) {
		super();
		this.addAll(symbols);
	}	
	
	public AlphabetType getType() {
		return this.type;
	}
	
	public boolean addAll(Alphabet symbols) {
		boolean added = false;
		for (Character symbol : symbols) {
			if (this.isAcceptableSymbol(symbol)) {
				added = this.add(symbol) ? true : added;
			} else {
				System.out.println("Not acceptable symbol: " + symbol + " in alphabet of type " + this.type);
			}
				
		}
		
		return added;
	}
	
	public boolean addAll(String symbols) {
		boolean added = false;
		for (Character symbol : symbols.toCharArray()) {
			if (this.isAcceptableSymbol(symbol)) {
				added = this.add(symbol) ? true : added;
			} else {
				System.out.println("Not acceptable symbol: " + symbol + " in alphabet of type " + this.type);
			}
		}
		
		return added;
	}
	
	public boolean removeAll(Alphabet alphabet) {
		boolean removed = false;
		for (Character symbol : alphabet) {
			removed = this.remove(symbol) ? true : removed;
		}
		
		return removed;
	}
	
	public boolean removeAll(String symbols) {
		boolean removed = false;
		for (Character symbol : symbols.toCharArray()) {
			removed = this.remove(symbol) ? true : removed;
		}
		
		return removed;
	}
	
	private boolean isAcceptableSymbol(Character symbol) {
		if (this.getType() == null)
			return true;
		if (this.getType() == AlphabetType.NON_TERMINAL
				&& isNonTerminal(symbol))
			return true;
		if (this.getType() == AlphabetType.TERMINAL
				&& isTerminal(symbol))
			return true;
		
		return false;
	}	
	
	public static boolean isTerminal(Character symbol) {
		return (!isNonTerminal(symbol));
	}
	
	public static boolean isNonTerminal(Character symbol) {
		return (Character.isLetter(symbol)
				&& Character.isUpperCase(symbol));
	}

	public String getUnionRegex() {
		String regex = "[";
		for (Character symbol : this) {
			regex += symbol;
		}
		regex += "]";
		
		return regex;
	}

	public static Alphabet getTotalNonTerminals() {
		Alphabet target = new Alphabet(AlphabetType.NON_TERMINAL);
		target.addAll(STANDARD_NON_TERMINAL_ALPHABET);
		
		return target;
	}	
	
	@Override public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass())
			return false;
		
		Alphabet other = (Alphabet) obj;
		
		boolean byType = (this.getType() == other.getType());
		boolean bySymbols = (this.containsAll(other) && other.containsAll(this));
		
		return byType && bySymbols;
	}
	
	@Override public int hashCode() {
		return Objects.hash(this.type, this.toArray(new Character[this.size()]));
	}
	
}
