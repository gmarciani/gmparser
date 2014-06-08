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

import org.junit.Before;
import org.junit.Test;

import com.gmarciani.gmparser.models.automaton.Automaton;
import com.gmarciani.gmparser.models.automaton.finite.FiniteAutomaton;
import com.gmarciani.gmparser.models.automaton.state.State;
import com.gmarciani.gmparser.models.automaton.state.States;
import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;

public class TestFiniteAutomaton {
	
	private FiniteAutomaton createFiniteAutomaton() {
		State stateOne = new State(1);
		State stateTwo = new State(2);
		State stateThree = new State(3);
		
		FiniteAutomaton automaton = new FiniteAutomaton(stateOne);
		automaton.addState(stateTwo);
		automaton.addAsFinalState(stateThree);
		
		automaton.addTransition(stateOne, stateTwo, 'a');
		automaton.addTransition(stateTwo, stateOne, 'b');
		automaton.addTransition(stateTwo, stateThree, 'c');
		
		return automaton;
	}

	@Test @Before public void create() {
		FiniteAutomaton automaton = this.createFiniteAutomaton();
		
		State stateOne = automaton.getStates().getState(1);
		State stateTwo = automaton.getStates().getState(2);
		State stateThree = automaton.getStates().getState(3);
		
		States expectedStates = new States(stateOne, stateTwo, stateThree);
		State expectedInitialState = stateOne;
		States expectedFinalStates = new States(stateThree);
		Alphabet expectedAlphabet = new Alphabet('a', 'b', 'c');
		
		assertEquals("Uncorrect finite-automaton creation (states)", 
				expectedStates, automaton.getStates());
		
		assertEquals("Uncorrect finite-automaton creation (initial state)",
				expectedInitialState, automaton.getInitialState());
		
		assertEquals("Uncorrect finite-automaton creation (final states)",
				expectedFinalStates, automaton.getFinalStates());
		
		assertEquals("Uncorrect finite-automaton creation (alphabet)", 
				expectedAlphabet, automaton.getAlphabet());
		
		assertTrue("Uncorrect finite-automaton creation (missing transitions)", 
				automaton.containsTransition(stateOne, stateTwo, 'a')
				&& automaton.containsTransition(stateTwo, stateOne, 'b')
				&& automaton.containsTransition(stateTwo, stateThree, 'c'));
	}
	
	@Test public void createWithTransitions() {
		State stateOne = new State(1);
		State stateTwo = new State(2);
		State stateThree = new State(3);
		
		Automaton automaton = new FiniteAutomaton(stateOne);			
		automaton.addTransition(stateOne, stateTwo, 'a');
		automaton.addTransition(stateTwo, stateOne, 'b');
		automaton.addTransition(stateTwo, stateThree, 'c');
		
		automaton.addAsFinalState(stateThree);
		
		States expectedStates = new States(stateOne, stateTwo, stateThree);
		State expectedInitialState = stateOne;
		States expectedFinalStates = new States(stateThree);
		Alphabet expectedAlphabet = new Alphabet('a', 'b', 'c');
		
		assertEquals("Uncorrect finite-automaton creation (states)", 
				expectedStates, automaton.getStates());
		
		assertEquals("Uncorrect finite-automaton creation (initial state)",
				expectedInitialState, automaton.getInitialState());
		
		assertEquals("Uncorrect finite-automaton creation (final states)",
				expectedFinalStates, automaton.getFinalStates());
		
		assertEquals("Uncorrect finite-automaton creation (alphabet)", 
				expectedAlphabet, automaton.getAlphabet());
		
		assertTrue("Uncorrect finite-automaton creation (missing transitions)", 
				automaton.containsTransition(stateOne, stateTwo, 'a')
				&& automaton.containsTransition(stateTwo, stateOne, 'b')
				&& automaton.containsTransition(stateTwo, stateThree, 'c'));
	}
	
	@Test public void accept() {
		FiniteAutomaton automaton = this.createFiniteAutomaton();
		
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
		FiniteAutomaton automaton = this.createFiniteAutomaton();
		
		System.out.println(automaton);
		System.out.println(automaton.toFormattedAutomaton());
	}

}
