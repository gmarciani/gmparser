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

import java.util.Stack;

import com.gmarciani.gmparser.models.automaton.finite.FiniteAutomaton;
import com.gmarciani.gmparser.models.commons.nple.Pair;
import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.production.Production;
import com.gmarciani.gmparser.models.parser.lr.action.Action;
import com.gmarciani.gmparser.models.parser.lr.action.ActionType;
import com.gmarciani.gmparser.models.parser.lr.bigproduction.BigProductionGraph;
import com.gmarciani.gmparser.models.parser.lr.bigproduction.Item;
import com.gmarciani.gmparser.models.parser.lr.matrix.LROneMatrix;
import com.gmarciani.gmparser.models.parser.lr.session.LROneParsingSession;

/**
 * @author Giacomo Marciani
 * @version 1.0
 */
public class LROneParser {
	
	public static synchronized boolean parse(Grammar grammar, String word) {
		word += '$';
		LROneMatrix recognitionMatrix = getRecognitionMatrix(grammar);
		return isLROneGrammar(recognitionMatrix) 
			&& parseWithPushDownAutomaton(recognitionMatrix, word);
	}

	public static LROneParsingSession parseWithSession(Grammar grammar, String word) {
		word += '$';
		LROneMatrix recognitionMatrix = getRecognitionMatrix(grammar);
		boolean result = isLROneGrammar(recognitionMatrix) 
				&& parseWithPushDownAutomaton(recognitionMatrix, word);
		return new LROneParsingSession(grammar, word, recognitionMatrix, result);
	}	
	
	public static LROneMatrix getRecognitionMatrix(Grammar grammar) {
		grammar.toAugmentedGrammar();
		FiniteAutomaton<Item> automaton = generateBigProductionFiniteAutomaton(grammar);		
		return new LROneMatrix(grammar, automaton);
	}

	private static FiniteAutomaton<Item> generateBigProductionFiniteAutomaton(Grammar grammar) {
		BigProductionGraph bigProductionGraph = new BigProductionGraph(grammar);
		FiniteAutomaton<Item> bigProductionFiniteAutomaton = bigProductionGraph.powersetConstruction();
		return bigProductionFiniteAutomaton;
	}	
	
	private static boolean parseWithPushDownAutomaton(LROneMatrix recognitionMatrix, String word) {
		int inputTape = 0;
		Stack<Pair<Character, Integer>> stack = new Stack<Pair<Character, Integer>>();
		stack.push(new Pair<Character, Integer>('$', recognitionMatrix.getAutomaton().getInitialState().getId()));		
		Action action = null;
		while(!stack.isEmpty()) {
			int currentState = stack.peek().getY();
			Character tapeSymbol = word.charAt(inputTape);
			action = recognitionMatrix.getAction(currentState, tapeSymbol);
			if (action == null)
				return false;
			if (action.isActionType(ActionType.ACCEPT)) {
				return true;
			} else if (action.isActionType(ActionType.SHIFT)) {
				Integer coverState = action.getValue();
				stack.push(new Pair<Character, Integer>(tapeSymbol, coverState));
				inputTape ++;
			} else if (action.isActionType(ActionType.GOTO)) {
				Integer dState = action.getValue();
				stack.push(new Pair<Character, Integer>(null, dState));
			} else if (action.isActionType(ActionType.REDUCE)) {
				Integer productionIndex = action.getValue();
				Production production = recognitionMatrix.getProductions().get(productionIndex);
				Character productionLhs = production.getLeft().getValueAsChars()[0];
				char productionRhs[] = production.getRight().getValueAsChars();
				for (int i = productionRhs.length - 1; i >=0; i --)
					if (stack.peek().getX().equals(productionRhs[i]) 
							|| stack.peek().getX().equals(null))
						stack.pop();
				currentState = stack.peek().getY();
				action = recognitionMatrix.getAction(currentState, productionLhs);
				if (action.isActionType(ActionType.GOTO)) {
					Integer dState = action.getValue();
					stack.push(new Pair<Character, Integer>(productionLhs, dState));
				} else {
					return false;
				}					
			}
		}
		return false;
	}
	
	public static boolean isLROneGrammar(Grammar grammar) {
		LROneMatrix recognitionMatrix = getRecognitionMatrix(grammar);
		return isLROneGrammar(recognitionMatrix);
	}
	
	private static boolean isLROneGrammar(LROneMatrix recognitionMatrix) {
		for (Integer stateId : recognitionMatrix.getDomainX())
			for (Character symbol : recognitionMatrix.getDomainY())
				if (recognitionMatrix.getAllForXY(stateId, symbol).size() > 1)
					return false;
		return true;
	}	

}
