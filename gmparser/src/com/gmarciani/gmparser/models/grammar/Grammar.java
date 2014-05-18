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
	
	private static final Character DEFAULT_AXIOM = 'S';
	
	public Grammar() {
		this.axiom = DEFAULT_AXIOM;
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

	@Override
	public String toString() {
		String s = "Grammar(" + this.getTerminalAlphabet() + " ; " + 
								this.getNonTerminalAlphabet() + " ; " + 
								this.getAxiom() + " ; " + 
								this.getProductions() + ")";
		return s;
	}	

}
