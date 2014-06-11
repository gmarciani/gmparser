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

public class TransitionGraph<V> extends AbstractAutomaton<V> 
							 implements Automaton<V> {
	
	public TransitionGraph(State<V> initialState) {
		this.states = new States<V>();		
		this.alphabet = new Alphabet();
		this.transitionFunction = new NonDeterministicTransitionFunction<V>(this.getStates(), this.getAlphabet(), this.getStates());
		this.addAsInitialState(initialState);
	}
	
	public TransitionGraph() {
		this.states = new States<V>();		
		this.alphabet = new Alphabet();
		this.transitionFunction = new NonDeterministicTransitionFunction<V>(this.getStates(), this.getAlphabet(), this.getStates());
	}
	
	public FiniteAutomaton<V> powersetConstruction() {
		States<V> initialStates = this.getEpsilonImage(this.getInitialState());
		State<V> initialState = new State<V>(initialStates);//new State<V>(initialStates.getIds());
		FiniteAutomaton<V> automaton = new FiniteAutomaton<V>(initialState);
		if (this.getFinalStates().containsSome(initialStates))
				automaton.addAsFinalState(initialState);
		States<V> newStates = new States<V>(automaton.getInitialState());
		States<V> addedStates = new States<V>();
		while(!newStates.isEmpty()) {
			State<V> state = newStates.getFirst();
			automaton.addState(state);
			for (Character symbol : this.getAlphabet()) {
				if (symbol.equals(Grammar.EPSILON)) // to be removed
					continue;
				automaton.addSymbol(symbol);
				States<V> sStates = new States<V>();
				for (Integer id : state.getId())
					sStates.add(this.getStates().getState(id));
				States<V> dStates = this.getImage(sStates, symbol);
				if (!dStates.isEmpty()) {
					State<V> dState = new State<V>(dStates);
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
		States<V> currentStates = new States<V>(this.getInitialState());
		for (Character symbol : word.toCharArray())
			currentStates = this.getImage(currentStates, symbol);	
		for (State<V> state : this.getEpsilonImage(currentStates))
			if (this.isFinalState(state))
				return true;		
		return false;
	}
	
	public States<V> getImage(States<V> states, Character symbol) {
		States<V> images = new States<V>();
		for (State<V> state : states)
			images.addAll(this.getImage(state, symbol));	
		return images;
	}	
	
	public States<V> getImage(State<V> state, Character symbol) {
		if (symbol.equals(Grammar.EPSILON))
			return this.getEpsilonImage(state);		
		States<V> images = new States<V>();
		for (State<V> epsilonImageState : this.getEpsilonImage(state))
			images.addAll(this.getTransitions(epsilonImageState, symbol));
		images.addAll(this.getEpsilonImage(images));
		return images;
	}
	
	public States<V> getEpsilonImage(States<V> states) {
		States<V> images = new States<V>();
		for (State<V> state : states)
			images.addAll(this.getEpsilonImage(state));
		return images;
	}
	
	public States<V> getEpsilonImage(State<V> state) {
		return this.getEpsilonImage(new States<V>(), state);
	}
	
	protected States<V> getEpsilonImage(States<V> images, State<V> state) {
		images.add(state);
		States<V> epsilonMoves = this.getTransitions(state, Grammar.EPSILON);
		for (State<V> epsilonMove : epsilonMoves)
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
