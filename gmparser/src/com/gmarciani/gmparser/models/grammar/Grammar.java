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

package com.gmarciani.gmparser.models.grammar;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Grammar {	
	
	private Set<Character> terminalAlphabet;
	private Set<Character> nonTerminalAlphabet;
	private Map<Character, Set<String>> productions;
	private Character axiom;
	private String empty;
	
	public static final Character DEFAULT_AXIOM = 'S';
	public static final String DEFAULT_EMPTY_STRING = "e";
	
	public static final String EPSILON = "\u03B5"; //on terminal Ctrl+Shift+u+03b5
	
	public static final String DEFAULT_PRODUCTION_SEPARATOR = ";";
	public static final String DEFAULT_MEMBER_SEPARATOR = "->";
	public static final String DEFAULT_INFIX_SEPARATOR = "|";
	
	public Grammar() {
		this.axiom = DEFAULT_AXIOM;
		this.empty = DEFAULT_EMPTY_STRING;
		this.terminalAlphabet = new LinkedHashSet<Character>();
		this.nonTerminalAlphabet = new LinkedHashSet<Character>();
		this.nonTerminalAlphabet.add(this.axiom);
		this.productions = new HashMap<Character, Set<String>>();
		this.productions.put(this.axiom, new LinkedHashSet<String>());		
	}	
	
	public Set<Character> getTerminalAlphabet() {
		return this.terminalAlphabet;
	}

	public Set<Character> getNonTerminalAlphabet() {
		return this.nonTerminalAlphabet;
	}

	public Map<Character, Set<String>> getProductions() {
		return this.productions;
	}

	public Character getAxiom() {
		return this.axiom;
	}
	
	public void setAxiom(Character axiom) {
		this.axiom = axiom;
	}
	
	public String getEmpty() {
		return this.empty;
	}
	
	public void setEmpty(String empty) {
		this.empty = empty;
	}
	
	public void addTerminalSymbol(Character symbol) {
		this.terminalAlphabet.add(symbol);
	}
	
	public void addNonTerminalSymbol(Character symbol) {
		this.nonTerminalAlphabet.add(symbol);
	}
	
	public void addProduction(Character nonTerminalSymbol, String sentential) {
		Set<String> sententials = this.productions.get(nonTerminalSymbol);
		sententials.add(sentential);
		this.productions.put(nonTerminalSymbol, sententials);
	}
	
	public Set<String> getProductionsForNonTerminalSymbol(Character nonTerminalSymbol) {
		return this.productions.get(nonTerminalSymbol);
	}
	
	public Set<String> getProductionsForAxiom() {
		return this.productions.get(this.axiom);
	}
	
	public boolean isAxiom(Character symbol) {
		return (symbol.equals(this.axiom));
	}
	
	public boolean isEmptyString(String string) {
		return (string.equals(this.empty));
	}
	
	public boolean isTerminalSymbol(Character symbol) {
		return (this.terminalAlphabet.contains(symbol));
	}
	
	public boolean isNonTerminalSymbol(Character symbol) {
		return (this.nonTerminalAlphabet.contains(symbol));
	}

	@Override
	public boolean equals(Object obj) {
		if (this.getClass() != obj.getClass())
			return false;
		
		Grammar other = (Grammar) obj;
		
		if (!this.axiom.equals(other.axiom))
			return false;
		
		if (!empty.equals(other.empty))
			return false;
		
		if (!nonTerminalAlphabet.equals(other.nonTerminalAlphabet))
			return false;
		
		if (!terminalAlphabet.equals(other.terminalAlphabet))
			return false;
		
		if (!productions.equals(other.productions))
			return false;		
		
		return true;
	}

	@Override
	public String toString() {
		String s = "Grammar(" + this.getTerminalAlphabet() + "," + 
								this.getNonTerminalAlphabet() + "," + 
								this.getAxiom() + "," + 
								this.getProductions() + ")" + EPSILON + "=" + this.getEmpty();
		return s;
	}	

}
