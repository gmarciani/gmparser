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
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Pattern;

import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;
import com.gmarciani.gmparser.models.grammar.alphabet.AlphabetType;
import com.gmarciani.gmparser.models.grammar.analysis.Extension;
import com.gmarciani.gmparser.models.grammar.analysis.GrammarAnalysis;
import com.gmarciani.gmparser.models.grammar.analysis.NormalForm;
import com.gmarciani.gmparser.models.grammar.analysis.Type;
import com.gmarciani.gmparser.models.grammar.production.Member;
import com.gmarciani.gmparser.models.grammar.production.Production;
import com.gmarciani.gmparser.models.grammar.production.Productions;

/**
 * Grammar.
 * 
 * @see com.gmarciani.gmparser.models.grammar.production.Productions
 * @see com.gmarciani.gmparser.models.grammar.production.Production
 * @see com.gmarciani.gmparser.models.alphabet.Alphabet
 * @see com.gmarciani.gmparser.models.grammar.analysis.Type
 * @see com.gmarciani.gmparser.models.grammar.analysis.Extension
 * @see com.gmarciani.gmparser.models.grammar.analysis.NormalForm
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
					if (production.getRightSize() >= i + 2) {
						Character nextSymbol = production.getRight().getValueAsChars()[i + 1];
						first.addAll(this.getFirstOne(nextSymbol));
						first.remove(Grammar.EPSILON);
					}					
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
	
	public static Grammar generateGrammar(String strGrammar) {
		Grammar grammar = GrammarFactory.getInstance()
				.hasProductions(strGrammar)
				.withEpsilon(Grammar.EPSILON)
				.create();		
		return grammar;
	}
	
	public static boolean validate(String strGrammar) {
		String regex = "^([a-zA-Z]+->[a-zA-Z\\u03B5]+(\\|[a-zA-Z\\u03B5]+)*)(\\u003B([a-zA-Z]+->[a-zA-Z\\u03B5]+(\\|[a-zA-Z\\u03B5]+)*))*\\u002E$";		
		return Pattern.matches(regex, strGrammar);
	}	
	
	public GrammarAnalysis generateGrammarAnalysis() {
		return new GrammarAnalysis(this);
	}
	
	public void toAugmentedGrammar() {
		if (this.getProductions().getProductionsLeftContaining(this.getAxiom()).size() == 1
				&& this.getProductions().getProductionsRightContaining(this.getAxiom()).size() == 0) {
			this.addTerminal('$');
			return;
		}
		Character newAxiom = this.getNewNonTerminal();		
		Production augmentedProduction = new Production(new Member(newAxiom), new Member(this.getAxiom()));
		this.addProduction(augmentedProduction);	
		this.setAxiom(newAxiom);
		this.addTerminal('$');
	}	
	
	public void removeUngenerativeSymbols() {
		Alphabet generativeNonTerminals = new Alphabet(AlphabetType.NON_TERMINAL);
		Alphabet generativeAlphabet = new Alphabet(this.getTerminals(), generativeNonTerminals);
		
		boolean loop = true;
		while(loop) {
			loop = false;		
			
			generativeAlphabet.addAll(generativeNonTerminals);
			
			for (Production production : this.getProductions()) {
				if (production.isRightWithin(generativeAlphabet))
					loop = generativeNonTerminals.addAll(production.getLeft().getNonTerminalAlphabet()) ? true : loop;	
			}			
		}		
		this.retainAllProductionsWithin(generativeAlphabet);
	}
		
	public void removeUnreacheableSymbols() {	
		Alphabet reacheableTerminals = new Alphabet(AlphabetType.TERMINAL);
		Alphabet reacheableNonTerminals = new Alphabet(AlphabetType.NON_TERMINAL);	
		reacheableNonTerminals.add(this.getAxiom());
		
		boolean loop = true;
		while(loop) {
			loop = false;			
			for (Production production : this.getProductions()) {	
				if (production.isLeftWithin(reacheableNonTerminals))
					loop = reacheableNonTerminals.addAll(production.getRight().getNonTerminalAlphabet()) ? true : loop;				
				if (production.isLeftWithin(reacheableNonTerminals))
					reacheableTerminals.addAll(production.getRight().getTerminalAlphabet());
			}
		}			
		Alphabet acceptedAlphabet = new Alphabet();
		acceptedAlphabet.addAll(reacheableTerminals);
		acceptedAlphabet.addAll(reacheableNonTerminals);		
		this.retainAllProductionsWithin(acceptedAlphabet);
	}
	
	public void removeUselessSymbols() {
		this.removeUngenerativeSymbols();
		this.removeUnreacheableSymbols();
	}
	
	public void removeEpsilonProductions() {
		Alphabet nullables = this.getNullables();	
		
		for (Production production : this.getProductions()) {
			Alphabet nullablesForProduction = this.getNullablesForProduction(production);
			if (!nullablesForProduction.isEmpty()) {
				Map<Character, List<Integer>> nullablesOccurencesMap = production.getRight().getSymbolsOccurrences(nullablesForProduction);
				for (Character nullable : nullablesForProduction) {
					List<Integer> nullableOccurrences = nullablesOccurencesMap.get(nullable);
					for (int nullableOccurrence : nullableOccurrences) {
						Member lhs = production.getLeft();
						StringBuilder builder = new StringBuilder(production.getRight().getValue());
						builder.insert(nullableOccurrence, Grammar.EPSILON);
						Member rhs = new Member(builder.toString());
						Production productionWithoutNullable = new Production(lhs, rhs);
						this.addProduction(productionWithoutNullable);
					}						
				}				
				Member lhs = production.getLeft();
				Member rhs = new Member(production.getRight().getValue().replaceAll(nullables.getUnionRegex(), Grammar.EPSILON.toString()));
				Production productionWithoutNullables = new Production(lhs, rhs);
				this.addProduction(productionWithoutNullables);
			}
		}
		
		for (Production epsilonProduction : this.getEpsilonProductions())
			this.removeProduction(epsilonProduction);
		
		if (nullables.contains(this.getAxiom()))
			this.addProduction(new Production(new Member(this.getAxiom()), new Member(Grammar.EPSILON)));
	}
	
	public void removeUnitProductions() {
		this.removeEpsilonProductions();
		
		for (Production trivialUnitProduction : this.getTrivialUnitProductions())
			this.removeProduction(trivialUnitProduction);
		
		Queue<Production> queue = new ConcurrentLinkedQueue<Production>(this.getNonTrivialUnitProductions());
		while(!queue.isEmpty()) {
			Production nonTrivialUnitProduction = queue.poll();
			String lhs = nonTrivialUnitProduction.getLeft().getValue();
			Character rhs = nonTrivialUnitProduction.getRight().getValue().charAt(0);
			
			Set<String> unfoldings = this.getProductions().getRightForNonTerminal(rhs);
			for (String unfolding : unfoldings)
				this.addProduction(new Production(new Member(lhs), new Member(unfolding)));
			
			this.removeProduction(nonTrivialUnitProduction);
			
			for (Production trivialUnitProduction : this.getTrivialUnitProductions())
				this.removeProduction(trivialUnitProduction);
			
			queue.addAll(this.getNonTrivialUnitProductions());
		}
	}	
	
	public void toChomskyNormalForm() {
		this.removeEpsilonProductions();
		this.removeUnitProductions();
		
		boolean emptyGeneration = this.getNullables().contains(this.getAxiom());
		
		boolean loop = true;
		while(loop) {
			loop = false;
			
			for (Production production : this.getProductions()) {
				if (production.getRight().getSize() > 2) {
					loop = true;
					Character newNonTerminal = this.getNewNonTerminal();
					Member lhs = production.getLeft();
					Member rhs = new Member(production.getRight().getValue().substring(0, 1) + newNonTerminal);
					Production reducedProductionOne = new Production(lhs, rhs);
					
					Production reducedProductionTwo = new Production(new Member(newNonTerminal), new Member(production.getRight().getValue().substring(1)));
					
					this.removeProduction(production);
					this.addProduction(reducedProductionOne);
					this.addProduction(reducedProductionTwo);
				}
			}
		}
		
		loop = true;
		while(loop) {
			loop = false;
			
			for (Production production : this.getProductions()) {
				if (production.getRight().getSize() > 1
						&& !production.getRight().getTerminalAlphabet().isEmpty()) {
					
					loop = true;
					Character newNonTerminal = this.getNewNonTerminal();
					Character terminal = production.getRight().getTerminalAlphabet().getFirst();
					Member lhs = production.getLeft();
					Member rhs = new Member(production.getRight().getValue().replace(terminal, newNonTerminal));
					Production promotionProductionOne = new Production(lhs, rhs);
					
					Production promotionProductionTwo = new Production(new Member(newNonTerminal), new Member(terminal));
					
					this.removeProduction(production);
					this.addProduction(promotionProductionOne);
					this.addProduction(promotionProductionTwo);
				}
			}
		}
		
		if (emptyGeneration)
			this.addProduction(new Production(new Member(this.getAxiom()), new Member(this.getEpsilon())));		
	}
	
}
