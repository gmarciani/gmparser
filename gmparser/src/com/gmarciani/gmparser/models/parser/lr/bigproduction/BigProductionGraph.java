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

package com.gmarciani.gmparser.models.parser.lr.bigproduction;

import com.gmarciani.gmparser.models.automaton.graph.TransitionGraph;
import com.gmarciani.gmparser.models.automaton.state.State;
import com.gmarciani.gmparser.models.automaton.state.StateId;
import com.gmarciani.gmparser.models.commons.set.GSet;
import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.production.Production;

public class BigProductionGraph extends TransitionGraph<Item> {
	
	private Grammar grammar;
	
	public BigProductionGraph(Grammar grammar) {
		super();		
		this.grammar = new Grammar(grammar);
		this.generate();		
	}
	
	public Grammar getGrammar() {
		return this.grammar;
	}
	
	protected void generate() {
		GSet<Item> items = new GSet<Item>();
		for (Production production : this.getGrammar().getProductions())
			items.addAll(Item.generateItems(production));
		int id = 1;
		for (Item item : items) {
			super.addState(new State<Item>(new StateId(id), item));
			id ++;
			if (item.hasNextCharacter())
				super.addSymbol(item.getNextCharacter());
		}
		super.addSymbol(Grammar.EPSILON);
		for (State<Item> sState : super.getStates()) {
			Item sItem = sState.getValue().getFirst();
			for (State<Item> dState : super.getStates()) {
				Item dItem = dState.getValue().getFirst();
				if (sItem.getProduction().equals(dItem.getProduction())
						&& dItem.getDot().equals(sItem.getDot() + 1))
					super.addTransition(sState, dState, sItem.getNextCharacter());
				if (sItem.hasNextCharacter()
						&& !dItem.hasJustReadCharacter()
						&& sItem.getNextCharacter().equals(dItem.getProduction().getLeft().getValue().charAt(0)))
						super.addTransition(sState, dState, Grammar.EPSILON);
			}
		}	
		for (State<Item> state : super.getStates()) {
			Item item = state.getValue().getFirst();
			if (item.getProduction().getLeftSize() == 1
					&& item.getProduction().getLeft().getValue().charAt(0) == this.getGrammar().getAxiom()
					&& item.isStart())
				super.addAsInitialState(state);
			if (item.getProduction().getLeftSize() == 1
					&& item.getProduction().getLeft().getValue().charAt(0) == this.getGrammar().getAxiom()
					&& item.isComplete())
				super.addAsFinalState(state);
		}
	}	

}
