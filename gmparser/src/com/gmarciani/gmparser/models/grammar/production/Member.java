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
import java.util.List;

import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;
import com.gmarciani.gmparser.models.grammar.alphabet.AlphabetType;

public class Member implements Comparable<Member> {
	
	private String value;

	public Member() {
		
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
		if (value == null)
			System.out.println("member with null value");
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
		
		target.add(this.getNonTerminalAlphabet());
		target.add(this.getTerminalAlphabet());		
		
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
	
	public boolean contains(CharSequence symbols) {
		return (this.getValue().contains(symbols));
	}
	
	public boolean contains(Character symbol) {
		return (this.getValue().indexOf(symbol) != -1);
	}
	
	public boolean isWithin(Alphabet alphabet) {
		return (alphabet.containsAll(this.getAlphabet()));
	}
	
	public boolean isEpsilon() {
		return this.getValue().matches("^" + Grammar.EMPTY + "+$");
	}
	
	private void rebuild() {
		if (this.isEpsilon()) {
			this.value = Grammar.EMPTY;
		} else {
			this.value = this.getValue().replace(Grammar.EMPTY, "");
		}			
	}
	
	@Override
	public int compareTo(Member other) {
		return (this.getValue().compareTo(other.getValue()));
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass())
			return false;

		Member other = (Member) obj;
		
		return (this.getValue().equals(other.getValue()));
	}
	
	@Override
	public String toString() {
		return this.value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.getValue() == null) ? 0 : this.getValue().hashCode());
		return result;
	}

}
