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
import com.gmarciani.gmparser.models.commons.nple.Triple;
import com.gmarciani.gmparser.models.commons.set.GSet;

/**
 * <p>The transition function QxV:Q.<p> 
 * <p>V is the type of values stored inside states.<p>
 * 
 * @see com.gmarciani.gmparser.models.automaton.FiniteAutomaton
 * @see com.gmarciani.gmparser.models.automaton.TransitionGraph
 * 
 * @author Giacomo Marciani
 * @version 1.0
 */
public interface TransitionFunction<V> {
	
	public boolean addTransition(State<V> sState, State<V> dState, Character symbol);
	
	public States<V> getStates();
	
	public boolean removeTransition(State<V> sState, State<V> dState, Character symbol);
	public boolean removeAllTransitionsFromState(State<V> state);
	public boolean removeAllTransitionsToState(State<V> state);
	public boolean removeAllTransitionsFromStateToState(State<V> sState, State<V> dState);
	public boolean removeAllTransitionsBySymbol(Character symbol);
	public boolean removeAllTransitionsFromStateBySymbol(State<V> state, Character symbol);
	public boolean removeAllTransitionsToStateBySymbol(State<V> state, Character symbol);
	
	public State<V> getTransition(State<V> state, Character symbol);
	public States<V> getTransitions(State<V> state, Character symbol);
	public GSet<Triple<State<V>, Character, State<V>>> getAllTransitions();
	
	public boolean containsTransition(State<V> sState, State<V> dState, Character symbol);
	
	public String toFormattedTransitionFunction();
	public String toExtendedFormattedTransitionFunction();
	
}
