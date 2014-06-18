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

package com.gmarciani.gmparser.models.automaton.transition;

import com.gmarciani.gmparser.models.automaton.state.State;
import com.gmarciani.gmparser.models.automaton.state.States;
import com.gmarciani.gmparser.models.commons.function.NonDeterministicFunction;
import com.gmarciani.gmparser.models.commons.nple.Triple;
import com.gmarciani.gmparser.models.commons.set.GSet;
import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;

/**
 * <p>The deterministic transition function QxV:Powerset(Q).<p>
 * 
 * @see com.gmarciani.gmparser.models.automaton.TransitionGraph
 * @see com.gmarciani.gmparser.models.automaton.state.State
 * 
 * @author Giacomo Marciani
 * @version 1.0
 */
public class NonDeterministicTransitionFunction<V> extends NonDeterministicFunction<State<V>, Character, State<V>> 
												   implements TransitionFunction<V> {
	
	public NonDeterministicTransitionFunction(States<V> sStates, Alphabet alphabet, States<V> dStates) {
		super(sStates, alphabet, dStates);
	}
	
	public NonDeterministicTransitionFunction() {
		super();
	}
	
	@Override public States<V> getStates() {
		States<V> states = new States<V>();
		for (State<V> state : super.getDomainX())
			states.add(state);
		return states;
	}

	@Override public boolean addTransition(State<V> sState, State<V> dState, Character symbol) {
		State<V> sStateNow = super.getDomainX().get(sState);
		State<V> dStateNow = super.getCodomain().get(dState);
		return super.add(sStateNow, symbol, dStateNow);
	}

	@Override public boolean removeTransition(State<V> sState, State<V> dState, Character symbol) {
		return super.removeXYZ(sState, symbol, dState);
	}

	@Override public boolean removeAllTransitionsFromState(State<V> state) {
		return super.removeAllForX(state);
	}
	
	@Override public boolean removeAllTransitionsToState(State<V> state) {
		return super.removeAllForZ(state);
	}
	
	@Override public boolean removeAllTransitionsFromStateToState(State<V> sState, State<V> dState) {
		return super.removeAllForXZ(sState, dState);
	}

	@Override public boolean removeAllTransitionsBySymbol(Character symbol) {
		return super.removeAllForY(symbol);
	}

	@Override public boolean removeAllTransitionsFromStateBySymbol(State<V> state, Character symbol) {
		return super.removeAllForXY(state, symbol);
	}
	
	@Override public boolean removeAllTransitionsToStateBySymbol(State<V> state, Character symbol) {
		return super.removeAllForYZ(symbol, state);
	}
	
	@Override public State<V> getTransition(State<V> state, Character symbol) {
		return this.getTransitions(state, symbol).getFirst();
	}
	
	@Override public States<V> getTransitions(State<V> state, Character symbol) {
		States<V> transitions = new States<V>();
		for (Triple<State<V>, Character, State<V>> triple : super.getAllForXY(state, symbol))
			transitions.add(triple.getZ());
		return transitions;
	}
	
	@Override public GSet<Triple<State<V>, Character, State<V>>> getAllTransitions() {
		return super.getAll();
	}

	@Override public boolean containsTransition(State<V> sState, State<V> dState, Character symbol) {
		return super.containsXYZ(sState, symbol, dState);
	}

	@Override public String toFormattedTransitionFunction() {
		return super.toFormattedFunction();
	}	
	
	@Override public String toExtendedFormattedTransitionFunction() {
		return super.toFormattedFunction() + "\n" + this.getStates().toExtendedString();
	}

}
