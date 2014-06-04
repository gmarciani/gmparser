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
import com.gmarciani.gmparser.models.grammar.alphabet.AlphabetType;

/**
 * LHS/RHS member of a production.
 * 
 * @see com.gmarciani.gmparser.models.grammar.Grammar
 * @see com.gmarciani.gmparser.models.grammar.production.Productions
 * @see com.gmarciani.gmparser.models.grammar.production.Production
 * @see com.gmarciani.gmparser.models.alphabet.Alphabet
 * 
 * @author Giacomo Marciani
 * @version 1.0
 */
public class Member implements Comparable<Member> {
	
	private String value;

	public Member() {
		this.value = "";
	}
	
	public Member(String value) {
		this.setValue(value);
	}
	
	public Member(Character value) {
		this.setValue(value);
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
		this.rebuild();
	}
	
	public void setValue(Character value) {
		this.value = "" + value;
		this.rebuild();
	}
	
	public int getSize() {
		return this.getValue().length();
	}
	
	public Alphabet getAlphabet() {
		Alphabet target = new Alphabet();
		
		target.addAll(this.getNonTerminalAlphabet());
		target.addAll(this.getTerminalAlphabet());		
		
		return target;
	}
	
	public Alphabet getNonTerminalAlphabet() {
		Alphabet target = new Alphabet(AlphabetType.NON_TERMINAL);
		
		for (Character symbol : this.getValue().toCharArray()) {
			if (Alphabet.isNonTerminal(symbol)) 
				target.add(symbol);
		}
		
		return target;
	}
	
	public Alphabet getTerminalAlphabet() {
		Alphabet target = new Alphabet(AlphabetType.TERMINAL);
		
		for (Character symbol : this.getValue().toCharArray()) {
			if (Alphabet.isTerminal(symbol)) 
				target.add(symbol);
		}
		
		return target;
	}	
	
	public List<Character> getSymbols() {
		List<Character> target = new ArrayList<Character>();
		
		for (Character symbol : this.getValue().toCharArray()) 
			target.add(symbol);
		
		return target;
	}
	
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
	
	public List<Character> getNonTerminals() {
		List<Character> target = new ArrayList<Character>();
		
		for (Character symbol : this.getValue().toCharArray()) {
			if (Alphabet.isNonTerminal(symbol))
				target.add(symbol);
		}
		return target;
	}
	
	public List<Character> getTerminals() {
		List<Character> target = new ArrayList<Character>();
		
		for (Character symbol : this.getValue().toCharArray()) {
			if (Alphabet.isTerminal(symbol))
				target.add(symbol);
		}
		return target;
	}	
	
	public boolean matches(String regex) {
		return (this.getValue().matches(regex));
	}
	
	public boolean isWithin(Alphabet alphabet) {
		return (alphabet.containsAll(this.getAlphabet()));
	}
	
	public boolean isContaining(Alphabet alphabet) {
		for (Character symbol : alphabet) {
			if (this.isContaining(symbol))
				return true;
		}
		
		return false;
	}
	
	public boolean isContaining(Character symbol) {
		return (this.getValue().indexOf(symbol) != -1);
	}
	
	public void replace(Character oldSymbol, Character newSymbol) {
		String oldValue = this.getValue();
		String newValue = oldValue.replace(oldSymbol, newSymbol);
		this.setValue(newValue);
	}
	
	public boolean isEpsilon() {
		return this.getValue().matches("^" + Grammar.EPSILON + "+$");
	}
	
	private void rebuild() {
		if (this.isEpsilon()) {
			this.value = Grammar.EPSILON.toString();
		} else {
			this.value = this.getValue().replace(Grammar.EPSILON.toString(), "");
		}			
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
