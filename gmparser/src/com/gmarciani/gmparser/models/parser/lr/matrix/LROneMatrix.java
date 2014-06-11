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

package com.gmarciani.gmparser.models.parser.lr.matrix;

import java.util.ArrayList;
import java.util.List;

import com.gmarciani.gmparser.models.automaton.finite.FiniteAutomaton;
import com.gmarciani.gmparser.models.automaton.state.StateId;
import com.gmarciani.gmparser.models.commons.function.NonDeterministicFunction;
import com.gmarciani.gmparser.models.commons.set.GSet;
import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.production.Production;
import com.gmarciani.gmparser.models.parser.lr.action.LROneAction;
import com.gmarciani.gmparser.models.parser.lr.bigproduction.Item;

public class LROneMatrix extends NonDeterministicFunction<StateId, Character, LROneAction> {
	
	private final Grammar grammar;
	private final FiniteAutomaton<Item> automaton;
	private final List<Production> productions;

	public LROneMatrix(Grammar grammar, FiniteAutomaton<Item> automaton) {
		super(automaton.getStates().getIds(), automaton.getAlphabet(), new GSet<LROneAction>());
		this.grammar = grammar;
		this.automaton = automaton;
		this.productions = new ArrayList<Production>();
		for (Production production : this.getGrammar().getProductions())
			this.getProductions().add(production);
		this.generate();
	}
	
	public Grammar getGrammar() {
		return this.grammar;
	}
	
	public FiniteAutomaton<Item> getFiniteAutomaton() {
		return this.automaton;
	}
	
	public List<Production> getProductions() {
		return this.productions;
	}
	
	protected void generate() {
		
	}
	
	public boolean addAction(StateId stateId, Character symbol, LROneAction action) {
		super.getCodomain().add(action);
		return super.add(stateId, symbol, action);
	}

}
