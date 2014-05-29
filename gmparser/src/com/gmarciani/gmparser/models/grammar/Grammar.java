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

import java.util.Objects;
import java.util.Set;

import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;
import com.gmarciani.gmparser.models.grammar.alphabet.AlphabetType;
import com.gmarciani.gmparser.models.grammar.production.Production;
import com.gmarciani.gmparser.models.grammar.production.Productions;

public class Grammar {	
	
	private Alphabet terminals;
	private Alphabet nonTerminals;
	private Productions productions;
	private Character axiom;
	
	private String empty;
	
	public static final Character AXIOM = 'S';
	public static final String EMPTY = "\u03B5"; //on terminal Ctrl+Shift+u+03b5
	
	public Grammar() {	
		this.axiom = AXIOM;
		this.terminals = new Alphabet(AlphabetType.TERMINAL);
		this.nonTerminals = new Alphabet(AlphabetType.NON_TERMINAL);
		this.nonTerminals.add(this.axiom);
		this.productions = new Productions();			
		this.empty = EMPTY;
	}	
	
	public Grammar(Character axiom) {	
		this.axiom = axiom;
		this.terminals = new Alphabet(AlphabetType.TERMINAL);
		this.nonTerminals = new Alphabet(AlphabetType.NON_TERMINAL);
		this.nonTerminals.add(this.axiom);
		this.productions = new Productions();			
		this.empty = EMPTY;
	}
	
	public Grammar(Character axiom, String empty) {	
		this.axiom = axiom;
		this.terminals = new Alphabet(AlphabetType.TERMINAL);
		this.nonTerminals = new Alphabet(AlphabetType.NON_TERMINAL);
		this.nonTerminals.add(this.axiom);
		this.productions = new Productions();			
		this.empty = empty;
	}
	
	public Alphabet getTerminals() {
		return this.terminals;
	}

	public Alphabet getNonTerminals() {
		return this.nonTerminals;
	}
	
	public Productions getProductions() {
		return this.productions;
	}
	
	public void setProductions(Productions productions) {
		this.productions = productions;
		this.rebuildAlphabet();
	}

	public Character getAxiom() {
		return this.axiom;
	}	
	
	public String getEmpty() {
		return this.empty;
	}
	
	public boolean addProduction(Production production) {
		boolean added = this.productions.add(production);
		if (added)
			this.rebuildAlphabet();
		return added;
	}	

	public boolean addProduction(String left, String right) {
		Production production = new Production(left, right);
		return this.addProduction(production);
	}
	
	public boolean addProduction(Character left, String right) {
		Production production = new Production(left, right);
		return this.addProduction(production);
	}
	
	public boolean removeProduction(Production production) {
		boolean removed = this.productions.remove(production);
		if (removed)
			this.rebuildAlphabet();
		
		return removed;
	}	
	
	public boolean retainAllProductionsWithin(Alphabet alphabet) {
		Productions withinProductions = this.productions.getProductionsWithin(alphabet);
		boolean modified = this.productions.retainAll(withinProductions);
		if (modified)
			this.rebuildAlphabet();
		
		return modified;
	}
	
	public boolean retainAllProductionsLeftWithin(Alphabet alphabet) {
		Productions withinProductions = this.productions.getProductionsLeftWithin(alphabet);
		boolean modified = this.productions.retainAll(withinProductions);
		if (modified)
			this.rebuildAlphabet();
		
		return modified;
	}
	
	public boolean retainAllProductionsRightWithin(Alphabet alphabet) {
		Productions withinProductions = this.productions.getProductionsRightWithin(alphabet);
		boolean modified = this.productions.retainAll(withinProductions);
		if (modified)
			this.rebuildAlphabet();
		
		return modified;
	}
	
	public Productions getEpsilonProductions() {
		return this.productions.getEpsilonProductions();
	}
	
	public Productions getUnitProductions() {
		return this.productions.getUnitProductions(this.nonTerminals);
	}
	
	public Productions getTrivialUnitProductions() {
		return this.productions.getTrivialUnitProductions(this.nonTerminals);
	}
	
	public Productions getNonTrivialUnitProductions() {
		return this.productions.getNonTrivialUnitProductions(this.nonTerminals);
	}
	
	public Alphabet getNullables() {
		return this.productions.getNullables();
	}
	
	public Alphabet getNullablesForProduction(Production production) {
		Alphabet target = new Alphabet(AlphabetType.NON_TERMINAL);
		Alphabet nullables = this.getNullables();
		
		for (Character nonTerminal : production.getRight().getNonTerminalAlphabet()) {
			if (nullables.contains(nonTerminal))
				target.add(nonTerminal);
		}		
		
		return target;
	}
	
	public Productions getProductionsWithin(Alphabet alphabet) {
		return this.productions.getProductionsWithin(alphabet);
	}
	
	public Productions getProductionsForNonTerminal(Character nonTerminal) {
		return this.productions.getProductionsForNonTerminal(nonTerminal);
	}
	
	public Set<String> getRightForNonTerminal(Character nonTerminal) {
		return this.productions.getRightForNonTerminal(nonTerminal);
	}
	
	public Alphabet getLeftNonTerminals() {
		return this.productions.getLeftNonTerminalAlphabet();
	}
	
	public Alphabet getRightNonTerminals() {
		return this.getRightNonTerminals();
	}
	
	public Alphabet getRightTerminals() {
		return this.productions.getRightTerminalAlphabet();
	}
	
	public Character getNewNonTerminal() {
		Alphabet totalNonTerminals = Alphabet.getTotalNonTerminals();
		totalNonTerminals.removeAll(this.nonTerminals);
		Character target = totalNonTerminals.first();
		
		return target;
		
	}
	
	private void rebuildAlphabet() {
		this.nonTerminals = this.productions.getNonTerminalAlphabet();
		this.terminals = this.productions.getTerminalAlphabet();
	}

	@Override public String toString() {
		String s = "Grammar(" + this.getTerminals() + "," + 
								this.getNonTerminals() + "," + 
								this.getAxiom() + "," + 
								this.getProductions() + ")";
		return s;
	}	
	
	@Override public boolean equals(Object obj) {
		if (this.getClass() != obj.getClass())
			return false;
		
		Grammar other = (Grammar) obj;
		
		return (this.getNonTerminals().equals(other.getNonTerminals())
				&& this.getTerminals().equals(other.getTerminals())
				&& this.getAxiom().equals(other.getAxiom())
				&& this.getProductions().equals(other.getProductions()));
	}	
	
	@Override public int hashCode() {
		return Objects.hash(this.getNonTerminals(), 
				this.getTerminals(), 
				this.getAxiom(), 
				this.getProductions());
	}
	
}
