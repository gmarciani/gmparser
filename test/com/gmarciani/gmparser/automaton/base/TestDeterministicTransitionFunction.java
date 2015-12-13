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

package com.gmarciani.gmparser.automaton.base;

import org.junit.Test;

import com.gmarciani.gmparser.models.automaton.state.State;
import com.gmarciani.gmparser.models.automaton.state.States;
import com.gmarciani.gmparser.models.automaton.transition.DeterministicTransitionFunction;
import com.gmarciani.gmparser.models.automaton.transition.TransitionFunction;
import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;

public class TestDeterministicTransitionFunction {
	
	@Test public void createCompleteFunction() {
		System.out.println("#createCompleteFunction");
		State<String> stateOne = new State<String>(1, "one");
		State<String> stateTwo = new State<String>(2, "two");
		State<String> stateThree = new State<String>(3, "three");
		
		States<String> states = new States<String>();
		states.add(stateOne);
		states.add(stateTwo);
		states.add(stateThree);
		Alphabet alphabet = new Alphabet('a', 'b', 'c');
		
		TransitionFunction<String> function = new DeterministicTransitionFunction<String>(states, alphabet, states);
		for (State<String> state : states) {
			for (Character symbol : alphabet) {
				function.addTransition(state, state, symbol);
				function.addTransition(state, stateThree, symbol);
			}				
		}			
		
		System.out.println(function);
		System.out.println(function.toExtendedFormattedTransitionFunction());
	}
	
	@Test public void createIncompleteFunction() {
		System.out.println("#createIncompleteFunction");
		State<String> stateOne = new State<String>(1, "one");
		State<String> stateTwo = new State<String>(2, "two");
		State<String> stateThree = new State<String>(3, "three");
		
		States<String> states = new States<String>();
		states.add(stateOne);
		states.add(stateTwo);
		states.add(stateThree);
		Alphabet alphabet = new Alphabet('a', 'b', 'c');
		
		TransitionFunction<String> function = new DeterministicTransitionFunction<String>(states, alphabet, states);
		function.addTransition(stateOne, stateTwo, 'a');
		function.addTransition(stateTwo, stateThree, 'b');
		function.addTransition(stateThree, stateThree, 'c');
		
		System.out.println(function);
		System.out.println(function.toExtendedFormattedTransitionFunction());
	}
	
	@Test public void createCompleteAndRemoveAllStateSymbol() {
		System.out.println("#createCompleteAndRemoveAllStateSymbol");
		State<String> stateOne = new State<String>(1, "one");
		State<String> stateTwo = new State<String>(2, "two");
		State<String> stateThree = new State<String>(3, "three");
		
		States<String> states = new States<String>();
		states.add(stateOne);
		states.add(stateTwo);
		states.add(stateThree);
		Alphabet alphabet = new Alphabet('a', 'b', 'c');
		
		TransitionFunction<String> function = new DeterministicTransitionFunction<String>(states, alphabet, states);
		for (State<String> state : states) {
			for (Character symbol : alphabet) {
				function.addTransition(state, state, symbol);
				function.addTransition(state, stateThree, symbol);
			}				
		}		
		
		function.removeAllTransitionsFromStateBySymbol(stateTwo, 'b');
		
		System.out.println(function);
		System.out.println(function.toExtendedFormattedTransitionFunction());
	}
	
	@Test public void createCompleteAndRemoveAllState() {
		System.out.println("#createCompleteAndRemoveAllState");
		State<String> stateOne = new State<String>(1, "one");
		State<String> stateTwo = new State<String>(2, "two");
		State<String> stateThree = new State<String>(3, "three");
		
		States<String> states = new States<String>();
		states.add(stateOne);
		states.add(stateTwo);
		states.add(stateThree);
		Alphabet alphabet = new Alphabet('a', 'b', 'c');
		
		TransitionFunction<String> function = new DeterministicTransitionFunction<String>(states, alphabet, states);
		for (State<String> state : states) {
			for (Character symbol : alphabet) {
				function.addTransition(state, state, symbol);
				function.addTransition(state, stateThree, symbol);
			}				
		}		
		
		function.removeAllTransitionsFromState(stateTwo);
		
		System.out.println(function);
		System.out.println(function.toExtendedFormattedTransitionFunction());
	}
	
	@Test public void createCompleteAndRemoveAllSymbol() {
		System.out.println("#createCompleteAndRemoveAllSymbol");
		State<String> stateOne = new State<String>(1, "one");
		State<String> stateTwo = new State<String>(2, "two");
		State<String> stateThree = new State<String>(3, "three");
		
		States<String> states = new States<String>();
		states.add(stateOne);
		states.add(stateTwo);
		states.add(stateThree);
		Alphabet alphabet = new Alphabet('a', 'b', 'c');
		
		TransitionFunction<String> function = new DeterministicTransitionFunction<String>(states, alphabet, states);
		for (State<String> state : states) {
			for (Character symbol : alphabet) {
				function.addTransition(state, state, symbol);
				function.addTransition(state, stateThree, symbol);
			}				
		}			
		
		function.removeAllTransitionsBySymbol('b');
		
		System.out.println(function);
		System.out.println(function.toExtendedFormattedTransitionFunction());
	}	

	@Test public void createEmptyFunction() {
		System.out.println("#createEmptyFunction");
		State<String> stateOne = new State<String>(1, "one");
		State<String> stateTwo = new State<String>(2, "two");
		State<String> stateThree = new State<String>(3, "three");
		
		States<String> states = new States<String>();
		states.add(stateOne);
		states.add(stateTwo);
		states.add(stateThree);
		Alphabet alphabet = new Alphabet('a', 'b', 'c');
		
		TransitionFunction<String> function = new DeterministicTransitionFunction<String>(states, alphabet, states);
		
		System.out.println(function);
		System.out.println(function.toExtendedFormattedTransitionFunction());
	}
	
	@Test public void createEmptyStates() {
		System.out.println("#createEmptyStates");			
		States<String> states = new States<String>();
		Alphabet alphabet = new Alphabet('a', 'b', 'c');
		
		TransitionFunction<String> function = new DeterministicTransitionFunction<String>(states, alphabet, states);
		
		System.out.println(function);
		System.out.println(function.toExtendedFormattedTransitionFunction());
	}
	
	@Test public void createEmptyAlphabet() {
		System.out.println("#createEmptyAlphabet");
		State<String> stateOne = new State<String>(1, "one");
		State<String> stateTwo = new State<String>(2, "two");
		State<String> stateThree = new State<String>(3, "three");
		
		States<String> states = new States<String>();
		states.add(stateOne);
		states.add(stateTwo);
		states.add(stateThree);
		Alphabet alphabet = new Alphabet();
		
		TransitionFunction<String> function = new DeterministicTransitionFunction<String>(states, alphabet, states);
		
		System.out.println(function);
		System.out.println(function.toExtendedFormattedTransitionFunction());
	}
	
	@Test public void createEmptyStatesAlphabet() {
		System.out.println("#createEmptyStatesAlphabet");		
		States<String> states = new States<String>();
		Alphabet alphabet = new Alphabet();
		
		TransitionFunction<String> function = new DeterministicTransitionFunction<String>(states, alphabet, states);
		
		System.out.println(function);
		System.out.println(function.toExtendedFormattedTransitionFunction());
	}

}
