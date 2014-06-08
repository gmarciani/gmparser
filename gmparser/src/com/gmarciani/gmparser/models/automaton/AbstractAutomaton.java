package com.gmarciani.gmparser.models.automaton;

import java.util.Objects;

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
		state.setNormal();
		boolean added = this.getStates().add(state);
		return added;
	}
	
	@Override public boolean addAsInitialState(State state) {
		for (State s : this.getStates())
			if (s.isInitial())
				s.setIsInitial(false);
		state.setIsInitial(true);
		boolean added = this.getStates().add(state);
		return added;
	}
	
	@Override public boolean addAsFinalState(State state) {
		state.setIsFinal(true);
		boolean added = this.getStates().add(state);
		return added;
	}
	
	@Override public boolean addAsInitialFinalState(State state) {
		state.setIsFinal(true);
		state.setIsFinal(true);
		boolean added = this.getStates().add(state);
		return added;
	}

	@Override public boolean removeState(State state) {
		return this.getTransitionFunction().removeAllTransitionsForState(state);
	}

	@Override public boolean containsState(State state) {
		return this.getStates().contains(state);
	}

	@Override public boolean removeFromFinalStates(State state) {
		return this.getFinalStates().remove(state);
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
		return this.getTransitionFunction().removeAllTransitionsForSymbol(symbol);
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
