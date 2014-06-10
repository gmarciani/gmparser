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

package com.gmarciani.gmparser.models.automaton.finite;

import com.gmarciani.gmparser.models.automaton.Automaton;
import com.gmarciani.gmparser.models.automaton.function.DeterministicTransitionFunction;
import com.gmarciani.gmparser.models.automaton.state.State;
import com.gmarciani.gmparser.models.automaton.state.States;
import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;

public class FiniteAutomaton<V> extends AbstractAutomaton<V> 
							 implements Automaton<V> {
	
	public FiniteAutomaton(State<V> initialState) {
		this.states = new States<V>();
		this.alphabet = new Alphabet();
		this.transitionFunction = new DeterministicTransitionFunction<V>(this.getStates(), this.getAlphabet(), this.getStates());
		this.addAsInitialState(initialState);
	}
	
	@Override public boolean addSymbol(Character symbol) {
		if (symbol.equals(Grammar.EPSILON))
			return false;
		return super.getAlphabet().add(symbol);
	}
	
	@Override public boolean isAccepted(String word) {
		State<V> currentState = this.getInitialState();
		if (currentState == null)
			return false;
		for (Character symbol : word.toCharArray()) {
			currentState = this.getTransitionFunction().getTransition(currentState, symbol);
			if (currentState == null)
				return false;
		}
			
		return currentState.isFinal();
	}
	
	@Override public String toString() {
		return "FiniteAutomaton(" + 
				this.getStates() + "," + 
				this.getAlphabet() + "," + 
				this.getInitialState() + "," + 
				this.getFinalStates() + "," + 
				this.getTransitionFunction() + ")";
	}

}
