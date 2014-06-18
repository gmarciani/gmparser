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
import com.gmarciani.gmparser.models.grammar.analysis.Extension;
import com.gmarciani.gmparser.models.grammar.analysis.GrammarAnalysis;
import com.gmarciani.gmparser.models.grammar.analysis.NormalForm;
import com.gmarciani.gmparser.models.grammar.analysis.Type;
import com.gmarciani.gmparser.models.grammar.factory.GrammarFactory;
import com.gmarciani.gmparser.models.grammar.production.Member;
import com.gmarciani.gmparser.models.grammar.production.Production;
import com.gmarciani.gmparser.models.grammar.production.Productions;

/**
 * <p>Grammar model.<p>
 * <p>A grammar is a 4-ple (Vt, Vn, S, P), 
 * where Vt is the terminal alphabet, 
 * Vn is the non terminal alphabet,
 * S is the axiom
 * and P is a set of productions.<p>
 * 
 * @see com.gmarciani.gmparser.models.grammar.production.Productions
 * @see com.gmarciani.gmparser.models.grammar.production.Production
 * @see com.gmarciani.gmparser.models.grammar.alphabet.Alphabet
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
	
	/**
	 * Creates a grammar with the specified axiom and epsilon.
	 * 
	 * @param axiom the axiom character of the grammar
	 * @param epsilon the empty word character of the grammar.
	 */
	public Grammar(Character axiom, Character epsilon) {	
		this.axiom = (axiom == null) ? AXIOM : axiom;
		this.terminals = new Alphabet();
		this.nonTerminals = new Alphabet();
		this.nonTerminals.add(this.axiom);
		this.productions = new Productions();			
		this.epsilon = (epsilon == null) ? EPSILON : epsilon;
	}		
	
	/**
	 * Creates a grammar as a copy of the specified grammar.
	 * 
	 * @param grammar the grammar to copy.
	 */
	public Grammar(Grammar grammar) {
		this.axiom = grammar.getAxiom();
		this.nonTerminals = new Alphabet(grammar.getNonTerminals());
		this.terminals = new Alphabet(grammar.getTerminals());
		this.productions = new Productions(grammar.getProductions());
		this.epsilon = grammar.getEpsilon();
	}
	
	/**
	 * Returns the terminal alphabet of the grammar.
	 * 
	 * @return the terminal alphabet.
	 */
	public Alphabet getTerminals() {
		return this.terminals;
	}

	/**
	 * Returns the non terminal alphabet of the grammar.
	 * 
	 * @return the non terminal alphabet.
	 */
	public Alphabet getNonTerminals() {
		return this.nonTerminals;
	}
	
	/**
	 * Returns the set of productions of the grammar.
	 * 
	 * @return the set of productions of the grammar.
	 */
	public Productions getProductions() {
		return this.productions;
	}
	
	/**
	 * Sets the set of productions of the grammar, 
	 * and rebuilds the terminal and non terminal alphabet with respect to it.
	 * 
	 * @param productions the new set of productions of the grammar.
	 */
	public void setProductions(Productions productions) {
		this.productions = productions;
		this.rebuildAlphabet();
	}

	/**
	 * Returns the axiom of the grammar.
	 * 
	 * @return the axiom of the grammar.
	 */
	public Character getAxiom() {
		return this.axiom;
	}	
	
	/**
	 * Sets the grammar axiom to the specified axiom.
	 * 
	 * @param axiom the new axiom.
	 */
	public void setAxiom(Character axiom) {
		this.axiom = axiom;
	}
	
	/**
	 * Returns the epsilon representation of the grammar.
	 * 
	 * @return the epsilon representation of the grammar.
	 */
	public Character getEpsilon() {
		return this.epsilon;
	}
	
	/**
	 * Adds to the non terminal alphabet the specified symbol.
	 * 
	 * @param symbol the symbol.
	 * 
	 * @return true if the specified symbol has been added to the non terminal alphabet; false, otherwise.
	 */
	public boolean addNonTerminal(Character symbol) {
		return this.getNonTerminals().add(symbol);
	}
	
	/**
	 * Removes from the non terminal alphabet the specified symbol.
	 * 
	 * @param symbol the symbol.
	 * 
	 * @return true if the specified symbol has been removed from the non terminal alphabet; false, otherwise.
	 */
	public boolean removeNonTerminal(Character symbol) {
		boolean removed = this.getNonTerminals().remove(symbol);
		if (removed)
			rebuildProductions();
		return removed;
	}
	
	/**
	 * Adds to the terminal alphabet the specified symbol.
	 * 
	 * @param symbol the symbol.
	 * 
	 * @return true if the specified symbol has been added to the terminal alphabet; false, otherwise.
	 */
	public boolean addTerminal(Character symbol) {
		return this.getTerminals().add(symbol);
	}
	
	/**
	 * Removes from the terminal alphabet the specified symbol.
	 * 
	 * @param symbol the symbol.
	 * 
	 * @return true if the specified symbol has been removed from the terminal alphabet; false, otherwise.
	 */
	public boolean removeTerminal(Character symbol) {
		boolean removed = this.getTerminals().remove(symbol);
		if (removed)
			rebuildProductions();
		return removed;
	}
	
	/**
	 * Adds to the set of productions the specified production.
	 * 
	 * @param production the production.
	 * 
	 * @return true if the specified production has been added to the set of productions; false, otherwise.
	 */
	public boolean addProduction(Production production) {
		boolean added = this.getProductions().add(production);
		if (added)
			this.rebuildAlphabet();
		return added;
	}		
	
	/**
	 * Removes from the set of productions the specified production.
	 * 
	 * @param production the production.
	 * 
	 * @return true if the specified production has been removed from the set of productions; false, otherwise.
	 */
	public boolean removeProduction(Production production) {
		boolean removed = this.getProductions().remove(production);
		if (removed)
			this.rebuildAlphabet();		
		return removed;
	}	
	
	/**
	 * Sets the set of productions as the subset of productions with LHS/RHS members within the specified alphabet.
	 * 
	 * @param alphabet the alphabet.
	 * 
	 * @return true if at least one productions has been removed from the set of productions; false, otherwise.
	 */
	public boolean retainAllProductionsWithin(Alphabet alphabet) {
		Productions withinProductions = this.getProductions().getProductionsWithin(alphabet);
		boolean modified = this.getProductions().retainAll(withinProductions);
		if (modified)
			this.rebuildAlphabet();		
		return modified;
	}
	
	/**
	 * Rebuilds the set of productions, with respect to terminal and non terminal alphabet modifications.
	 */
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
	
	/**
	 * Rebuilds the terminal and non terminal alphabet, with respect to the productions modifications.
	 */
	private void rebuildAlphabet() {
		this.nonTerminals = this.getProductions().getNonTerminalAlphabet();
		this.terminals = this.getProductions().getTerminalAlphabet();
	}
	
	/**
	 * Returns the grammar type, with respect to the Chomsky hierarchy.
	 * 
	 * @return the grammar type, with respect to the Chomsky hierarchy.
	 */
	public Type getType() {
		return this.getProductions().getType(this.getNonTerminals(), this.getTerminals());
	}
	
	/**
	 * Checks if the grammar is unrestricted.
	 * 
	 * @return true if the grammar is unrestricted; false, otherwise.
	 */
	public boolean isUnrestricted() {
		return this.getProductions().areUnrestricted(this.getNonTerminals(), this.getTerminals());
	}
	
	/**
	 * Checks if the grammar is context-sensitive.
	 * 
	 * @return true if the grammar is context-sensitive; false, otherwise.
	 */
	public boolean isContextSensitive() {
		return this.getProductions().areContextSensitive(this.getNonTerminals(), this.getTerminals());
	}
	
	/**
	 * Checks if the grammar is context-free.
	 * 
	 * @return true if the grammar is context-free; false, otherwise.
	 */
	public boolean isContextFree() {
		return this.getProductions().areContextFree(this.getNonTerminals(), this.getTerminals());
	}
	
	/**
	 * Checks if the grammar is regular.
	 * 
	 * @return true if the grammar is regular; false, otherwise.
	 */
	public boolean isRegular() {
		return this.getProductions().areRegular(this.getNonTerminals(), this.getTerminals());
	}
	
	/**
	 * Checks if the grammar is left linear.
	 * 
	 * @return true if the grammar is left linear; false, otherwise.
	 */
	public boolean isRegularLeftLinear() {
		return this.getProductions().areRegularLeftLinear(this.getNonTerminals(), this.getTerminals());
	}
	
	/**
	 * Checks if the grammar is right linear.
	 * 
	 * @return true if the grammar is right linear; false, otherwise.
	 */
	public boolean isRegularRightLinear() {
		return this.getProductions().areRegularRightLinear(this.getNonTerminals(), this.getTerminals());
	}	
	
	/**
	 * Returns the grammar extension, with respect to the Chomsky hierarchy.
	 * 
	 * @return the grammar extension, with respect to the Chomsky hierarchy.
	 */
	public Extension getExtension() {
		if (this.isExtended() && !this.isSExtended())
			return Extension.EXTENDED;		
		if (this.isSExtended())
			return Extension.S_EXTENDED;		
		return Extension.NONE;
	}
	
	/**
	 * Checks if the grammar is extended.
	 * 
	 * @return true if the grammar is extended; false, otherwise.
	 */
	public boolean isExtended() {
		Productions epsilonProductions = this.getEpsilonProductions();		
		return epsilonProductions.size() >= 1;
	}
	
	/**
	 * Checks if the grammar is S-extended.
	 * 
	 * @return true if the grammar is S-extended; false, otherwise.
	 */
	public boolean isSExtended() {
		Productions epsilonProductions = this.getEpsilonProductions();
		Production productionSExtension = new Production(new Member(this.getAxiom()), new Member(this.getEpsilon()));		
		return (epsilonProductions.contains(productionSExtension)
				&& epsilonProductions.size() == 1);
	}
	
	/**
	 * Returns the grammar normal forms.
	 * 
	 * @return the grammar normal forms.
	 */
	public Set<NormalForm> getNormalForm() {
		return this.getProductions().getNormalForm(this.getNonTerminals(), this.getTerminals());
	}
	
	/**
	 * Checks if the grammar is in Chomsky Normal Form.
	 * 
	 * @return true if the grammar is in Chomsky Normal Form; false, otherwise.
	 */
	public boolean isChomskyNormalForm() {
		return this.getProductions().areChomskyNormalForm(this.getNonTerminals(), this.getTerminals());
	}

	/**
	 * Checks if the grammar is in Greibach Normal Form.
	 * 
	 * @return true if the grammar is in Greibach Normal Form; false, otherwise.
	 */
	public boolean isGreibachNormalForm() {
		return this.getProductions().areGreibachNormalForm(this.getNonTerminals(), this.getTerminals());
	}
	
	/**
	 * Returns the subset of epsilon productions.
	 * 
	 * @return the subset of epsilon productions.
	 */
	public Productions getEpsilonProductions() {
		return this.getProductions().getEpsilonProductions();
	}
	
	/**
	 * Returns the subset of unit productions.
	 * 
	 * @return the subset of unit productions.
	 */
	public Productions getUnitProductions() {
		return this.getProductions().getUnitProductions(this.getNonTerminals());
	}
	
	/**
	 * Returns the subset of trivial unit productions.
	 * 
	 * @return the subset of trivial unit productions.
	 */
	public Productions getTrivialUnitProductions() {
		return this.getProductions().getTrivialUnitProductions(this.getNonTerminals());
	}
	
	/**
	 * Returns the subset of non trivial unit productions.
	 * 
	 * @return the subset of non trivial unit productions.
	 */
	public Productions getNonTrivialUnitProductions() {
		return this.getProductions().getNonTrivialUnitProductions(this.getNonTerminals());
	}
	
	/**
	 * Returns the subset of nullables symbols in the set of productions.
	 * 
	 * @return the subset of nullables symbols in the set of productions.
	 */
	public Alphabet getNullables() {
		return this.getProductions().getNullables();
	}
	
	/**
	 * Returns the subset of nullables symbols for the specified production.
	 * 
	 * @param production the production.
	 * 
	 * @return the subset of nullables symbols for the specified production.
	 */
	public Alphabet getNullablesForProduction(Production production) {
		Alphabet target = new Alphabet();
		Alphabet nullables = this.getNullables();
		
		for (Character nonTerminal : production.getRight().getNonTerminalAlphabet()) {
			if (nullables.contains(nonTerminal))
				target.add(nonTerminal);
		}		
		
		return target;
	}
	
	/**
	 * Returns the subset of ungenerative symbols in the set of productions.
	 * 
	 * @return the subset of ungenerative symbols in the set of productions.
	 */
	public Alphabet getUngeneratives() {
		return this.getProductions().getUngeneratives(this.getNonTerminals(), this.getTerminals());
	}
	
	/**
	 * Returns the subset of unreacheable symbols in the set of productions.
	 * 
	 * @return the subset of unreacheable symbols in the set of productions.
	 */
	public Alphabet getUnreacheables() {
		return this.getProductions().getUnreacheables(this.getNonTerminals(), this.getTerminals(), this.getAxiom());
	}
	
	/**
	 * Returns the subset of useless symbols in the set of productions.
	 * 
	 * @return the subset of useless symbols in the set of productions.
	 */
	public Alphabet getUseless() {
		return this.getProductions().getUseless(this.getNonTerminals(), this.getTerminals(), this.getAxiom());
	}
	
	/**
	 * Returns a new available non terminal symbols.
	 * 
	 * @return a new available non terminal symbols.
	 */
	public Character getNewNonTerminal() {
		Alphabet totalNonTerminals = Alphabet.getTotalNonTerminals();
		totalNonTerminals.removeAll(this.getNonTerminals());
		Character target = totalNonTerminals.getFirst();		
		return target;		
	}
	
	/**
	 * Generates a grammar from the specified string.
	 * 
	 * @param strGrammar the grammar, represented as a string.
	 * 
	 * @return the generated grammar.
	 */
	public static Grammar generateGrammar(String strGrammar) {
		Grammar grammar = GrammarFactory.getInstance()
				.hasProductions(strGrammar)
				.withEpsilon(Grammar.EPSILON)
				.create();		
		return grammar;
	}
	
	/**
	 * Checks if the specified string correctly represent a grammar.
	 * 
	 * @param strGrammar the grammar, represented as a string.
	 * 
	 * @return true if the specified string correctly represent a grammar.
	 */
	public static boolean validate(String strGrammar) {
		//String regex = "^([a-zA-Z]+->[a-zA-Z\\u03B5]+(\\|[a-zA-Z\\u03B5]+)*)(\\u003B([a-zA-Z]+->[a-zA-Z\\u03B5]+(\\|[a-zA-Z\\u03B5]+)*))*\\u002E$";
		String regex = "^([^->]+->[^->]+(\\|[^->]+)*)(\\u003B([^->]+->[^->]+(\\|[^->]+)*))*\\u002E$";
		return Pattern.matches(regex, strGrammar);
	}	
	
	/**
	 * Returns the grammar analysis.
	 * 
	 * @return the grammar analysis.
	 */
	public GrammarAnalysis generateGrammarAnalysis() {
		return new GrammarAnalysis(this);
	}
	
	/**
	 * <p>Computes the First1 for the specified symbol.<p>
	 * <p>The algorithm has been derived from [A. Pettorossi "Techniques for Searching, Parsing and Matching (3rd edition)", alg. 4.2.2]<p>
	 * 
	 * @param symbol the symbol.
	 * 
	 * @return the First1 for the specified symbol.
	 */
	public Alphabet getFirstOne(Character symbol) {		
		if (symbol.equals(Grammar.EPSILON)) // First1(e) = {e}.
			return new Alphabet(Grammar.EPSILON);
		if (this.getTerminals().contains(symbol)) // First1(a) = {a}.
			return new Alphabet(symbol);
		Alphabet first = new Alphabet();
		Alphabet nullables = this.getNullables();
		for (Production production : this.getProductions()) { // add First1(B1) - {e}.
			if (!production.getLeft().getValue().equals(String.valueOf(symbol)))
				continue;
			Character firstSymbol = production.getRight().getValue().toCharArray()[0];
			first.addAll(this.getFirstOne(firstSymbol));
			first.remove(Grammar.EPSILON);
			List<Character> scannedSymbols = new ArrayList<Character>();
			for (int i = 0; i < production.getRight().getSize(); i ++) { // if B1 ->* e then add First1(B2) - {e} ... if B1B2...Bk-1 -> *e then add First1(Bk) - {e}.
				Character rSymbol = production.getRight().getValue().charAt(i);
				scannedSymbols.add(rSymbol);
				if (nullables.containsAll(scannedSymbols)) {
					if (production.getRight().getSize() >= i + 2) {
						Character nextSymbol = production.getRight().getValueAsChars()[i + 1];
						first.addAll(this.getFirstOne(nextSymbol));
						first.remove(Grammar.EPSILON);
					}					
				}		
			}
			if (nullables.containsAll(scannedSymbols)) // if B1B2...Bk-1Bk ->* e then add e.
				first.add(Grammar.EPSILON);		
		}
		return first;
	}	
	
	/**
	 * <p>Converts the current grammar to the augmented grammar.<p>
	 * <p>The algorithm has been derived from [A. Pettorossi "Techniques for Searching, Parsing and Matching (3rd edition)", def. 5.1.3]<p>
	 */
	public void toAugmentedGrammar() {
		if (this.getProductions().getProductionsLeftContaining(this.getAxiom()).size() == 1 // if there is only one production for the axiom ...
				&& this.getProductions().getProductionsRightContaining(this.getAxiom()).size() == 0) { // ... and the axiom doesn't occur on RHS of any production ...
			this.addTerminal('$'); // ... just add $ to Vt.
			return;
		}
		Character newAxiom = this.getNewNonTerminal();	// Choose a new axiom S' ...
		Production augmentedProduction = new Production(new Member(newAxiom), new Member(this.getAxiom())); // ... add S' -> S to the set of productions ...
		this.addProduction(augmentedProduction);	
		this.setAxiom(newAxiom); // ... S' is the new axiom ...
		this.addTerminal('$'); // ... add $ to Vt.
	}	
	
	/**
	 * <p>Removes from the current grammar all the ungenerative symbols.<p>
	 * <p>The algorithm has been derived from [A. Pettorossi "Automata Theory and Formal Languages (3rd edition)", alg. 3.5.1]<p>
	 */
	public void removeUngenerativeSymbols() {
		Alphabet generativeNonTerminals = new Alphabet(); // Vn' = {}.
		Alphabet generativeAlphabet = new Alphabet(this.getTerminals(), generativeNonTerminals);
		
		boolean loop = true;
		while(loop) { // Until no new non terminal symbol can be added to Vn' ...
			loop = false;					
			generativeAlphabet.addAll(generativeNonTerminals);			
			for (Production production : this.getProductions()) 
				if (production.getRight().isWithin(generativeAlphabet)) // ... if there exists a production A -> a, with a in (Vt u Vn') ...
					loop = generativeNonTerminals.addAll(production.getLeft().getNonTerminalAlphabet()) ? true : loop;	// ... then add A to Vn'.
		}		
		this.retainAllProductionsWithin(generativeAlphabet); // P' = {p | p is a production with only generative symbols}.
	}
		
	/**
	 * <p>Removes from the current grammar all the unreacheable symbols.<p>
	 * <p>The algorithm has been derived from [A. Pettorossi "Automata Theory and Formal Languages (3rd edition)", alg. 3.5.3]<p>
	 */
	public void removeUnreacheableSymbols() {	
		Alphabet reacheableTerminals = new Alphabet(); // Vt' = {}.
		Alphabet reacheableNonTerminals = new Alphabet(this.getAxiom()); // Vn' = {S}.
		
		boolean loop = true;
		while(loop) { // Until no new non terminal symbol can be added to Vn' ...
			loop = false;			
			for (Production production : this.getProductions()) {	
				if (production.getLeft().isWithin(reacheableNonTerminals)) // ... if there exists a production A -> aBb, with A in Vn', B in Vn, and a,b in (Vt u Vn)* ...
					loop = reacheableNonTerminals.addAll(production.getRight().getNonTerminalAlphabet()) ? true : loop; // ... then add B to Vn'.
				if (production.getLeft().isWithin(reacheableNonTerminals)) // ... if there exists a production abc, with A in Vn', b in Vt, and a,c in (Vt u Vn)* ...
					reacheableTerminals.addAll(production.getRight().getTerminalAlphabet()); // ... then add b to Vt'.
			}
		}			
		Alphabet acceptedAlphabet = new Alphabet();
		acceptedAlphabet.addAll(reacheableTerminals);
		acceptedAlphabet.addAll(reacheableNonTerminals);		
		this.retainAllProductionsWithin(acceptedAlphabet); // P' = {p | p is a production with only reacheable symbols}.
	}
	
	/**
	 * <p>Removes from the current grammar all the useless symbols.<p>
	 * <p>The algorithm has been derived from [A. Pettorossi "Automata Theory and Formal Languages (3rd edition)", the. 3.5.6]<p>
	 */
	public void removeUselessSymbols() {
		this.removeUngenerativeSymbols(); // first, remove ungenerative symbols ...
		this.removeUnreacheableSymbols(); // ... then remove unreacheable symbols.
	}
	
	/**
	 * <p>Removes from the current grammar all the epsilon productions.<p>
	 * <p>The algorithm has been derived from [A. Pettorossi "Automata Theory and Formal Languages (3rd edition)", alg. 3.5.8]<p>
	 */
	public void removeEpsilonProductions() {
		Alphabet nullables = this.getNullables(); // N = {n | n is a nullable symbol}.		
		for (Production production : this.getProductions()) {
			Alphabet nullablesForProduction = this.getNullablesForProduction(production);
			if (!nullablesForProduction.isEmpty()) {
				Map<Character, List<Integer>> nullablesOccurencesMap = production.getRight().getSymbolsOccurrences(nullablesForProduction);
				for (Character nullable : nullablesForProduction) { // mapped replacement (for language conservation) of nullables in every production.
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
		
		for (Production epsilonProduction : this.getEpsilonProductions()) // Delete all epsilon productions ...
			this.removeProduction(epsilonProduction);
		
		if (nullables.contains(this.getAxiom())) // ... with the exception of S -> e, if S -> e was in P.
			this.addProduction(new Production(new Member(this.getAxiom()), new Member(Grammar.EPSILON)));
	}
	
	/**
	 * <p>Removes from the current grammar all the unit productions.<p>
	 * <p>The algorithm has been derived from [A. Pettorossi "Automata Theory and Formal Languages (3rd edition)", alg. 3.5.11]<p>
	 */
	public void removeUnitProductions() {
		this.removeEpsilonProductions(); // The input grammar must be a context-free without epsilon productions.
		
		for (Production trivialUnitProduction : this.getTrivialUnitProductions()) // Remove every trivial unit production.
			this.removeProduction(trivialUnitProduction);
		
		Queue<Production> queue = new ConcurrentLinkedQueue<Production>(this.getNonTrivialUnitProductions()); // Enqueue every non trivial production ...
		while(!queue.isEmpty()) {
			Production nonTrivialUnitProduction = queue.poll(); // ... extract from the queue a unit production A -> B...
			String lhs = nonTrivialUnitProduction.getLeft().getValue();
			Character rhs = nonTrivialUnitProduction.getRight().getValue().charAt(0);
			
			Productions productions = this.getProductions().getProductionsLeftContaining(rhs);
			for (Production production : productions)
				this.addProduction(new Production(new Member(lhs), production.getRight())); // ... unfold B in A -> B			
			this.removeProduction(nonTrivialUnitProduction);
			
			for (Production trivialUnitProduction : this.getTrivialUnitProductions()) // ... remove every (eventually generated) trivial unit production ...
				this.removeProduction(trivialUnitProduction);			
			queue.addAll(this.getNonTrivialUnitProductions()); // ... and add to the queue every (eventually generated) non trivial unit production.
		}
	}	
	
	/**
	 * <p>Converts the current grammar to the equivalent grammar in Chomsky Normal Form.<p>
	 * <p>Note that the current grammar should be a context-free grammar.<p>
	 * <p>The algorithm has been derived from [A. Pettorossi "Automata Theory and Formal Languages (3rd edition)", alg. 3.6.3]<p>
	 */
	public void toChomskyNormalForm() {
		this.removeEpsilonProductions(); // Simplify the grammar, removing the epsilon productions ...
		this.removeUnitProductions(); // ... and unit productions.
		
		boolean emptyGeneration = this.getNullables().contains(this.getAxiom()); // is e in L(G)?
		
		boolean loop = true;
		while(loop) { // Until there is no new production p to reduce ...
			loop = false;			
			for (Production production : this.getProductions()) {
				if (production.getRight().getSize() > 2) { // ... reduce the order of p.
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
		while(loop) { // Until there is no new terminal symbol a to promote ...
			loop = false;			
			for (Production production : this.getProductions()) {
				if (production.getRight().getSize() > 1
						&& !production.getRight().getTerminalAlphabet().isEmpty()) {	// ... promote a.		
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
		
		this.removeUselessSymbols();
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
