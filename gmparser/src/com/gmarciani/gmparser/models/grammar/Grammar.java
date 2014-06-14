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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;
import com.gmarciani.gmparser.models.grammar.alphabet.AlphabetType;
import com.gmarciani.gmparser.models.grammar.analysis.GrammarAnalysis;
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
	
	public Grammar(Character axiom, Character epsilon) {	
		this.axiom = (axiom == null) ? AXIOM : axiom;
		this.terminals = new Alphabet(AlphabetType.TERMINAL);
		this.nonTerminals = new Alphabet(AlphabetType.NON_TERMINAL);
		this.nonTerminals.add(this.axiom);
		this.productions = new Productions();			
		this.epsilon = (epsilon == null) ? EPSILON : epsilon;
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
	
	public boolean addNonTerminal(Character symbol) {
		return this.getNonTerminals().add(symbol);
	}
	
	public boolean removeNonTerminal(Character symbol) {
		boolean removed = this.getNonTerminals().remove(symbol);
		if (removed)
			rebuildProductions();
		return removed;
	}
	
	public boolean addTerminal(Character terminal) {
		return this.getTerminals().add(terminal);
	}
	
	public boolean removeTerminal(Character symbol) {
		boolean removed = this.getTerminals().remove(symbol);
		if (removed)
			rebuildProductions();
		return removed;
	}
	
	public boolean addProduction(Production production) {
		boolean added = this.getProductions().add(production);
		if (added)
			this.rebuildAlphabet();
		return added;
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
	
	public Type getType() {
		return this.getProductions().getType(this.getNonTerminals(), this.getTerminals());
	}
	
	public boolean isUnrestricted() {
		return this.getProductions().areUnrestricted(this.getNonTerminals(), this.getTerminals());
	}
	
	public boolean isContextSensitive() {
		return this.getProductions().areContextSensitive(this.getNonTerminals(), this.getTerminals());
	}
	
	public boolean isContextFree() {
		return this.getProductions().areContextFree(this.getNonTerminals(), this.getTerminals());
	}
	
	public boolean isRegular() {
		return this.getProductions().areRegular(this.getNonTerminals(), this.getTerminals());
	}
	
	public boolean isRegularLeftLinear() {
		return this.getProductions().areRegularLeftLinear(this.getNonTerminals(), this.getTerminals());
	}
	
	public boolean isRegularRightLinear() {
		return this.getProductions().areRegularRightLinear(this.getNonTerminals(), this.getTerminals());
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
		Production productionSExtension = new Production(new Member(this.getAxiom()), new Member(this.getEpsilon()));
		
		return (epsilonProductions.contains(productionSExtension)
				&& epsilonProductions.size() == 1);
	}
	
	public Set<NormalForm> getNormalForm() {
		return this.getProductions().getNormalForm(this.getNonTerminals(), this.getTerminals());
	}
	
	public boolean isChomskyNormalForm() {
		return this.getProductions().areChomskyNormalForm(this.getNonTerminals(), this.getTerminals());
	}

	public boolean isGreibachNormalForm() {
		return this.getProductions().areGreibachNormalForm(this.getNonTerminals(), this.getTerminals());
	}
	
	public Productions getEpsilonProductions() {
		return this.getProductions().getEpsilonProductions(this.getEpsilon());
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
			Character firstSymbol = production.getRight().getValue().toCharArray()[0];
			first.addAll(this.getFirstOne(firstSymbol));
			first.remove(Grammar.EPSILON);
			List<Character> scannedSymbols = new ArrayList<Character>();
			for (int i = 0; i < production.getRightSize(); i ++) {
				Character rSymbol = production.getRight().getValue().charAt(i);
				scannedSymbols.add(rSymbol);
				if (nullables.containsAll(scannedSymbols)) {
					Character nextSymbol = production.getRight().getValue().toCharArray()[i + 1];
					first.addAll(this.getFirstOne(nextSymbol));
					first.remove(Grammar.EPSILON);
				}		
			}
			if (nullables.containsAll(scannedSymbols)) {
				first.add(Grammar.EPSILON);
			}
				
		}
		return first;
	}
	
	private void rebuildProductions() {
		Productions toRemove = new Productions();
		for (Production production : this.getProductions())
			if (!this.getNonTerminals().containsAll(production.getNonTerminalAlphabet())
					|| !this.getTerminals().containsAll(production.getTerminalAlphabet()))
				toRemove.add(production);
		for (Production production : toRemove)
			this.getProductions().remove(production);
		this.rebuildAlphabet();
	}
	
	private void rebuildAlphabet() {
		this.nonTerminals = this.getProductions().getNonTerminalAlphabet();
		this.terminals = this.getProductions().getTerminalAlphabet();
	}
	
	public static boolean validate(String strGrammar) {
		String regex = "^([a-zA-Z]+->[a-zA-Z\\u03B5]+(\\|[a-zA-Z\\u03B5]+)*)(\\u003B([a-zA-Z]+->[a-zA-Z\\u03B5]+(\\|[a-zA-Z\\u03B5]+)*))*\\u002E$";
		
		return Pattern.matches(regex, strGrammar);
	}
	
	public Grammar generateAugmentedGrammar() {
		Grammar augmentedGrammar = new Grammar(this);
		Character newNonTerminalForOldAxiom = augmentedGrammar.getNewNonTerminal();
		Character newAxiom = 'S';
		
		augmentedGrammar.renameNonTerminal(augmentedGrammar.getAxiom(), newNonTerminalForOldAxiom);
		Production augmentedProduction = new Production(new Member(newAxiom), new Member(newNonTerminalForOldAxiom));
		augmentedGrammar.addProduction(augmentedProduction);
		
		augmentedGrammar.addTerminal('$');
		
		return augmentedGrammar;
	}
	
	public GrammarAnalysis getGrammarAnalysis() {
		return new GrammarAnalysis(this);
	}
	
	public static Grammar generateGrammar(String strGrammar) {
		Grammar grammar = GrammarFactory.getInstance()
				.hasProductions(strGrammar)
				.withEpsilon(Grammar.EPSILON)
				.create();		
		return grammar;
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
