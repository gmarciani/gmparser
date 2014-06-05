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

import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import com.gmarciani.gmparser.models.automaton.Automaton;
import com.gmarciani.gmparser.models.automaton.finite.FiniteAutomaton;
import com.gmarciani.gmparser.models.automaton.state.State;
import com.gmarciani.gmparser.models.automaton.state.StateId;
import com.gmarciani.gmparser.models.automaton.state.States;
import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;

public class TransitionGraph implements Automaton {
	
	private States states;
	private Alphabet alphabet;
	private StateId initialState;
	private Set<StateId> finalStates;
	private TransitionFunction transitionFunction;
	
	public TransitionGraph(State initialState) {
		this.states = new States(initialState);
		this.alphabet = new Alphabet(Grammar.EPSILON);
		this.initialState = initialState.getId();
		this.finalStates = new TreeSet<StateId>();
		this.transitionFunction = new TransitionFunction();
	}

	public States getStates() {
		return this.states;
	}

	public Alphabet getAlphabet() {
		return this.alphabet;
	}
	
	public StateId getInitialStateId() {
		return this.initialState;
	}

	public State getInitialState() {
		return this.getStates().getState(this.getInitialStateId());
	}

	public States getFinalStates() {
		return this.finalStates;
	}
	
	public boolean addToFinalStates(State state) {
		return this.getFinalStates().add(state);
	}

	public TransitionFunction getTransitionFunction() {
		return this.transitionFunction;
	}
	
	public void addTransition(State sourceState, State destinationState, Character symbol) {
		this.getTransitionFunction().addTransition(sourceState, destinationState, symbol);
	}
	
	public States getImage(States states, Character symbol) {
		States image = new States();
		
		for (State state : states) {
			image.addAll(this.getImage(state, symbol));
		}
		return image;
	}
	
	public States getImage(State state, Character symbol) {
		States image = new States();
		State currentState = state;
		
		
		
		return image;
	}
	
	@Override public boolean isAccepted(String word) {
		FiniteAutomaton automaton = this.powersetConstruction();
		return automaton.isAccepted(word);
	}
	
	public FiniteAutomaton powersetConstruction() {
		return null;
	}
	
	@Override public String toString() {
		return "TransitionGraph(" + 
				this.getStates() + ", " + 
				this.getAlphabet() + "," + 
				this.getInitialState() + ", " + 
				this.getFinalStates() + ", " + 
				this.getTransitionFunction() + ")";
	}
	
	@Override public boolean equals(Object obj) {
		if (this.getClass() != obj.getClass())
			return false;
		
		FiniteAutomaton other = (FiniteAutomaton) obj;
		
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
