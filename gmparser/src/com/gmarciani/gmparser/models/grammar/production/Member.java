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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;

/**
 * <p>LHS/RHS member of a production.<p>
 * 
 * @see com.gmarciani.gmparser.models.grammar.Grammar
 * @see com.gmarciani.gmparser.models.grammar.production.Productions
 * @see com.gmarciani.gmparser.models.grammar.production.Production
 * 
 * @author Giacomo Marciani
 * @version 1.0
 */
public class Member implements Comparable<Member> {
	
	private String value;

	/**
	 * Creates a new member, with its value initializes to the empty string.
	 */
	public Member() {
		this.value = "";
	}
	
	/**
	 * Creates a new member, with the specified value.
	 * 
	 * @param value the value of the member.
	 */
	public Member(String value) {
		this.setValue(value);
	}
	
	/**
	 * Creates a new member, with the specified single symbol value.
	 * 
	 * @param value the single symbol value of the member.
	 */
	public Member(Character value) {
		this(value.toString());
	}

	/**
	 * Returns the value of the member.
	 * 
	 * @return the value of the member.
	 */
	public String getValue() {
		return this.value;
	}
	
	/**
	 * Returns the value of the member, represented as an array of symbols.
	 * 
	 * @return the value of the member, represented as an array of symbols.
	 */
	public char[] getValueAsChars() {
		return this.getValue().toCharArray();
	}

	/**
	 * Sets the value of the member to the specified value.
	 * 
	 * @param value the new value of the member.
	 */
	public void setValue(String value) {
		this.value = value;
		this.rebuild();
	}
	
	/**
	 * Sets the value of the member to the specified single symbol value.
	 * 
	 * @param value the new single symbol value of the member.
	 */
	public void setValue(Character value) {
		this.setValue(value.toString());
	}
	
	/**
	 * Returns the length of the member's value.
	 * 
	 * @return the length of the member's value.
	 */
	public int getSize() {
		return this.getValue().length();
	}
	
	/**
	 * Returns the alphabet of the member's value.
	 * 
	 * @return the alphabet of the member's value.
	 */
	public Alphabet getAlphabet() {
		Alphabet target = new Alphabet();		
		target.addAll(this.getNonTerminalAlphabet());
		target.addAll(this.getTerminalAlphabet());		
		return target;
	}
	
	/**
	 * Returns the non terminal alphabet of the member's value.
	 * 
	 * @return the non terminal alphabet of the member's value.
	 */
	public Alphabet getNonTerminalAlphabet() {
		Alphabet target = new Alphabet();		
		for (Character symbol : this.getValue().toCharArray())
			if (Alphabet.isNonTerminal(symbol)) 
				target.add(symbol);
		return target;
	}
	
	/**
	 * Returns the terminal alphabet of the member's value.
	 * 
	 * @return the terminal alphabet of the member's value.
	 */
	public Alphabet getTerminalAlphabet() {
		Alphabet target = new Alphabet();		
		for (Character symbol : this.getValue().toCharArray())
			if (Alphabet.isTerminal(symbol)) 
				target.add(symbol);
		return target;
	}	
	
	/**
	 * Returns the mapping of all the specified symbols occurrences in the member's value.
	 * 
	 * @param alphabet
	 * 
	 * @return the mapping of symbols occurrences in the member's value.
	 */
	public Map<Character, List<Integer>> getSymbolsOccurrences(Alphabet alphabet) {
		Map<Character, List<Integer>> target = new HashMap<Character, List<Integer>>();		
		for (int index = 0; index < this.getValue().length(); index ++) {
			Character symbol = this.getValue().charAt(index);			
			if (alphabet.contains(symbol)) {
				if (!target.containsKey(symbol))
					target.put(symbol, new ArrayList<Integer>());
				target.get(symbol).add(index);
			}			
		}		
		return target;		
	}	
	
	/**
	 * Checks if the members matches the specified regular expression.
	 * 
	 * @param regex the regular expression to match.
	 * 
	 * @return true if the member matches the specified regular expression; false, otherwise.
	 */
	public boolean matches(String regex) {
		return (this.getValue().matches(regex));
	}
	
	/**
	 * Checks if the member's value contains all symbols of the specified alphabet.
	 * 
	 * @param alphabet the alphabet.
	 * 
	 * @return true if the member's value contains all symbols of the specified alphabet; false, otherwise.
	 */
	public boolean isWithin(Alphabet alphabet) {
		return (alphabet.containsAll(this.getAlphabet()));
	}
	
	/**
	 * Checks if the member's value contains at least one symbol of the specified alphabet.
	 * 
	 * @param alphabet the alphabet.
	 * 
	 * @return true if the member's value contains at least one symbol of the specified alphabet; false, otherwise.
	 */
	public boolean isContaining(Alphabet alphabet) {
		for (Character symbol : alphabet)
			if (this.isContaining(symbol))
				return true;		
		return false;
	}
	
	/**
	 * Checks if the member's value contains the specified symbol.
	 * 
	 * @param symbol the symbol.
	 * 
	 * @return true if the member's value contains the specified symbol; false, otherwise.
	 */
	public boolean isContaining(Character symbol) {
		return (this.getValue().indexOf(symbol) != -1);
	}
	
	/**
	 * Check if the member's value is the empty word.
	 * 
	 * @return true if the member's value is the empty word; false, otherwise.
	 */
	public boolean isEpsilon() {
		for (Character symbol : this.getValue().toCharArray())
			if (!symbol.equals(Grammar.EPSILON))
				return false;
		return true;
	}
	
	/**
	 * Rebuilds the member's value to satisfy the epsilon invariance.
	 */
	private void rebuild() {
		if (this.isEpsilon()) {
			this.value = Grammar.EPSILON.toString();
			return;
		}
		String oldValue = this.getValue();
		String newValue = "";
		for (Character symbol : oldValue.toCharArray())
			if (!symbol.equals(Grammar.EPSILON))
				newValue += symbol;
		this.value = newValue;		
	}
	
	@Override public String toString() {
		return this.getValue();
	}
	
	@Override public int compareTo(Member other) {
		return (this.getValue().compareTo(other.getValue()));
	}
	
	@Override public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass())
			return false;

		Member other = (Member) obj;
		
		return (this.getValue().equals(other.getValue()));
	}	

	@Override public int hashCode() {
		return Objects.hash(this.getValue());
	}

}
