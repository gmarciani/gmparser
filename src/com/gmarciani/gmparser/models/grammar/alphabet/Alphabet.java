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

import com.gmarciani.gmparser.models.commons.set.GSet;

/**
 * <p>The alphabet model.<p>
 * <p>An alphabet is a set of symbols, that could be terminals or non terminals.<p>
 * 
 * @see com.gmarciani.gmparser.models.grammar.Grammar
 * 
 * @author Giacomo Marciani
 * @version 1.0
 */
public class Alphabet extends GSet<Character> {

	private static final long serialVersionUID = 86933392974869837L;
	
	/**
	 * Constructs a new alphabet.
	 */
	public Alphabet() {
		super();
	}
	
	/**
	 * Constructs a new alphabet containing the specified symbols.
	 * 
	 * @param symbols the symbols to add to the alphabet.
	 */
	public Alphabet(Character ... symbols) {
		super();
		for (Character symbol : symbols)
			super.add(symbol);
	}
	
	/**
	 * Constructs a new alphabet containing the symbols in the specified string.
	 * 
	 * @param symbols the symbols to add to the alphabet.
	 */
	public Alphabet(String symbols) {
		super();
		this.addAll(symbols);
	}	
	
	/**
	 * Constructs a new alphabet as union of the specified alphabets.
	 * 
	 * @param alphabets the symbols to add to the alphabet.
	 */
	public Alphabet(Alphabet ... alphabets) {
		super();
		for (Alphabet alphabet : alphabets)
			super.addAll(alphabet);
	}
	
	/**
	 * Adds to the alphabet all the symbols contained in the specified alphabet.
	 * 
	 * @param alphabet the symbols to add to the alphabet
	 * 
	 * @return true if at least one symbols has been added to the alphabet; false, otherwise.
	 */
	public boolean addAll(Alphabet alphabet) {
		boolean added = false;
		for (Character symbol : alphabet)
			added = this.add(symbol) ? true : added;		
		return added;
	}
	
	/**
	 * Adds to the alphabet all the symbols contained in the specified string.
	 * 
	 * @param symbols the symbols to add to the alphabet.
	 * 
	 * @return true if at least one symbol has been added to the alphabet; false, otherwise.
	 */
	public boolean addAll(String symbols) {
		boolean added = false;
		for (Character symbol : symbols.toCharArray())
			added = this.add(symbol) ? true : added;
		return added;
	}
	
	/**
	 * Removes from the alphabet all symbols of the specified alphabet.
	 * 
	 * @param alphabet the alphabet of symbols to remove.
	 * 
	 * @return true if at least a symbol has been removed; false, otherwise.
	 */
	public boolean removeAll(Alphabet alphabet) {
		boolean removed = false;
		for (Character symbol : alphabet) 
			removed = this.remove(symbol) ? true : removed;		
		return removed;
	}
	
	/**
	 * Removes from the alphabet all symbols in the string.
	 * 
	 * @param symbols the string of symbols to remove.
	 * 
	 * @return true if at least a symbol has been removed; false, otherwise.
	 */
	public boolean removeAll(String symbols) {
		boolean removed = false;
		for (Character symbol : symbols.toCharArray())
			removed = this.remove(symbol) ? true : removed;		
		return removed;
	}	
	
	/**
	 * Checks if the specified symbol is a terminal symbol.
	 * 
	 * @param symbol the symbol.
	 * 
	 * @return true if the symbol is a terminal symbol; false, otherwise.
	 */
	public static boolean isTerminal(Character symbol) {
		return (!isNonTerminal(symbol));
	}
	
	/**
	 * Checks if the specified symbol is a non terminal symbol.
	 * 
	 * @param symbol the symbol.
	 * 
	 * @return true if the symbol is a non terminal symbol; false, otherwise.
	 */
	public static boolean isNonTerminal(Character symbol) {
		return (Character.isLetter(symbol)
				&& Character.isUpperCase(symbol));
	}

	/**
	 * Returns the regular expression of the alphabet.
	 * 
	 * @return the regular expression of the alphabet.
	 */
	public String getUnionRegex() {
		String regex = "[";
		for (Character symbol : this)
			regex += symbol;
		regex += "]";		
		return regex;
	}

	/**
	 * Returns an alphabet of all the possible non terminal symbols.
	 * 
	 * @return the alphabet of all the possible non terminal symbols.
	 */
	public static Alphabet getTotalNonTerminals() {
		return new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
	}
	
}
