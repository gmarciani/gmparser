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

import com.gmarciani.gmparser.models.automaton.graph.AbstractGraph;
import com.gmarciani.gmparser.models.automaton.graph.TransitionGraph;
import com.gmarciani.gmparser.models.commons.set.GSet;
import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.production.Production;

public class BigProductionGraph extends AbstractGraph<Item, Character> {
	
	protected Grammar grammar;
	
	public BigProductionGraph(Grammar grammar) {
		super();
		this.generate(grammar);		
	}
	
	protected void generate(Grammar grammar) {
		GSet<Item> items = new GSet<Item>();
		for (Production production : grammar.getProductions())
			items.addAll(Item.generateItems(production));
		for (Item item : items) {
			super.addVertex(item);
			if (item.hasNextCharacter())
				super.addEdge(item.getNextCharacter());
		}
		super.addEdge(Grammar.EPSILON);
		for (Item sItem : items) {
			for (Item dItem : items) {
				if (sItem.getProduction().equals(dItem.getProduction())
						&& dItem.getDot().equals(sItem.getDot() + 1))
					super.addTransition(sItem, dItem, sItem.getNextCharacter());
				if (sItem.hasNextCharacter()
						&& !dItem.hasJustReadCharacter()
						&& sItem.getNextCharacter().equals(dItem.getProduction().getLeft().getValue().charAt(0)))
						super.addTransition(sItem, dItem, Grammar.EPSILON);
			}
		}
			
		
	}

	public TransitionGraph generateTransitionGraph() {
		// TODO Auto-generated method stub
		return null;
	}

}
