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

import java.util.Objects;

import com.gmarciani.gmparser.models.automaton.Automaton;
import com.gmarciani.gmparser.models.automaton.function.TransitionFunction;
import com.gmarciani.gmparser.models.automaton.state.State;
import com.gmarciani.gmparser.models.automaton.state.States;
import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;

public abstract class AbstractAutomaton implements Automaton {
	
	protected States states;
	protected Alphabet alphabet;
	protected TransitionFunction transitionFunction;

	@Override public States getStates() {
		return this.states;
	}

	@Override public Alphabet getAlphabet() {
		return this.alphabet;
	}

	@Override public State getInitialState() {
		for (State state : this.getStates())
			if (state.isInitial())
				return state;
		return null;
	}

	@Override public States getFinalStates() {
		States finals = new States();
		for (State state : this.getStates())
			if (state.isFinal())
				finals.add(state);
		return finals;
	}

	protected TransitionFunction getTransitionFunction() {
		return this.transitionFunction;
	}

	@Override public boolean addState(State state) {
		if (this.containsState(state))
			return false;
		state.setNormal();
		boolean added = this.getStates().add(state);
		return added;
	}
	
	@Override public void addAsInitialState(State state) {
		for (State s : this.getStates())
			if (s.isInitial())
				s.setIsInitial(false);
		if (this.containsState(state)) {			
			this.getStates().getState(state.getId()).setIsInitial(true);
			return;
		}		
		state.setIsInitial(true);
		this.getStates().add(state);
	}
	
	@Override public void addAsFinalState(State state) {
		if (this.containsState(state)) {
			this.getStates().getState(state.getId()).setIsFinal(true);
			return;
		}			
		state.setIsFinal(true);
		this.getStates().add(state);
	}

	@Override public boolean removeState(State state) {
		boolean removedFrom = this.getTransitionFunction().removeAllTransitionsFromState(state);
		boolean removedTo = this.getTransitionFunction().removeAllTransitionsToState(state);
		boolean removedFromStates = this.getStates().remove(state);
		return removedFrom || removedTo || removedFromStates;
	}

	@Override public boolean containsState(State state) {
		return this.getStates().contains(state);
	}

	@Override public void removeFromFinalStates(State state) {
		if (this.containsState(state))
			this.getStates().getState(state.getId()).setIsFinal(false);
	}

	@Override public boolean isInitialState(State state) {
		return this.getInitialState().equals(state);
	}

	@Override public boolean isFinalState(State state) {
		return this.getFinalStates().contains(state);
	}

	@Override public boolean addSymbol(Character symbol) {
		return this.getAlphabet().add(symbol);
	}

	@Override public boolean removeSymbol(Character symbol) {
		boolean removedTransitions = this.getTransitionFunction().removeAllTransitionsBySymbol(symbol);
		boolean removedFromAlphabet = this.getAlphabet().remove(symbol);
		return removedTransitions || removedFromAlphabet;
	}

	@Override public boolean containsSymbol(Character symbol) {
		return this.getAlphabet().contains(symbol);
	}

	@Override public boolean addTransition(State sState, State dState, Character symbol) {
		return this.getTransitionFunction().addTransition(sState, dState, symbol);
	}

	@Override public boolean removeTransition(State sState, State dState, Character symbol) {
		return this.getTransitionFunction().removeTransition(sState, dState, symbol);
	}
	
	@Override public States getTransitions(State sState, Character symbol) {
		return this.getTransitionFunction().getTransitions(sState, symbol);
	}
	
	@Override public State getTransition(State sState, Character symbol) {
		return this.getTransitionFunction().getTransition(sState, symbol);
	}

	@Override public boolean containsTransition(State sState, State dState, Character symbol) {
		return this.getTransitionFunction().containsTransition(sState, dState, symbol);
	}

	@Override public abstract boolean isAccepted(String word);

	@Override public String toFormattedAutomaton() {
		return this.getTransitionFunction().toFormattedTransitionFunction();
	}
	
	@Override public boolean equals(Object obj) {
		if (this.getClass() != obj.getClass())
			return false;
		
		AbstractAutomaton other = (AbstractAutomaton) obj;
		
		return (this.getStates().equals(other.getStates())
				&& this.getAlphabet().equals(other.getAlphabet())
				&& this.getInitialState().equals(other.getInitialState())
				&& this.getFinalStates().equals(other.getFinalStates())
				&& this.getTransitionFunction().equals(other.getTransitionFunction()));
	}
	
	@Override public int hashCode() {
		return Objects.hash(this.getStates(), 
				this.getAlphabet(), 
				this.getInitialState(), 
				this.getFinalStates(), 
				this.getTransitionFunction());
	}	

}
