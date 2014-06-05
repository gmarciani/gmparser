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
import com.gmarciani.gmparser.models.automaton.state.State;
import com.gmarciani.gmparser.models.automaton.state.States;
import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;

public class FiniteAutomaton implements Automaton {
	
	private States states;
	private Alphabet alphabet;
	private State initialState;
	private States finalStates;
	private TransitionFunction transitionFunction;
	
	public FiniteAutomaton(States states, Alphabet alphabet, State initialState, States finalStates, TransitionFunction transitionFunction) {
		this.setStates(states);
		this.setAlphabet(alphabet);
		this.setInitialState(initialState);
		this.setFinalStates(finalStates);
		this.setTransitionFunction(transitionFunction);
	}
	
	public FiniteAutomaton(State initialState) {
		this.setStates(new States());
		this.getStates().add(initialState);
		this.setAlphabet(new Alphabet());
		this.setInitialState(initialState);
		this.setFinalStates(new States());
		this.setTransitionFunction(new TransitionFunction(this.getStates(), this.getAlphabet()));
	}

	public States getStates() {
		return this.states;
	}

	public void setStates(States states) {
		this.states = states;
	}

	public Alphabet getAlphabet() {
		return this.alphabet;
	}

	public void setAlphabet(Alphabet alphabet) {
		this.alphabet = alphabet;
	}

	public State getInitialState() {
		return this.initialState;
	}

	public void setInitialState(State initialState) {
		this.initialState = initialState;
	}

	public States getFinalStates() {
		return this.finalStates;
	}

	public void setFinalStates(States finalStates) {
		this.finalStates = finalStates;
	}

	public TransitionFunction getTransitionFunction() {
		return this.transitionFunction;
	}

	public void setTransitionFunction(TransitionFunction transitionFunction) {
		this.transitionFunction = transitionFunction;
	}
	
	public FiniteAutomaton powersetConstruction() {
		FiniteAutomaton minimizedAutomaton = null;
		
		return minimizedAutomaton;
	}
	
	@Override public String toString() {
		return "FiniteStateAutomaton(" + 
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

	@Override
	public boolean isAccepted(String word) {
		// TODO Auto-generated method stub
		return false;
	}

}
