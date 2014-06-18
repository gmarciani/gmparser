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

import java.util.ArrayList;
import java.util.List;

import com.gmarciani.gmparser.models.automaton.state.State;
import com.gmarciani.gmparser.models.automaton.state.States;
import com.gmarciani.gmparser.models.automaton.transition.NonDeterministicTransitionFunction;
import com.gmarciani.gmparser.models.commons.set.GSet;
import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;

/**
 * <p>The transition graph model.<p>
 * <p>Note that a transition graph can make non deterministic moves and epsilon-moves.<p>
 * <p>V is the type of the generic value of every state.<p>
 * 
 * @see com.gmarciani.gmparser.models.automaton.AbstractAutomaton
 * @see com.gmarciani.gmparser.models.automaton.FiniteAutomaton
 * 
 * @author Giacomo Marciani
 * @version 1.0
 */
public class TransitionGraph<V> extends AbstractAutomaton<V> {
	
	/**
	 * Creates a new transition graph with the specified initial state.
	 * 
	 * @param state the initial state.
	 */
	public TransitionGraph(State<V> state) {
		this.states = new States<V>();		
		this.alphabet = new Alphabet();
		this.transitionFunction = new NonDeterministicTransitionFunction<V>(this.getStates(), this.getAlphabet(), this.getStates());
		this.addAsInitial(state);
	}
	
	/**
	 * Creates a new empty transition graph.
	 */
	public TransitionGraph() {
		this.states = new States<V>();		
		this.alphabet = new Alphabet();
		this.transitionFunction = new NonDeterministicTransitionFunction<V>(this.getStates(), this.getAlphabet(), this.getStates());
	}
	
	/**
	 * <p>Generates the deterministic finite automaton equivalent to the current transition graph.<p>
	 * <p>The algorithm has been derived from [A. Pettorossi "Automata Theory and Formal Languages (3rd edition)", alg. 2.3.8]<p>
	 * 
	 * @return the deterministic finite automaton equivalent to the current transition graph.
	 */
	public FiniteAutomaton<V> powersetConstruction() {
		List<States<V>> renaming = new ArrayList<States<V>>(); // support for aggregated states renaming.
		States<V> iStateImage = this.getEpsilonImage(this.getInitial()); // the FA's initial state is the e-image of TG's initial state. 
		renaming.add(0, iStateImage);
		State<V> iState = new State<V>(0, iStateImage.getValues()); // the FA's initial state inherits every value in e-image of TG's initial state.
		FiniteAutomaton<V> automaton = new FiniteAutomaton<V>(iState);
		GSet<States<V>> uncompleteStates = new GSet<States<V>>(iStateImage); //support for collection iteration, and FA minimization.
		GSet<States<V>> completeStates = new GSet<States<V>>(); //support for collection iteration, and FA minimization.
		while(!uncompleteStates.isEmpty()) {
			States<V> sStates = uncompleteStates.getFirst();
			int nextSId = renaming.indexOf(sStates);
			State<V> sState = new State<V>(nextSId, sStates.getValues());
			automaton.addState(sState);
			if (this.getFinals().containsSome(sStates)) // a FA's state is final if it aggregates at least one TG's final state.
				automaton.addAsFinal(sState);
			for (Character symbol : this.getAlphabet()) {
				if (symbol.equals(Grammar.EPSILON)) // a FA cannot make any e-move.
					continue;
				automaton.addSymbol(symbol);
				States<V> dStates = this.getImage(sStates, symbol); // a FA's destination state is the v-image of the TG's subset of states that aggregates.
				if (!dStates.isEmpty()) { // minimization filter.
					if (!renaming.contains(dStates))
						renaming.add(automaton.getNextId(), dStates);
					int nextDId = renaming.indexOf(dStates);
					State<V> dState = new State<V>(nextDId, dStates.getValues()); // a FA's state inherits every value of the TG's subset of states that aggregates.
					if (!automaton.containsState(dState))
						automaton.addState(dState);
					if (this.getFinals().containsSome(dStates)) // a FA's state is final if it aggregates at least one TG's final state.
						automaton.addAsFinal(dState);
					if (!completeStates.contains(dStates))
						uncompleteStates.add(dStates);					
					automaton.addTransition(sState, dState, symbol);
				}				
			}
			completeStates.add(sStates);
			uncompleteStates.remove(sStates);
		}
		
		return automaton;
	}	
	
	/**
	 * <p>Returns the symbol-image of the specified subset of states.<p>
	 * <p>The algorithm has been derived from [A. Pettorossi "Automata Theory and Formal Languages (3rd edition)", def 2.3.6]<p>
	 * 
	 * @param states the subset of states.
	 * @param symbol the symbol.
	 * 
	 * @return the symbol-image of the specified subset of states.
	 */
	public States<V> getImage(States<V> states, Character symbol) {
		States<V> images = new States<V>();
		for (State<V> state : states)
			images.addAll(this.getImage(state, symbol)); // the v-image of a subset of states is the union of the v-images of all the states in the subset.
		return images;
	}	
	
	/**
	 * <p>Returns the symbol-image of the specified state.<p>
	 * <p>The algorithm has been derived from [A. Pettorossi "Automata Theory and Formal Languages (3rd edition)", def. 2.3.2]<p>
	 * 
	 * @param state the state.
	 * @param symbol the symbol.
	 * 
	 * @return the symbol-image of the specified state.
	 */
	public States<V> getImage(State<V> state, Character symbol) {
		if (symbol.equals(Grammar.EPSILON))
			return this.getEpsilonImage(state);		
		States<V> images = new States<V>();
		for (State<V> epsilonImageState : this.getEpsilonImage(state)) // the v-image of a state is the subset of states reacheable with e-moves ...
			images.addAll(this.getTransitions(epsilonImageState, symbol)); // ... followed by a v-move ...
		images.addAll(this.getEpsilonImage(images)); // ... and the subset of states reacheable with e-moves.
		return images;
	}
	
	private States<V> getEpsilonImage(States<V> states) {
		States<V> images = new States<V>();
		for (State<V> state : states)
			images.addAll(this.getEpsilonImage(state));
		return images;
	}
	
	private States<V> getEpsilonImage(State<V> state) {
		return this.getEpsilonImage(new States<V>(), state);
	}
	
	private States<V> getEpsilonImage(States<V> images, State<V> state) {
		images.add(state);
		States<V> epsilonMoves = this.getTransitions(state, Grammar.EPSILON);
		for (State<V> epsilonMove : epsilonMoves)
			if (!images.contains(epsilonMove))
				images.addAll(this.getEpsilonImage(images, epsilonMove));
		return images;
	}
	
	/* (non-Javadoc)
	 * @see com.gmarciani.gmparser.models.automaton.AbstractAutomaton#isAccepted(java.lang.String)
	 */
	@Override public boolean isAccepted(String word) {
		States<V> currentStates = new States<V>(this.getInitial());
		for (Character symbol : word.toCharArray())
			currentStates = this.getImage(currentStates, symbol);	
		for (State<V> state : this.getEpsilonImage(currentStates))
			if (this.isFinal(state))
				return true;		
		return false;
	}
	
	@Override public String toString() {
		return "TransitionGraph(" + 
				this.getStates() + "," + 
				this.getAlphabet() + "," + 
				this.getInitial() + "," + 
				this.getFinals() + "," + 
				this.getTransitionFunction() + ")";
	}	

}
