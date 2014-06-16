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
import com.gmarciani.gmparser.models.commons.nple.Pair;
import com.gmarciani.gmparser.models.commons.set.GSet;
import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;
import com.gmarciani.gmparser.models.grammar.production.Member;
import com.gmarciani.gmparser.models.grammar.production.Production;
import com.gmarciani.gmparser.models.grammar.production.Productions;

public class BigProductionGraph extends TransitionGraph<Item> {	
	
	private Grammar grammar;
	
	public static final Character END_MARKER = '$';
	
	public BigProductionGraph(Grammar grammar) {
		super();		
		this.grammar = new Grammar(grammar);
		this.generate();		
	}
	
	public Grammar getGrammar() {
		return this.grammar;
	}
	
	private void generate() {
		GSet<Pair<Item, Item>> epsilonTransitions = new GSet<Pair<Item, Item>>();
		GSet<Item> items = new GSet<Item>();
		items.addAll(this.generateAxiomItems());
		boolean loop = true;
		while(loop) {
			loop = false;
			for (Item item : items) {
				if (item.isComplete()
						|| !this.getGrammar().getNonTerminals().contains(item.getNextCharacter()))
					continue;
				Character nextNonTerminal = item.getNextCharacter();
				Productions nextProductions = this.getGrammar().getProductions().getProductionsLeftEqual(new Member(nextNonTerminal));
				for (Production nextProduction : nextProductions) {
					GSet<Item> nextItems = generateItemsWithoutLookAhead(nextProduction);
					Alphabet lookAheadSet = generateLookAheadSet(item);
					for (Item nextItem : nextItems) {
						if (nextItem.isStart())
							epsilonTransitions.add(new Pair<Item, Item>(item, nextItem));
						nextItem.getLookAhead().addAll(lookAheadSet);
						loop = (items.add(nextItem)) ? true : loop;
					}										
				}			
			}
		}		
		
		int id = 0;
		for (Item item : items) {
			super.addState(new State<Item>(id, item));
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
						&& dItem.getDot() == (sItem.getDot() + 1)
						&& sItem.getLookAhead().equals(dItem.getLookAhead()))
					super.addTransition(sState, dState, sItem.getNextCharacter());		
				if (epsilonTransitions.contains(new Pair<Item, Item>(sItem, dItem)))
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
	
	private GSet<Item> generateAxiomItems() {
		GSet<Item> items = new GSet<Item>();
		Production axiomProduction = this.getGrammar().getProductions().getProductionsLeftContaining(this.getGrammar().getAxiom()).getFirst();
		items.addAll(generateItemsWithoutLookAhead(axiomProduction));
		for (Item item : items)
			item.getLookAhead().add(END_MARKER);
		return items;
	}
	
	private Alphabet generateLookAheadSet(Item item) {
		Character symbol = item.getNextCharacter(1);		
		if (symbol == null) //b = epsilon
			return item.getLookAhead();
		if (this.getGrammar().getNullables().contains(symbol)) { // b != epsilon, b = nullable
			Alphabet lookAheadSet = new Alphabet();
			Alphabet first = this.getGrammar().getFirstOne(symbol);
			first.remove(Grammar.EPSILON);			
			lookAheadSet.addAll(item.getLookAhead());
			lookAheadSet.addAll(first);
			return lookAheadSet;
		}		
		return grammar.getFirstOne(symbol); // b != epsilon, b != nullable
	}
	
	private GSet<Item> generateItemsWithoutLookAhead(Production production) {
		GSet<Item> items = new GSet<Item>();
		if (production.isEpsilonProduction()) {
			items.add(new Item(production, 1));
			return items;
		}				
		for (int i = 0; i <= production.getRightSize(); i ++)
			items.add(new Item(production, i));	
		return items;
	}

}


