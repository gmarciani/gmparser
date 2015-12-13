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

package com.gmarciani.gmparser.models.parser.lr.recognition;

import java.util.ArrayList;
import java.util.List;

import com.bethecoder.ascii_table.ASCIITable;
import com.gmarciani.gmparser.models.automaton.FiniteAutomaton;
import com.gmarciani.gmparser.models.automaton.state.State;
import com.gmarciani.gmparser.models.commons.function.NonDeterministicFunction;
import com.gmarciani.gmparser.models.commons.nple.Triple;
import com.gmarciani.gmparser.models.commons.set.GSet;
import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.production.Production;

/**
 * <p>The recognition matrix for the LR(1) parser.<p>
 * 
 * @see com.gmarciani.gmparser.models.parser.lr.LROneParser
 * 
 * @author Giacomo Marciani
 * @version 1.0
 */
/**
 * @author giacomo
 *
 */
public final class LROneMatrix extends NonDeterministicFunction<Integer, Character, Action> {
	
	private final Grammar grammar;
	private final FiniteAutomaton<Item> automaton;
	private final List<Production> productions;

	/**
	 * Creates a new LR(1) recognition matrix based on the specified grammar and finite automaton.
	 * 
	 * @param grammar the grammar.
	 * @param automaton the finite automaton.
	 */
	public LROneMatrix(Grammar grammar, FiniteAutomaton<Item> automaton) {
		super(automaton.getStates().getIds(), automaton.getAlphabet(), new GSet<Action>());
		super.getDomainY().add(BigProductionGraph.END_MARKER);
		this.grammar = grammar;
		this.automaton = automaton;
		this.productions = new ArrayList<Production>();
		for (Production production : this.getGrammar().getProductions())
			this.getProductions().add(production);
		this.generate();
	}
	
	/**
	 * Returns the grammar for the LR(1) recognition matrix.
	 * 
	 * @return the grammar for the LR(1) recognition matrix.
	 */
	public Grammar getGrammar() {
		return this.grammar;
	}
	
	/**
	 * Returns the finite automaton for the LR(1) recognition matrix.
	 * 
	 * @return the finite automaton for the LR(1) recognition matrix.
	 */
	public FiniteAutomaton<Item> getAutomaton() {
		return this.automaton;
	}
	
	/**
	 * Returns the productions mapping for the LR(1) recognition matrix.
	 * 
	 * @return the productions mapping for the LR(1) recognition matrix.
	 */
	public List<Production> getProductions() {
		return this.productions;
	}
		
	/**
	 * Generates the LR(1) recognition matrix.
	 */
	private void generate() {
		State<Item> finalState = this.getAutomaton().getFinals().getFirst();
		this.addAction(ActionType.ACCEPT, null, finalState.getId(), BigProductionGraph.END_MARKER);
		for (State<Item> state : this.getAutomaton().getStates()) {
			if (this.getAutomaton().isFinal(state))
				continue;
			GSet<Item> items = state.getValue();
			for (Item item : items)
				if (item.isComplete())
					for (Character symbol : item.getLookAhead())
						this.addAction(ActionType.REDUCE, this.getProductions().indexOf(item.getProduction()), state.getId(), symbol);
		}		
		for (Triple<State<Item>, Character, State<Item>> transition : this.getAutomaton().getAllTransitions()) {
			State<Item> sState = transition.getX();
			State<Item> dState = transition.getZ();
			Character symbol = transition.getY();
			if (this.getGrammar().getTerminals().contains(symbol))
				this.addAction(ActionType.SHIFT, dState.getId(), sState.getId(), symbol);
			if (this.getGrammar().getNonTerminals().contains(symbol))
				this.addAction(ActionType.GOTO, dState.getId(), sState.getId(), symbol);
		}
	}
	
	/**
	 * Adds to the recognition matrix the specified action, with the specified value, in correspondence of the specified state id and symbol.
	 * 
	 * @param type the action type.
	 * @param value the action value.
	 * @param stateId the state id.
	 * @param symbol the symbol.
	 * 
	 * @return true if the specified action has been added; false, otherwise.
	 */
	private boolean addAction(ActionType type, Integer value, Integer stateId, Character symbol) {
		return super.addAndInsert(stateId, symbol, new Action(type, value));
	}
	
	/**
	 * Returns the action stored in correspondence of the specified state id and symbol. 
	 * 
	 * @param stateId the state id.
	 * @param symbol the symbol.
	 * 
	 * @return the action stored in correspondence of the specified state id and symbol. 
	 */
	public Action getAction(Integer stateId, Character symbol) {
		if (!super.containsXY(stateId, symbol))
			return null;
		return super.getAllForXY(stateId, symbol).getFirst().getZ();
	}
	
	public String toExtendedFormattedMatrix() {
		return super.toFormattedFunction() + this.getProductionsRepresentation();
	}
	
	private String getProductionsRepresentation() {
		String header[] = {"#", "Production"};		
		if (this.getProductions().isEmpty()) {
			String data[][] = {{"null", "null"}};
			return ASCIITable.getInstance().getTable(header, ASCIITable.ALIGN_CENTER, data, ASCIITable.ALIGN_CENTER);
		}		
		String data[][] = new String[this.getProductions().size()][2];
		int r = 0;
		for (Production production : this.getProductions()) {
			data[r][0] = String.valueOf(this.getProductions().indexOf(production));
			data[r][1] = String.valueOf(production);
			r ++;
		}			
		return ASCIITable.getInstance().getTable(header, ASCIITable.ALIGN_CENTER, data, ASCIITable.ALIGN_CENTER);
	}

	

}
