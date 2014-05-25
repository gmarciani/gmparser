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

import java.util.TreeSet;

public class Alphabet extends TreeSet<Character> {

	private static final long serialVersionUID = 86933392974869837L;
	
	private AlphabetType type = null;
	
	public Alphabet() {
		super();
	}
	
	public Alphabet(AlphabetType type) {
		super();
		this.type = type;
	}
	
	public Alphabet(Character symbol) {
		super();
		this.add(symbol);
	}
	
	public Alphabet(Alphabet alphabet) {
		super(alphabet);
	}
	
	public Alphabet(String symbols) {
		super();
		this.add(symbols);
	}	
	
	public AlphabetType getType() {
		return this.type;
	}
	
	public boolean add(Alphabet symbols) {
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
	
	public boolean add(String symbols) {
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
	
	private boolean isAcceptableSymbol(Character symbol) {
		if (this.type == null)
			return true;
		if (this.type == AlphabetType.NON_TERMINAL
				&& isNonTerminal(symbol))
			return true;
		if (this.type == AlphabetType.TERMINAL
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
}
