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
import java.util.Set;

import com.gmarciani.gmparser.models.automaton.Automaton;
import com.gmarciani.gmparser.models.automaton.function.DeterministicTransitionFunction;
import com.gmarciani.gmparser.models.automaton.state.State;
import com.gmarciani.gmparser.models.automaton.state.StateId;
import com.gmarciani.gmparser.models.automaton.state.States;
import com.gmarciani.gmparser.models.commons.set.AdvancedSet;
import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;

public class DeterministicFiniteAutomaton implements Automaton {
	
	private States states;
	private Alphabet alphabet;
	private StateId initialStateId;
	private Set<StateId> finalStatesId;
	private DeterministicTransitionFunction transitionFunction;
	
	public DeterministicFiniteAutomaton(State initialState) {
		initialState.setIsInitial(true);
		this.states = new States(initialState);
		this.alphabet = new Alphabet();
		this.initialStateId = initialState.getId();
		this.finalStatesId = new AdvancedSet<StateId>();
		this.transitionFunction = new DeterministicTransitionFunction(this.getStates(), this.getAlphabet());
	}

	public States getStates() {
		return this.states;
	}

	public Alphabet getAlphabet() {
		return this.alphabet;
	}
	
	public StateId getInitialStateId() {
		return this.initialStateId;
	}
	
	public State getInitialState() {
		return this.getStates().getState(this.getInitialStateId());
	}
	
	public Set<StateId> getFinalStatesId() {
		return this.finalStatesId;
	}
	
	public States getFinalStates() {
		States finals = new States();
		for (State state : this.getStates())
			if (this.isFinalState(state.getId()))
				finals.add(state);
		return finals;
	}
	
	public DeterministicTransitionFunction getTransitionFunction() {
		return this.transitionFunction;
	}
	
	public boolean isInitialState(StateId id) {
		return this.getInitialStateId().equals(id);
	}
	
	public boolean isFinalState(StateId id) {
		return this.getFinalStatesId().contains(id);
	}
	
	public boolean addState(State state) {
		boolean added = this.getStates().add(state);
		this.getTransitionFunction().addTrivialEpsilonMove(state);
		return added;
	}
	
	public void removeState(State state) {
		if (this.isInitialState(state.getId()))
			return;
		if (this.isFinalState(state.getId()))
			this.removeFromFinalStates(state.getId());
		this.getTransitionFunction().removeAllTransitionsForState(state);
		this.getStates().remove(state);
	}
	
	public boolean addSymbol(Character symbol) {
		return this.getAlphabet().add(symbol);
	}
	
	public void removeSymbol(Character symbol) {
		this.getTransitionFunction().removeAllTransitionsForSymbol(symbol);
		this.getAlphabet().remove(symbol);
	}
	
	public boolean addToFinalStates(StateId id) {
		this.getStates().getState(id).setIsFinal(true);
		return this.getFinalStatesId().add(id);
	}
	
	public void removeFromFinalStates(StateId id) {
		this.getStates().getState(id).setIsFinal(false);
		this.getFinalStatesId().remove(id);
	}
	
	public void addTransition(State sourceState, State destinationState, Character symbol) {
		this.getTransitionFunction().addTransition(sourceState, destinationState, symbol);
	}
	
	public void removeTransition(State sState, State dState, Character symbol) {
		this.getTransitionFunction().removeTransition(sState, dState, symbol);
	}
	
	@Override public boolean isAccepted(String word) {
		State currentState = this.getInitialState();
		for (Character symbol : word.toCharArray()) {
			currentState = this.getTransitionFunction().get(currentState, symbol);
			if (currentState == null)
				return false;
		}
			
		return currentState.isFinal();
	}
	
	public String toFormattedString() {
		return this.getTransitionFunction().toFormattedString();
	}
	
	@Override public String toString() {
		return "DeterministicFiniteAutomaton(" + 
				this.getStates() + "," + 
				this.getAlphabet() + "," + 
				this.getInitialState() + "," + 
				this.getFinalStates() + "," + 
				this.getTransitionFunction() + ")";
	}
	
	@Override public boolean equals(Object obj) {
		if (this.getClass() != obj.getClass())
			return false;
		
		DeterministicFiniteAutomaton other = (DeterministicFiniteAutomaton) obj;
		
		return (this.getStates().equals(other.getStates())
				&& this.getAlphabet().equals(other.getAlphabet())
				&& this.getInitialStateId().equals(other.getInitialState())
				&& this.getFinalStatesId().equals(other.getFinalStates())
				&& this.getTransitionFunction().equals(other.getTransitionFunction()));
	}
	
	@Override public int hashCode() {
		return Objects.hash(this.getStates(), 
				this.getAlphabet(), 
				this.getInitialStateId(), 
				this.getFinalStatesId(), 
				this.getTransitionFunction());
	}	

}
