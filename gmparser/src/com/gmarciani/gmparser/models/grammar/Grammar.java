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
	
	public Grammar(Grammar grammar) {
		this.axiom = grammar.getAxiom();
		this.nonTerminals = new Alphabet(grammar.getNonTerminals());
		this.terminals = new Alphabet(grammar.getTerminals());
		this.productions = new Productions(grammar.getProductions());
		this.empty = grammar.getEmpty();
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
		boolean added = this.getProductions().add(production);
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
		boolean removed = this.getProductions().remove(production);
		if (removed)
			this.rebuildAlphabet();
		
		return removed;
	}	
	
	public boolean retainAllProductionsWithin(Alphabet alphabet) {
		Productions withinProductions = this.getProductions().getProductionsWithin(alphabet);
		boolean modified = this.getProductions().retainAll(withinProductions);
		if (modified)
			this.rebuildAlphabet();
		
		return modified;
	}
	
	public boolean retainAllProductionsLeftWithin(Alphabet alphabet) {
		Productions withinProductions = this.getProductions().getProductionsLeftWithin(alphabet);
		boolean modified = this.getProductions().retainAll(withinProductions);
		if (modified)
			this.rebuildAlphabet();
		
		return modified;
	}
	
	public boolean retainAllProductionsRightWithin(Alphabet alphabet) {
		Productions withinProductions = this.getProductions().getProductionsRightWithin(alphabet);
		boolean modified = this.getProductions().retainAll(withinProductions);
		if (modified)
			this.rebuildAlphabet();
		
		return modified;
	}
	
	public Type getType() {
		return this.getProductions().getType(this.getNonTerminals(), this.getTerminals());
	}
	
	public boolean isUnrestricted() {
		return this.getProductions().isUnrestricted(this.getNonTerminals(), this.getTerminals());
	}
	
	public boolean isContextSensitive() {
		return this.getProductions().isContextSensitive(this.getNonTerminals(), this.getTerminals());
	}
	
	public boolean isContextFree() {
		return this.getProductions().isContextFree(this.getNonTerminals(), this.getTerminals());
	}
	
	public boolean isRegular() {
		return this.getProductions().isRegular(this.getNonTerminals(), this.getTerminals());
	}
	
	public boolean isRegularLeftLinear() {
		return this.getProductions().isRegularLeftLinear(this.getNonTerminals(), this.getTerminals());
	}
	
	public boolean isRegularRightLinear() {
		return this.getProductions().isRegularRightLinear(this.getNonTerminals(), this.getTerminals());
	}	
	
	public boolean isExtended() {
		Productions epsilonProductions = this.getEpsilonProductions();
		
		return epsilonProductions.size() >= 1;
	}
	
	public boolean isSExtended() {
		Productions epsilonProductions = this.getEpsilonProductions();
		Production productionSExtension = new Production(this.getAxiom(), this.getEmpty());
		
		return (epsilonProductions.contains(productionSExtension)
				&& epsilonProductions.size() == 1);
	}
	
	public Set<NormalForm> getNormalForm() {
		return this.getProductions().getNormalForm(this.getNonTerminals(), this.getTerminals());
	}
	
	public boolean isChomskyNormalForm() {
		return this.getProductions().isChomskyNormalForm(this.getNonTerminals(), this.getTerminals());
	}

	public boolean isGreibachNormalForm() {
		return this.getProductions().isGreibachNormalForm(this.getNonTerminals(), this.getTerminals());
	}
	
	public Productions getEpsilonProductions() {
		return this.getProductions().getEpsilonProductions();
	}
	
	public Productions getUnitProductions() {
		return this.getProductions().getUnitProductions(this.getNonTerminals());
	}
	
	public Productions getTrivialUnitProductions() {
		return this.getProductions().getTrivialUnitProductions(this.getNonTerminals());
	}
	
	public Productions getNonTrivialUnitProductions() {
		return this.getProductions().getNonTrivialUnitProductions(this.getNonTerminals());
	}
	
	public Alphabet getNullables() {
		return this.getProductions().getNullables();
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
		return this.getProductions().getProductionsWithin(alphabet);
	}
	
	public Productions getProductionsForNonTerminal(Character nonTerminal) {
		return this.getProductions().getProductionsForNonTerminal(nonTerminal);
	}
	
	public Set<String> getRightForNonTerminal(Character nonTerminal) {
		return this.getProductions().getRightForNonTerminal(nonTerminal);
	}
	
	public Alphabet getLeftNonTerminals() {
		return this.getProductions().getLeftNonTerminalAlphabet();
	}
	
	public Alphabet getRightNonTerminals() {
		return this.getRightNonTerminals();
	}
	
	public Alphabet getRightTerminals() {
		return this.getProductions().getRightTerminalAlphabet();
	}
	
	public Character getNewNonTerminal() {
		Alphabet totalNonTerminals = Alphabet.getTotalNonTerminals();
		totalNonTerminals.removeAll(this.getNonTerminals());
		Character target = totalNonTerminals.first();
		
		return target;
		
	}
	
	private void rebuildAlphabet() {
		this.nonTerminals = this.getProductions().getNonTerminalAlphabet();
		this.terminals = this.getProductions().getTerminalAlphabet();
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
