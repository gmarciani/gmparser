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
import java.util.regex.Pattern;

import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;
import com.gmarciani.gmparser.models.grammar.alphabet.AlphabetType;
import com.gmarciani.gmparser.models.grammar.production.Member;
import com.gmarciani.gmparser.models.grammar.production.Production;
import com.gmarciani.gmparser.models.grammar.production.Productions;

/**
 * Grammar.
 * 
 * @see com.gmarciani.gmparser.models.grammar.production.Productions
 * @see com.gmarciani.gmparser.models.grammar.production.Production
 * @see com.gmarciani.gmparser.models.alphabet.Alphabet
 * @see com.gmarciani.gmparser.models.grammar.Type
 * @see com.gmarciani.gmparser.models.grammar.Extension
 * @see com.gmarciani.gmparser.models.grammar.NormalForm
 * 
 * @author Giacomo Marciani
 * @version 1.0
 */
public class Grammar {	
	
	private Alphabet terminals;
	private Alphabet nonTerminals;
	private Productions productions;
	private Character axiom;
	
	private Character epsilon;
	
	public static final Character AXIOM = 'S';
	public static final Character EPSILON = Character.toChars(Integer.parseInt("03B5", 16))[0]; //on terminal Ctrl+Shift+u+03b5
	
	public Grammar() {	
		this.axiom = AXIOM;
		this.terminals = new Alphabet(AlphabetType.TERMINAL);
		this.nonTerminals = new Alphabet(AlphabetType.NON_TERMINAL);
		this.nonTerminals.add(this.axiom);
		this.productions = new Productions();			
		this.epsilon = EPSILON;
	}	
	
	public Grammar(Character axiom) {	
		this.axiom = axiom;
		this.terminals = new Alphabet(AlphabetType.TERMINAL);
		this.nonTerminals = new Alphabet(AlphabetType.NON_TERMINAL);
		this.nonTerminals.add(this.axiom);
		this.productions = new Productions();			
		this.epsilon = EPSILON;
	}
	
	public Grammar(Character axiom, Character empty) {	
		this.axiom = axiom;
		this.terminals = new Alphabet(AlphabetType.TERMINAL);
		this.nonTerminals = new Alphabet(AlphabetType.NON_TERMINAL);
		this.nonTerminals.add(this.axiom);
		this.productions = new Productions();			
		this.epsilon = empty;
	}
	
	public Grammar(Grammar grammar) {
		this.axiom = grammar.getAxiom();
		this.nonTerminals = new Alphabet(grammar.getNonTerminals());
		this.terminals = new Alphabet(grammar.getTerminals());
		this.productions = new Productions(grammar.getProductions());
		this.epsilon = grammar.getEpsilon();
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
	
	public void setAxiom(Character axiom) {
		this.axiom = axiom;
	}
	
	public Character getEpsilon() {
		return this.epsilon;
	}
	
	public void setEpsilon(Character epsilon) {
		this.epsilon = epsilon;
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
	
	public boolean addProduction(String left, Character right) {
		Production production = new Production(left, right);
		return this.addProduction(production);
	}
	
	public boolean addProduction(Character left, Character right) {
		Production production = new Production(left, right);
		return this.addProduction(production);
	}
	
	public boolean addNonTerminal(Character nonTerminal) {
		return this.getNonTerminals().add(nonTerminal);
	}
	
	public boolean addTerminal(Character terminal) {
		return this.getTerminals().add(terminal);
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
	
	public Extension getExtension() {
		if (this.isExtended() && !this.isSExtended())
			return Extension.EXTENDED;
		
		if (this.isSExtended())
			return Extension.S_EXTENDED;
		
		return Extension.NONE;
	}
	
	public boolean isExtended() {
		Productions epsilonProductions = this.getEpsilonProductions();
		
		return epsilonProductions.size() >= 1;
	}
	
	public boolean isSExtended() {
		Productions epsilonProductions = this.getEpsilonProductions();
		Production productionSExtension = new Production(this.getAxiom(), this.getEpsilon());
		
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
	
	public Alphabet getUngeneratives() {
		return this.getProductions().getUngeneratives(this.getNonTerminals(), this.getTerminals());
	}
	
	public Alphabet getUnreacheables() {
		return this.getProductions().getUnreacheables(this.getNonTerminals(), this.getTerminals(), this.getAxiom());
	}
	
	public Alphabet getUseless() {
		return this.getProductions().getUseless(this.getNonTerminals(), this.getTerminals(), this.getAxiom());
	}
	
	public Productions getProductionsWithLeft(Member left) {
		return this.getProductions().getProductionsWithLeft(left);
	}
	
	public Productions getProductionsWithRight(Member right) {
		return this.getProductions().getProductionsWithRight(right);
	}
	
	public Productions getProductionsWithin(Alphabet alphabet) {
		return this.getProductions().getProductionsWithin(alphabet);
	}
	
	public Productions getProductionsLeftWithin(Alphabet alphabet) {
		return this.getProductions().getProductionsLeftWithin(alphabet);
	}
	
	public Productions getProductionsRightWithin(Alphabet alphabet) {
		return this.getProductions().getProductionsRightWithin(alphabet);
	}
	
	public Productions getProductionsContaining(Alphabet alphabet) {
		return this.getProductions().getProductionsContaining(alphabet);
	}
	
	public Productions getProductionsLeftContaining(Alphabet alphabet) {
		return this.getProductions().getProductionsLeftContaining(alphabet);
	}
	
	public Productions getProductionsRightContaining(Alphabet alphabet) {
		return this.getProductions().getProductionsRightContaining(alphabet);
	}
	
	public Productions getProductionsContaining(Character symbol) {
		return this.getProductions().getProductionsContaining(symbol);
	}
	
	public Productions getProductionsLeftContaining(Character symbol) {
		return this.getProductions().getProductionsLeftContaining(symbol);
	}
	
	public Productions getProductionsRightContaining(Character symbol) {
		return this.getProductions().getProductionsRightContaining(symbol);
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
		Character target = totalNonTerminals.getFirst();
		
		return target;
		
	}
	
	public void renameNonTerminal(Character oldNonTerminal, Character newNonTerminal) {
		for (Production production : this.getProductions()) {
			if (production.isLeftContaining(oldNonTerminal))
				production.getLeft().replace(oldNonTerminal, newNonTerminal);
			
			if (production.isRightContaining(oldNonTerminal))
				production.getRight().replace(oldNonTerminal, newNonTerminal);
		}
		
		if (this.getAxiom().equals(oldNonTerminal))
			this.setAxiom(newNonTerminal);
		
		this.rebuildAlphabet();
	}
	
	public Alphabet getFirstOne(Character symbol) {		
		if (symbol.equals(Grammar.EPSILON))
			return new Alphabet(Grammar.EPSILON);
		if (this.getTerminals().contains(symbol))
			return new Alphabet(symbol);
		Alphabet first = new Alphabet();
		Alphabet nullables = this.getNullables();
		for (Production production : this.getProductions()) {
			if (!production.getLeft().getValue().equals(String.valueOf(symbol)))
				continue;
			for (int i = 0; i < production.getRightSize(); i ++) {	
				Character pSymbol = production.getRight().getValue().charAt(i);
				if (!nullables.contains(pSymbol)) {
					Alphabet newFirst = this.getFirstOne(pSymbol);
					first.addAll(newFirst);
					first.remove(Grammar.EPSILON);
					break;
				}				
			}
			if (nullables.containsAll(production.getRight().getNonTerminalAlphabet()))
				first.add(Grammar.EPSILON);
		}
		return first;
	}
	
	private void rebuildAlphabet() {
		this.nonTerminals = this.getProductions().getNonTerminalAlphabet();
		this.terminals = this.getProductions().getTerminalAlphabet();
	}
	
	public static boolean validate(String strGrammar) {
		String regex = "^([a-zA-Z]+->[a-zA-Z\\u03B5]+(\\|[a-zA-Z\\u03B5]+)*)(\\u003B([a-zA-Z]+->[a-zA-Z\\u03B5]+(\\|[a-zA-Z\\u03B5]+)*))*\\u002E$";
		
		return Pattern.matches(regex, strGrammar);
	}
	
	public static Grammar generateAugmentedGrammar(Grammar grammar) {
		Grammar augmentedGrammar = new Grammar(grammar);
		Character newNonTerminalForOldAxiom = augmentedGrammar.getNewNonTerminal();
		Character newAxiom = 'S';
		
		augmentedGrammar.renameNonTerminal(augmentedGrammar.getAxiom(), newNonTerminalForOldAxiom);
		Production augmentedProduction = new Production(newAxiom, newNonTerminalForOldAxiom);
		augmentedGrammar.addProduction(augmentedProduction);
		
		augmentedGrammar.addTerminal('$');
		
		return augmentedGrammar;
	}

	@Override public String toString() {
		return "Grammar(" + 
					this.getTerminals() + "," + 
					this.getNonTerminals() + "," + 
					this.getAxiom() + "," + 
					this.getProductions() + ")";
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
