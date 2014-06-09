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

package com.gmarciani.gmparser.models.automaton.function;

import com.gmarciani.gmparser.models.automaton.state.State;
import com.gmarciani.gmparser.models.automaton.state.States;

public interface TransitionFunction {
	
	public boolean addTransition(State sState, State dState, Character symbol);
	
	public boolean removeTransition(State sState, State dState, Character symbol);
	public boolean removeAllTransitionsFromState(State state);
	public boolean removeAllTransitionsToState(State state);
	public boolean removeAllTransitionsFromStateToState(State sState, State dState);
	public boolean removeAllTransitionsBySymbol(Character symbol);
	public boolean removeAllTransitionsFromStateBySymbol(State state, Character symbol);
	public boolean removeAllTransitionsToStateBySymbol(State state, Character symbol);
	
	public State getTransition(State state, Character symbol);
	public States getTransitions(State state, Character symbol);
	
	public boolean containsTransition(State sState, State dState, Character symbol);
	
	public String toFormattedTransitionFunction();
	
}
