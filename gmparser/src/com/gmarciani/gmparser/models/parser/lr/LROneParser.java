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

import com.gmarciani.gmparser.models.automaton.FiniteAutomaton;
import com.gmarciani.gmparser.models.commons.nple.Pair;
import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.production.Production;
import com.gmarciani.gmparser.models.parser.lr.recognition.Action;
import com.gmarciani.gmparser.models.parser.lr.recognition.ActionType;
import com.gmarciani.gmparser.models.parser.lr.recognition.BigProductionGraph;
import com.gmarciani.gmparser.models.parser.lr.recognition.Item;
import com.gmarciani.gmparser.models.parser.lr.recognition.LROneMatrix;
import com.gmarciani.gmparser.models.parser.lr.session.LROneParsingSession;

/**
 * <p>CLR(1) parser implementation.<p>
 * <p>Every algorithm has been derived from [A. Pettorossi "Techniques for Searching, Parsing and Matching (3rd edition)", alg. 5.4.1]<p>
 * 
 * @see com.gmarciani.gmparser.models.parser.cyk.session.CYKParsingSession
 * 
 * @author Giacomo Marciani
 * @version 1.0
 */
public class LROneParser {
	
	/**
	 * <p>Checks if the specified word can be parsed by the specified grammar.<p>
	 * <p>The algorithm has been derived from [A. Pettorossi "Techniques for Searching, Parsing and Matching (3rd edition)", alg. 5.4.1]<p>
	 * 
	 * @param grammar the grammar to parse with.
	 * @param word the word to parse.
	 * 
	 * @return true if the specified word can be parsed by the specified grammar; false, otherwise.
	 */
	public static synchronized boolean parse(Grammar grammar, String word) {
		word += '$'; // adds the end marker for PDA parsing.
		LROneMatrix recognitionMatrix = getRecognitionMatrix(grammar);
		return isLROneGrammar(recognitionMatrix) 
			&& parseWithPushDownAutomaton(recognitionMatrix, word);
	}

	/**
	 * <p>Checks if the specified word can be parsed by the specified grammar.<p>
	 * <p>The algorithm has been derived from [A. Pettorossi "Techniques for Searching, Parsing and Matching (3rd edition)", alg. 5.4.1]<p>
	 * 
	 * @param grammar the grammar to parse with.
	 * @param word the word to parse.
	 * 
	 * @return the LR(1) parsing session for the specified grammar and word.
	 */
	public static LROneParsingSession parseWithSession(Grammar grammar, String word) {
		word += '$'; // adds the end marker for PDA parsing.
		LROneMatrix recognitionMatrix = getRecognitionMatrix(grammar);
		boolean result = isLROneGrammar(recognitionMatrix) 
				&& parseWithPushDownAutomaton(recognitionMatrix, word);
		return new LROneParsingSession(grammar, word, recognitionMatrix, result);
	}	
	
	/**
	 * <p>Generates the LR(1) recognition matrix derived from the specified grammar.<p>
	 * <p>The algorithm has been derived from [A. Pettorossi "Techniques for Searching, Parsing and Matching (3rd edition)", alg. 5.4.1]<p>
	 * 
	 * @param grammar the grammar.
	 * 
	 * @return the LR(1) recognition matrix derived from the specified grammar.
	 */
	public static LROneMatrix getRecognitionMatrix(Grammar grammar) {
		grammar.toAugmentedGrammar(); // Generate the augmented grammar ...
		FiniteAutomaton<Item> automaton = generateBigProductionFiniteAutomaton(grammar); // ... then generate the associated Big Productions graph ...	
		return new LROneMatrix(grammar, automaton); // ... and derive the LR(1) recognition matrix.
	}

	/**
	 * <p>Generates the deterministic finite automaton of items derived from the specified grammar.<p>
	 * <p>The algorithm has been derived from [A. Pettorossi "Techniques for Searching, Parsing and Matching (3rd edition)", alg. 5.4.1]<p>
	 * 
	 * @param grammar the grammar.
	 * 
	 * @return the deterministic finite automaton of items derived from the specified grammar.
	 */
	private static FiniteAutomaton<Item> generateBigProductionFiniteAutomaton(Grammar grammar) {
		BigProductionGraph bigProductionGraph = new BigProductionGraph(grammar); // Generates the Big Productions linear graph ...
		FiniteAutomaton<Item> bigProductionFiniteAutomaton = bigProductionGraph.powersetConstruction(); // ... and generates the equivalent deterministic finite automaton.
		return bigProductionFiniteAutomaton;
	}	
	
	/**
	 * <p>Checks if the specified word can be accepted by a the non deterministic PDA by the specified LR(1) recognition matrix.<p>
	 * <p>The algorithm has been derived from [A. Pettorossi "Techniques for Searching, Parsing and Matching (3rd edition)", alg. 5.4.1]<p>
	 * 
	 * @param recognitionMatrix the LR(1) recognition matrix to parse with.
	 * @param word the word to parse.
	 * 
	 * @return true if the non deterministic PDA can accept the specified word by the specified LR(1) recognition matrix.
	 */
	private static boolean parseWithPushDownAutomaton(LROneMatrix recognitionMatrix, String word) {
		int inputTape = 0;
		Stack<Pair<Character, Integer>> stack = new Stack<Pair<Character, Integer>>();
		stack.push(new Pair<Character, Integer>('$', recognitionMatrix.getAutomaton().getInitial().getId()));		
		Action action = null;
		while(!stack.isEmpty()) {
			int currentState = stack.peek().getY();
			Character tapeSymbol = word.charAt(inputTape);
			action = recognitionMatrix.getAction(currentState, tapeSymbol);
			if (action == null) // found no action, but expected.
				return false;
			if (action.isActionType(ActionType.ACCEPT)) { // acceptance: accept the word.
				return true;
			} else if (action.isActionType(ActionType.SHIFT)) { // shift: push into the stack the cover state with its covered tape symbol.
				Integer coverState = action.getValue();
				stack.push(new Pair<Character, Integer>(tapeSymbol, coverState));
				inputTape ++;
			} else if (action.isActionType(ActionType.GOTO)) { //goto: push into the stack the non terminal symbol.
				Integer dState = action.getValue();
				stack.push(new Pair<Character, Integer>(null, dState));
			} else if (action.isActionType(ActionType.REDUCE)) { // reduction (the backtracking step): backtracks the stack.
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
	
	/**
	 * <p>Checks if the specified grammar is a LR(1) grammar.<p>
	 * <p>The algorithm has been derived from [A. Pettorossi "Techniques for Searching, Parsing and Matching (3rd edition)", rem. 5.4.4]<p>
	 * 
	 * @param grammar the grammar.
	 * 
	 * @return true if the specified grammar is a LR(1) grammar.
	 */
	public static boolean isLROneGrammar(Grammar grammar) {
		// a LR(1) grammar can be recognized by the LR(1) recognition matrix that generates.
		LROneMatrix recognitionMatrix = getRecognitionMatrix(grammar);
		return isLROneGrammar(recognitionMatrix);
	}
	
	/**
	 * Checks if the specified LR(1) recognition matrix has been derived from a LR(1) grammar.
	 * <p>The algorithm has been derived from [A. Pettorossi "Techniques for Searching, Parsing and Matching (3rd edition)", rem. 5.4.4]<p>
	 * 
	 * @param recognitionMatrix the LR(1) recognition matrix.
	 * 
	 * @return true if the specified LR(1) recognition matrix has been derived from a LR(1) grammar; false, otherwise.
	 */
	private static boolean isLROneGrammar(LROneMatrix recognitionMatrix) {
		// a LR(1) grammar generates a LR(1) recognition matrix without colliding action, so without production ambiguity deeper than one.
		for (Integer stateId : recognitionMatrix.getDomainX())
			for (Character symbol : recognitionMatrix.getDomainY())
				if (recognitionMatrix.getAllForXY(stateId, symbol).size() > 1)
					return false;
		return true;
	}	

}
