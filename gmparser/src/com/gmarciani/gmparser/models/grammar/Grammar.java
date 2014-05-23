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

import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;
import com.gmarciani.gmparser.models.grammar.production.Production;
import com.gmarciani.gmparser.models.grammar.production.Productions;

public class Grammar {	
	
	private Alphabet terminals;
	private Alphabet nonTerminals;
	private Productions productions;
	private Character axiom;
	
	private String emptyString;
	
	public static final Character AXIOM = 'S';
	public static final String EMPTY_STRING = "\u03B5"; //on terminal Ctrl+Shift+u+03b5
	
	public Grammar() {	
		this.axiom = AXIOM;
		this.terminals = new Alphabet();
		this.nonTerminals = new Alphabet();
		this.nonTerminals.add(this.axiom);
		this.productions = new Productions();			
		this.emptyString = EMPTY_STRING;
	}	
	
	public Grammar(char axiom) {	
		this.axiom = axiom;
		this.terminals = new Alphabet();
		this.nonTerminals = new Alphabet();
		this.nonTerminals.add(axiom);
		this.productions = new Productions();			
		this.emptyString = EMPTY_STRING;
	}
	
	public Grammar(char axiom, String emptyString) {	
		this.axiom = axiom;
		this.terminals = new Alphabet();
		this.nonTerminals = new Alphabet();
		this.nonTerminals.add(axiom);
		this.productions = new Productions();			
		this.emptyString = emptyString;
	}
	
	public Alphabet getTerminals() {
		return this.terminals;
	}
	
	public void setTerminals(Alphabet terminals) {
		this.terminals = terminals;
	}

	public Alphabet getNonTerminals() {
		return this.nonTerminals;
	}
	
	public void setNonTerminals(Alphabet nonTerminals) {
		this.nonTerminals = nonTerminals;
	}
	
	public Productions getProductions() {
		return this.productions;
	}
	
	public void setProductions(Productions productions) {
		this.productions = productions;
	}

	public Character getAxiom() {
		return this.axiom;
	}	
	
	public void setAxiom(Character axiom) {
		this.axiom = axiom;
	}
	
	public String getEmptyString() {
		return this.emptyString;
	}
	
	public void setEmptyString(String emptyString) {
		this.emptyString = emptyString;
	}
	
	public void addProduction(Production production) {
		this.productions.add(production);
		this.addSymbols(production.getLeft() + production.getRight());
	}	

	public void addProduction(String left, String right) {
		Production production = new Production(left, right);
		this.productions.add(production);
		this.addSymbols(left + right);
	}
	
	private void addSymbols(String symbols) {
		String cleanSymbols = symbols.replaceAll(this.getEmptyString(), "");
		for (char symbol : cleanSymbols.toCharArray()) {
			if (Character.isUpperCase(symbol)) {
				this.nonTerminals.add(symbol);
			} else {
				this.terminals.add(symbol);
			}
		}
	}

	public void addTerminal(Character symbol) {
		this.terminals.add(symbol);
	}
	
	public void addNonTerminal(Character symbol) {
		this.nonTerminals.add(symbol);
	}	

	@Override
	public String toString() {
		String s = "Grammar(" + this.getTerminals() + "," + 
								this.getNonTerminals() + "," + 
								this.getAxiom() + "," + 
								this.getProductions() + ")";
		return s;
	}	
	
	@Override
	public boolean equals(Object obj) {
		if (this.getClass() != obj.getClass())
			return false;
		
		Grammar other = (Grammar) obj;
		
		if (!nonTerminals.equals(other.nonTerminals))
			return false;
		
		if (!terminals.equals(other.terminals))
			return false;
		
		if (!productions.equals(other.productions))
			return false;		
		
		return true;
	}

}
