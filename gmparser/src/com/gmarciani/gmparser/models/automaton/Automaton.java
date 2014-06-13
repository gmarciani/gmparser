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

import com.gmarciani.gmparser.models.automaton.state.State;
import com.gmarciani.gmparser.models.automaton.state.States;
import com.gmarciani.gmparser.models.commons.nple.Triple;
import com.gmarciani.gmparser.models.commons.set.GSet;
import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;

public interface Automaton<V> {
	
	public States<V> getStates();
	public Alphabet getAlphabet();
	public State<V> getInitialState();
	public States<V> getFinalStates();
	
	public Integer getNextId();
	
	public boolean addState(State<V> state);
	public void addAsInitialState(State<V> state);
	public void addAsFinalState(State<V> state);
	public boolean removeState(State<V> state);
	public boolean containsState(State<V> state);
	public void removeFromFinalStates(State<V> state);	
	public boolean isInitialState(State<V> state);
	public boolean isFinalState(State<V> state);
	
	public boolean addSymbol(Character symbol);	
	public boolean removeSymbol(Character symbol);
	public boolean containsSymbol(Character symbol);
	
	public boolean addTransition(State<V> sState, State<V> dState, Character symbol);
	public boolean removeTransition(State<V> sState, State<V> dState, Character symbol);	
	public States<V> getTransitions(State<V> sState, Character symbol);
	public State<V> getTransition(State<V> sState, Character symbol);
	public boolean containsTransition(State<V> sState, State<V> dState, Character symbol);
	public GSet<Triple<State<V>, Character, State<V>>> getAllTransitions();
	
	public boolean isAccepted(String word);
	
	public String toFormattedAutomaton();
	public String toExtendedFormattedAutomaton();

}
