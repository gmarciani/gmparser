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

package com.gmarciani.gmparser.models.parser.lr;

import com.gmarciani.gmparser.controllers.grammar.GrammarTransformer;
import com.gmarciani.gmparser.models.automaton.finite.FiniteAutomaton;
import com.gmarciani.gmparser.models.automaton.pushdown.NonDeterministPushDownAutomaton;
import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.parser.Parser;
import com.gmarciani.gmparser.models.parser.lr.action.Action;
import com.gmarciani.gmparser.models.parser.lr.action.ActionType;
import com.gmarciani.gmparser.models.parser.lr.bigproduction.BigProductionGraph;
import com.gmarciani.gmparser.models.parser.lr.bigproduction.Item;
import com.gmarciani.gmparser.models.parser.lr.matrix.LROneMatrix;

public class LROneParser implements Parser {

	public LROneParser() {
		// TODO Auto-generated constructor stub
	}

	@Override public boolean parse(Grammar grammar, String word) {
		Grammar augmentedGrammar = this.generateAugmentedGrammar(grammar);
		FiniteAutomaton<Item> automaton = this.generateBigProductionFiniteAutomaton(augmentedGrammar);
		LROneMatrix recognitionMatrix = this.generateRecognitionMatrix(automaton);
		NonDeterministPushDownAutomaton pda = this.generatePushDownAutomaton(recognitionMatrix);
		Action finalAction = pda.parse(word);
		return finalAction.isActionType(ActionType.ACCEPT);
	}	
	
	private Grammar generateAugmentedGrammar(Grammar grammar) {
		if (grammar.getProductionsForNonTerminal(grammar.getAxiom()).size() > 1
				|| grammar.getProductionsRightContaining(grammar.getAxiom()).size() >= 1)
			return GrammarTransformer.getInstance().generateAugmentedGrammar(grammar);
		
		return grammar;
	}

	private FiniteAutomaton<Item> generateBigProductionFiniteAutomaton(Grammar grammar) {
		BigProductionGraph bigProductionGraph = new BigProductionGraph(grammar);
		FiniteAutomaton<Item> bigProductionFiniteAutomaton = bigProductionGraph.powersetConstruction();
		return bigProductionFiniteAutomaton;
	}
	
	private LROneMatrix generateRecognitionMatrix(FiniteAutomaton<Item> automaton) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private NonDeterministPushDownAutomaton generatePushDownAutomaton(LROneMatrix recognitionMatrix) {
		// TODO Auto-generated method stub
		return null;
	}

}
