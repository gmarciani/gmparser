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

package com.gmarciani.gmparser.models.automaton;

import java.util.Objects;

import com.gmarciani.gmparser.models.automaton.state.State;
import com.gmarciani.gmparser.models.automaton.state.States;
import com.gmarciani.gmparser.models.automaton.transition.TransitionFunction;
import com.gmarciani.gmparser.models.commons.nple.Triple;
import com.gmarciani.gmparser.models.commons.set.GSet;
import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;

/**
 * <p>Finite states automaton abstract model.<p>
 * <p>A finite state automaton is a 4-ple (Q,S,q0,F,T), where
 * Q is the set of states,
 * S is the alphabet,
 * q0 is the initial state,
 * F is the subset of final states,
 * T is a transition function, that can be deterministic or non deterministic.<p>
 * <p>V is the type of the generic value of every state.<p>
 * 
 * @see com.gmarciani.gmparser.models.automaton.FiniteAutomaton
 * @see com.gmarciani.gmparser.models.automaton.TransitionGraph
 * @see com.gmarciani.gmparser.models.automaton.state.States
 * @see com.gmarciani.gmparser.models.automaton.state.State
 * @see com.gmarciani.gmparser.models.grammar.alphabet.Alphabet
 * 
 * @author Giacomo Marciani
 * @version 1.0
 */
public abstract class AbstractAutomaton<V> {
	
	protected States<V> states;
	protected Alphabet alphabet;
	protected TransitionFunction<V> transitionFunction;

	/**
	 * Returns the set of states.
	 * 
	 * @return the set of states.
	 */
	public States<V> getStates() {
		return this.states;
	}

	/**
	 * Returns the alphabet.
	 * 
	 * @return the alphabet.
	 */
	public Alphabet getAlphabet() {
		return this.alphabet;
	}

	/**
	 * Returns the initial state.
	 * 
	 * @return the initial state.
	 */
	public State<V> getInitial() {
		for (State<V> state : this.getStates())
			if (state.isInitial())
				return state;
		return null;
	}

	/**
	 * Returns the set of final states.
	 * 
	 * @return the set of final states.
	 */
	public States<V> getFinals() {
		States<V> finals = new States<V>();
		for (State<V> state : this.getStates())
			if (state.isFinal())
				finals.add(state);
		return finals;
	}

	/**
	 * Returns the transition function.
	 * 
	 * @return the transition function.
	 */
	protected TransitionFunction<V> getTransitionFunction() {
		return this.transitionFunction;
	}
	
	/**
	 * Returns the next available state id.
	 * 
	 * @return the next available state id.
	 */
	public Integer getNextId() {
		int id = 0;
		while (this.getStates().getIds().contains(id))
			id ++;
		return id;
	}

	/**
	 * Adds to the finite automaton the specified state.
	 * 
	 * @param state the state.
	 * 
	 * @return true if the state has been added to the set of states; false, otherwise.
	 */
	public boolean addState(State<V> state) {
		if (this.containsState(state))
			return false;
		state.setNormal();
		boolean added = this.getStates().add(state);
		return added;
	}
	
	/**
	 * Adds to the finite automaton the specified state as the initial state.
	 * 
	 * @param state the state.
	 */
	public void addAsInitial(State<V> state) {
		for (State<V> s : this.getStates())
			if (s.isInitial())
				s.setIsInitial(false);
		if (this.containsState(state)) {			
			this.getStates().getState(state.getId()).setIsInitial(true);
			return;
		}		
		state.setIsInitial(true);
		this.getStates().add(state);
	}
	
	/**
	 * Adds to the finite automaton the specified state as final state.
	 * 
	 * @param state the state.
	 */
	public void addAsFinal(State<V> state) {
		if (this.containsState(state)) {
			this.getStates().getState(state.getId()).setIsFinal(true);
			return;
		}			
		state.setIsFinal(true);
		this.getStates().add(state);
	}

	/**
	 * Removes from the finite automaton the specified state, and all the correspondent transitions.
	 * 
	 * @param state the state.
	 * 
	 * @return true if the specified state has been removed from the finite automaton; false, otherwise.
	 */
	public boolean removeState(State<V> state) {
		boolean removedFrom = this.getTransitionFunction().removeAllTransitionsFromState(state);
		boolean removedTo = this.getTransitionFunction().removeAllTransitionsToState(state);
		boolean removedFromStates = this.getStates().remove(state);
		return removedFrom || removedTo || removedFromStates;
	}

	/**
	 * Checks if the finite automaton contains the specified state.
	 * 
	 * @param state the state.
	 * 
	 * @return true if the finite automaton contains the specified state; false, otherwise.
	 */
	public boolean containsState(State<V> state) {
		return this.getStates().contains(state);
	}

	/**
	 * Removes the specified state from the set of final states.
	 * 
	 * @param state the state.
	 */
	public void removeFromFinals(State<V> state) {
		if (this.containsState(state))
			this.getStates().getState(state.getId()).setIsFinal(false);
	}

	/**
	 * Checks if the specified state is the initial state.
	 * 
	 * @param state the state.
	 * 
	 * @return true if the specified state is the initial state; false, otherwise.
	 */
	public boolean isInitial(State<V> state) {
		return this.getInitial().equals(state);
	}

	/**
	 * Checks if the specified state is final.
	 * 
	 * @param state the state.
	 * 
	 * @return true if the specified state is final; false, otherwise.
	 */
	public boolean isFinal(State<V> state) {
		return this.getFinals().contains(state);
	}

	/**
	 * Adds to the finite automaton the specified symbol.
	 * 
	 * @param symbol the symbol.
	 * 
	 * @return true if the specified symbol has been added to the finite automaton; false, otherwise.
	 */
	public boolean addSymbol(Character symbol) {
		return this.getAlphabet().add(symbol);
	}

	/**
	 * Removed from the finite automaton the specified symbols, and all the correspondent transitions.
	 * 
	 * @param symbol the symbol.
	 * 
	 * @return true if the specified symbol has been removed from the finite automaton; false, otherwise.
	 */
	public boolean removeSymbol(Character symbol) {
		boolean removedTransitions = this.getTransitionFunction().removeAllTransitionsBySymbol(symbol);
		boolean removedFromAlphabet = this.getAlphabet().remove(symbol);
		return removedTransitions || removedFromAlphabet;
	}

	/**
	 * Checks if the finite automaton contains the specified symbol.
	 * 
	 * @param symbol the symbol.
	 * 
	 * @return true if the finite automaton contains the specified symbol.
	 */
	public boolean containsSymbol(Character symbol) {
		return this.getAlphabet().contains(symbol);
	}

	/**
	 * Adds to the finite automaton the specified transition.
	 * 
	 * @param sState the source state.
	 * @param dState the destination state.
	 * @param symbol the transition symbol.
	 * 
	 * @return true if the specified transition has been added; false, otherwise.
	 */
	public boolean addTransition(State<V> sState, State<V> dState, Character symbol) {
		return this.getTransitionFunction().addTransition(sState, dState, symbol);
	}

	/**
	 * Removes from the finite automaton the specified transition.
	 * 
	 * @param sState the source state.
	 * @param dState the destination state.
	 * @param symbol the transition symbol.
	 * 
	 * @return true if the specified transition has been removed; false, otherwise.
	 */
	public boolean removeTransition(State<V> sState, State<V> dState, Character symbol) {
		return this.getTransitionFunction().removeTransition(sState, dState, symbol);
	}
	
	/**
	 * Returns the subset of all transitions from the specified source state by the specified symbol.
	 * 
	 * @param sState the source state.
	 * @param symbol the transition symbol.
	 * 
	 * @return the subset of all transitions from the specified source state by the specified symbol.
	 */
	public States<V> getTransitions(State<V> sState, Character symbol) {
		return this.getTransitionFunction().getTransitions(sState, symbol);
	}
	
	/**
	 * Returns the transition from the specified source state by the specified symbol.
	 * 
	 * @param sState the source state.
	 * @param symbol the transition symbol.
	 * 
	 * @return the transition from the specified source state by the specified symbol.
	 */
	public State<V> getTransition(State<V> sState, Character symbol) {
		return this.getTransitionFunction().getTransition(sState, symbol);
	}
	
	/**
	 * Returns the set of all transitions of the finite automaton.
	 *  
	 * @return the set of all transitions of the finite automaton.
	 */
	public GSet<Triple<State<V>, Character, State<V>>> getAllTransitions() {
		return this.getTransitionFunction().getAllTransitions();
	}

	/**
	 * Returns true if the finite automaton contains the specified transition; false, otherwise.
	 * 
	 * @param sState the source state.
	 * @param dState the destination state.
	 * @param symbol the transition symbol.
	 * 
	 * @return true if the finite automaton contains the specified transition; false, otherwise.
	 */
	public boolean containsTransition(State<V> sState, State<V> dState, Character symbol) {
		return this.getTransitionFunction().containsTransition(sState, dState, symbol);
	}

	/**
	 * Checks if the finite automaton accepts the specified word.
	 * 
	 * @param word the word to accept.
	 * 
	 * @return true if the finite automaton accepts the specified word; false, otherwise.
	 */
	public abstract boolean isAccepted(String word);

	public String toFormattedAutomaton() {
		return this.getTransitionFunction().toFormattedTransitionFunction();
	}
	
	public String toExtendedFormattedAutomaton() {
		return this.getTransitionFunction().toExtendedFormattedTransitionFunction();
	}
	
	@Override public boolean equals(Object obj) {
		if (this.getClass() != obj.getClass())
			return false;
		
		AbstractAutomaton<?> other = (AbstractAutomaton<?>) obj;
		
		return (this.getStates().equals(other.getStates())
				&& this.getAlphabet().equals(other.getAlphabet())
				&& this.getInitial().equals(other.getInitial())
				&& this.getFinals().equals(other.getFinals())
				&& this.getTransitionFunction().equals(other.getTransitionFunction()));
	}
	
	@Override public int hashCode() {
		return Objects.hash(this.getStates(), 
				this.getAlphabet(), 
				this.getInitial(), 
				this.getFinals(), 
				this.getTransitionFunction());
	}	

}
