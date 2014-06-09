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

package com.gmarciani.gmparser.models.automaton.function;

import com.gmarciani.gmparser.models.automaton.state.State;
import com.gmarciani.gmparser.models.automaton.state.States;
import com.gmarciani.gmparser.models.commons.function.DeterministicFunction;
import com.gmarciani.gmparser.models.commons.nple.Triple;
import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;

public class DeterministicTransitionFunction extends DeterministicFunction<State, Character, State> 
											 implements TransitionFunction {

	public DeterministicTransitionFunction(States sStates, Alphabet alphabet, States dStates) {
		super(sStates, alphabet, dStates);
	}
	
	public DeterministicTransitionFunction() {
		super();
	}

	@Override public boolean addTransition(State sState, State dState, Character symbol) {
		State sStateNow = super.getDomainX().get(sState);
		State dStateNow = super.getCodomain().get(dState);
		return super.add(sStateNow, symbol, dStateNow);
	}

	@Override public boolean removeTransition(State sState, State dState, Character symbol) {
		return super.removeXYZ(sState, symbol, dState);
	}

	@Override public boolean removeAllTransitionsFromState(State state) {
		return super.removeAllForX(state);
	}
	
	@Override public boolean removeAllTransitionsToState(State state) {
		return super.removeAllForZ(state);
	}
	
	@Override public boolean removeAllTransitionsFromStateToState(State sState, State dState) {
		return super.removeAllForXZ(sState, dState);
	}

	@Override public boolean removeAllTransitionsBySymbol(Character symbol) {
		return super.removeAllForY(symbol);
	}

	@Override public boolean removeAllTransitionsFromStateBySymbol(State state, Character symbol) {
		return super.removeAllForXY(state, symbol);
	}
	
	@Override public boolean removeAllTransitionsToStateBySymbol(State state, Character symbol) {
		return super.removeAllForYZ(symbol, state);
	}
	
	@Override public State getTransition(State state, Character symbol) {
		return super.get(state, symbol);
	}
	
	@Override public States getTransitions(State state, Character symbol) {
		States transitions = new States();
		for (Triple<State, Character, State> triple : super.getAllForXY(state, symbol))
			transitions.add(triple.getZ());
		return transitions;
	}

	@Override public boolean containsTransition(State sState, State dState, Character symbol) {
		return super.containsXYZ(sState, symbol, dState);
	}

	@Override public String toFormattedTransitionFunction() {
		return super.toFormattedFunction();
	}

}
