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

package com.gmarciani.gmparser.models.automaton.graph;

import com.gmarciani.gmparser.models.automaton.Automaton;
import com.gmarciani.gmparser.models.automaton.finite.AbstractAutomaton;
import com.gmarciani.gmparser.models.automaton.finite.FiniteAutomaton;
import com.gmarciani.gmparser.models.automaton.function.NonDeterministicTransitionFunction;
import com.gmarciani.gmparser.models.automaton.state.State;
import com.gmarciani.gmparser.models.automaton.state.States;
import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;

public class TransitionGraph extends AbstractAutomaton 
							 implements Automaton {
	
	public TransitionGraph(State initialState) {
		this.states = new States();		
		this.alphabet = new Alphabet();
		this.transitionFunction = new NonDeterministicTransitionFunction(this.getStates(), this.getAlphabet(), this.getStates());
		this.addAsInitialState(initialState);
	}	
	
	public FiniteAutomaton powersetConstruction() {
		States initialStates = this.getEpsilonImage(this.getInitialState());
		State initialState = new State(initialStates.getIds());
		FiniteAutomaton automaton = new FiniteAutomaton(initialState);
		if (this.getFinalStates().containsSome(initialStates))
				automaton.addAsFinalState(initialState);
		States newStates = new States(automaton.getInitialState());
		States addedStates = new States();
		while(!newStates.isEmpty()) {
			State state = newStates.getFirst();
			automaton.addState(state);
			for (Character symbol : this.getAlphabet()) {
				if (symbol.equals(Grammar.EPSILON)) // to be removed
					continue;
				automaton.addSymbol(symbol);
				States sStates = new States();
				for (Integer id : state.getId())
					sStates.add(this.getStates().getState(id));
				States dStates = this.getImage(sStates, symbol);
				if (!dStates.isEmpty()) {
					State dState = new State(dStates.getIds());
					if (!automaton.containsState(dState))
						automaton.addState(dState);
					if (this.getFinalStates().containsSome(dStates))
						automaton.addAsFinalState(dState);
					if (!addedStates.contains(dState))
						newStates.add(dState);					
					automaton.addTransition(state, dState, symbol);
				}				
			}
			addedStates.add(state);
			newStates.remove(state);
		}
		
		return automaton;
	}
	
	@Override public boolean isAccepted(String word) {
		States currentStates = new States(this.getInitialState());
		for (Character symbol : word.toCharArray())
			currentStates = this.getImage(currentStates, symbol);	
		for (State state : this.getEpsilonImage(currentStates))
			if (this.isFinalState(state))
				return true;		
		return false;
	}
	
	public States getImage(States states, Character symbol) {
		States images = new States();
		for (State state : states)
			images.addAll(this.getImage(state, symbol));	
		return images;
	}	
	
	public States getImage(State state, Character symbol) {
		if (symbol.equals(Grammar.EPSILON))
			return this.getEpsilonImage(state);		
		States images = new States();
		for (State epsilonImageState : this.getEpsilonImage(state))
			images.addAll(this.getTransitions(epsilonImageState, symbol));
		images.addAll(this.getEpsilonImage(images));
		return images;
	}
	
	public States getEpsilonImage(States states) {
		States images = new States();
		for (State state : states)
			images.addAll(this.getEpsilonImage(state));
		return images;
	}
	
	public States getEpsilonImage(State state) {
		return this.getEpsilonImage(new States(), state);
	}
	
	protected States getEpsilonImage(States images, State state) {
		images.add(state);
		States epsilonMoves = this.getTransitions(state, Grammar.EPSILON);
		for (State epsilonMove : epsilonMoves)
			if (!images.contains(epsilonMove))
				images.addAll(this.getEpsilonImage(images, epsilonMove));
		return images;
	}
	
	@Override public String toString() {
		return "TransitionGraph(" + 
				this.getStates() + "," + 
				this.getAlphabet() + "," + 
				this.getInitialState() + "," + 
				this.getFinalStates() + "," + 
				this.getTransitionFunction() + ")";
	}	

}
