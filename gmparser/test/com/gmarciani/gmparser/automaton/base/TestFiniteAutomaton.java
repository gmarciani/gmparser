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

import com.gmarciani.gmparser.models.automaton.Automaton;
import com.gmarciani.gmparser.models.automaton.finite.FiniteAutomaton;
import com.gmarciani.gmparser.models.automaton.state.State;

public class TestFiniteAutomaton {

	@Test public void create() {
		State stateOne = new State(1);
		State stateTwo = new State(2);
		State stateThree = new State(3);
		
		Automaton automaton = new FiniteAutomaton(stateOne);
		automaton.addState(stateTwo);
		automaton.addAsFinalState(stateThree);
		
		automaton.addTransition(stateOne, stateTwo, 'a');
		automaton.addTransition(stateTwo, stateOne, 'b');
		automaton.addTransition(stateTwo, stateThree, 'c');
		
		assertTrue("Uncorrect creation (missing states)", 
				automaton.containsState(stateOne)
				&& automaton.containsState(stateTwo)
				&& automaton.containsState(stateThree));
		
		assertTrue("Uncorrect creation (missing initial state)",
				automaton.isInitialState(stateOne));
		
		assertTrue("Uncorrect creation (missing final states)",
				automaton.isFinalState(stateThree));
		
		assertTrue("Uncorrect creation (missing symbols)", 
				automaton.containsSymbol('a')
				&& automaton.containsSymbol('b')
				&& automaton.containsSymbol('c'));
		
		assertTrue("Uncorrect creation (missing transitions)", 
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
		
		assertTrue("Uncorrect creation (missing states)", 
				automaton.containsState(stateOne)
				&& automaton.containsState(stateTwo)
				&& automaton.containsState(stateThree));
		
		assertTrue("Uncorrect creation (missing initial state)",
				automaton.isInitialState(stateOne));
		
		assertTrue("Uncorrect creation (missing final states)",
				automaton.isFinalState(stateThree));
		
		assertTrue("Uncorrect creation (missing symbols)", 
				automaton.containsSymbol('a')
				&& automaton.containsSymbol('b')
				&& automaton.containsSymbol('c'));
		
		assertTrue("Uncorrect creation (missing transitions)", 
				automaton.containsTransition(stateOne, stateTwo, 'a')
				&& automaton.containsTransition(stateTwo, stateOne, 'b')
				&& automaton.containsTransition(stateTwo, stateThree, 'c'));
	}
	
	@Test public void accept() {
		State stateOne = new State(1);
		State stateTwo = new State(2);
		State stateThree = new State(3);
		
		Automaton automaton = new FiniteAutomaton(stateOne);
		automaton.addState(stateTwo);
		automaton.addAsFinalState(stateThree);
		
		automaton.addTransition(stateOne, stateTwo, 'a');
		automaton.addTransition(stateTwo, stateOne, 'b');
		automaton.addTransition(stateTwo, stateThree, 'c');		
		
		assertTrue("Uncorrect creation (missing states)", 
				automaton.containsState(stateOne)
				&& automaton.containsState(stateTwo)
				&& automaton.containsState(stateThree));
		
		assertTrue("Uncorrect creation (missing initial state)",
				automaton.isInitialState(stateOne));
		
		assertTrue("Uncorrect creation (missing final states)",
				automaton.isFinalState(stateThree));
		
		assertTrue("Uncorrect creation (missing symbols)", 
				automaton.containsSymbol('a')
				&& automaton.containsSymbol('b')
				&& automaton.containsSymbol('c'));
		
		assertTrue("Uncorrect creation (missing transitions)", 
				automaton.containsTransition(stateOne, stateTwo, 'a')
				&& automaton.containsTransition(stateTwo, stateOne, 'b')
				&& automaton.containsTransition(stateTwo, stateThree, 'c'));
		
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
		State stateOne = new State(1);
		State stateTwo = new State(2);
		State stateThree = new State(3);
		
		Automaton automaton = new FiniteAutomaton(stateOne);
		automaton.addState(stateTwo);
		automaton.addAsFinalState(stateThree);
		
		automaton.addTransition(stateOne, stateTwo, 'a');
		automaton.addTransition(stateTwo, stateOne, 'b');
		automaton.addTransition(stateTwo, stateThree, 'c');
		
		assertTrue("Uncorrect creation (missing states)", 
				automaton.containsState(stateOne)
				&& automaton.containsState(stateTwo)
				&& automaton.containsState(stateThree));
		
		assertTrue("Uncorrect creation (missing initial state)",
				automaton.isInitialState(stateOne));
		
		assertTrue("Uncorrect creation (missing final states)",
				automaton.isFinalState(stateThree));
		
		assertTrue("Uncorrect creation (missing symbols)", 
				automaton.containsSymbol('a')
				&& automaton.containsSymbol('b')
				&& automaton.containsSymbol('c'));
		
		assertTrue("Uncorrect creation (missing transitions)", 
				automaton.containsTransition(stateOne, stateTwo, 'a')
				&& automaton.containsTransition(stateTwo, stateOne, 'b')
				&& automaton.containsTransition(stateTwo, stateThree, 'c'));
		
		System.out.println(automaton);
		System.out.println(automaton.toFormattedAutomaton());
	}

}
