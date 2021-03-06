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

import static org.junit.Assert.*;

import org.junit.Test;

import com.gmarciani.gmparser.models.automaton.FiniteAutomaton;
import com.gmarciani.gmparser.models.automaton.state.State;
import com.gmarciani.gmparser.models.automaton.state.States;
import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;

public class TestFiniteAutomaton {
	
	private FiniteAutomaton<String> createFiniteAutomaton() {
		State<String> stateOne = new State<String>(1, "one");
		State<String> stateTwo = new State<String>(2, "two");
		State<String> stateThree = new State<String>(3, "three");
		
		FiniteAutomaton<String> automaton = new FiniteAutomaton<String>(stateOne);
		automaton.addState(stateTwo);
		automaton.addAsFinal(stateThree);
		
		automaton.addSymbol('a');
		automaton.addSymbol('b');
		automaton.addSymbol('c');
		
		automaton.addTransition(stateOne, stateTwo, 'a');
		automaton.addTransition(stateTwo, stateOne, 'b');
		automaton.addTransition(stateTwo, stateThree, 'c');
		
		System.out.println(automaton.toFormattedAutomaton());
		
		return automaton;
	}

	@Test public void create() {
		FiniteAutomaton<String> automaton = this.createFiniteAutomaton();
		
		State<String> stateOne = automaton.getStates().getState(1);
		State<String> stateTwo = automaton.getStates().getState(2);
		State<String> stateThree = automaton.getStates().getState(3);
		
		States<String> expectedStates = new States<String>();
		expectedStates.add(stateOne);
		expectedStates.add(stateTwo);
		expectedStates.add(stateThree);
		State<String> expectedInitialState = stateOne;
		States<String> expectedFinalStates = new States<String>(stateThree);
		Alphabet expectedAlphabet = new Alphabet('a', 'b', 'c');
		
		assertEquals("Uncorrect finite-automaton creation (states)", 
				expectedStates, automaton.getStates());
		
		assertEquals("Uncorrect finite-automaton creation (initial state)",
				expectedInitialState, automaton.getInitial());
		
		assertEquals("Uncorrect finite-automaton creation (final states)",
				expectedFinalStates, automaton.getFinals());
		
		assertEquals("Uncorrect finite-automaton creation (alphabet)", 
				expectedAlphabet, automaton.getAlphabet());
		
		assertTrue("Uncorrect finite-automaton creation (missing transitions)", 
				automaton.containsTransition(stateOne, stateTwo, 'a')
				&& automaton.containsTransition(stateTwo, stateOne, 'b')
				&& automaton.containsTransition(stateTwo, stateThree, 'c'));
	}
	
	@Test public void modify() {
		FiniteAutomaton<String> automaton = this.createFiniteAutomaton();		
		
		State<String> stateOne = automaton.getStates().getState(1);
		State<String> stateTwo = automaton.getStates().getState(2);
		State<String> stateThree = automaton.getStates().getState(3);
		State<String> stateFour = new State<String>(4, "four");
		State<String> stateFive = new State<String>(5, "five");
		
		automaton.addState(stateFour);
		automaton.addState(stateFive);
		
		automaton.addTransition(stateFour, stateOne, 'a');
		automaton.addTransition(stateFour, stateTwo, 'b');
		automaton.addTransition(stateFour, stateFive, 'c');
		
		automaton.addAsInitial(stateFour);
		automaton.removeFromFinals(stateThree);
		automaton.addAsFinal(stateOne);
		automaton.addAsFinal(stateFive);
		automaton.removeState(stateTwo);
		automaton.removeSymbol('b');
		
		States<String> expectedStates = new States<String>();
		expectedStates.add(stateOne);
		expectedStates.add(stateThree);
		expectedStates.add(stateFour);
		expectedStates.add(stateFive);
		State<String> expectedInitialState = stateFour;
		States<String> expectedFinalStates = new States<String>();
		expectedFinalStates.add(stateOne);
		expectedFinalStates.add(stateFive);
		Alphabet expectedAlphabet = new Alphabet('a', 'c');
		
		assertEquals("Uncorrect finite-automaton creation (states)", 
				expectedStates, automaton.getStates());
		
		assertEquals("Uncorrect finite-automaton creation (initial state)",
				expectedInitialState, automaton.getInitial());
		
		assertEquals("Uncorrect finite-automaton creation (final states)",
				expectedFinalStates, automaton.getFinals());
		
		assertEquals("Uncorrect finite-automaton creation (alphabet)", 
				expectedAlphabet, automaton.getAlphabet());
		
		assertTrue("Uncorrect finite-automaton creation (missing transitions)", 
				!automaton.containsTransition(stateOne, stateTwo, 'a')
				&& !automaton.containsTransition(stateTwo, stateOne, 'b')
				&& !automaton.containsTransition(stateTwo, stateThree, 'c')
				&& automaton.containsTransition(stateFour, stateOne, 'a')
				&& !automaton.containsTransition(stateFour, stateTwo, 'b')
				&& automaton.containsTransition(stateFour, stateFive, 'c'));
	}
	
	@Test public void accept() {
		FiniteAutomaton<String> automaton = this.createFiniteAutomaton();
		
		String wordToAccept[] = {"ac", "abac", "ababac", "abababac", "ababababac"};
		String wordToNotAccept[] = {"", "c", "a", "ab", "abc"};
		
		for (String word : wordToAccept)
			assertTrue("Uncorrect acceptance (should be accepted)", 
					automaton.isAccepted(word));
		
		for (String word : wordToNotAccept)
			assertFalse("Uncorrect acceptance (should not be accepted)", 
					automaton.isAccepted(word));
	}
	
	@Test public void represent() {
		FiniteAutomaton<String> automaton = this.createFiniteAutomaton();
		
		System.out.println(automaton);
		System.out.println(automaton.toExtendedFormattedAutomaton());
	}

}
