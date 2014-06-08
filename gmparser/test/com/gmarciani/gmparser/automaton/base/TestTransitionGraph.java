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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.gmarciani.gmparser.models.automaton.graph.TransitionGraph;
import com.gmarciani.gmparser.models.automaton.state.State;
import com.gmarciani.gmparser.models.automaton.state.States;
import com.gmarciani.gmparser.models.grammar.Grammar;

public class TestTransitionGraph {

	@Test public void create() {
		State stateOne = new State(1);
		State stateTwo = new State(2);
		State stateThree = new State(3);
		State stateFour = new State(4);
		
		TransitionGraph automaton = new TransitionGraph(stateOne);
		automaton.addState(stateTwo);
		automaton.addState(stateThree);
		automaton.addAsFinalState(stateFour);
		
		automaton.addTransition(stateOne, stateTwo, 'a');
		automaton.addTransition(stateOne, stateTwo, Grammar.EPSILON);
		automaton.addTransition(stateTwo, stateOne, 'b');
		automaton.addTransition(stateTwo, stateThree, 'c');
		automaton.addTransition(stateTwo, stateFour, Grammar.EPSILON);
		automaton.addTransition(stateThree, stateFour, 'c');
		automaton.addTransition(stateFour, stateOne, 'd');
		automaton.addTransition(stateFour, stateTwo, Grammar.EPSILON);
		
		assertTrue("Uncorrect creation (missing states)", 
				automaton.containsState(stateOne)
				&& automaton.containsState(stateTwo)
				&& automaton.containsState(stateThree)
				&& automaton.containsState(stateFour));
		
		assertTrue("Uncorrect creation (missing initial state)",
				automaton.isInitialState(stateOne));
		
		assertTrue("Uncorrect creation (missing final states)",
				automaton.isFinalState(stateFour));
		
		assertTrue("Uncorrect creation (missing symbols)", 
				automaton.containsSymbol('a')
				&& automaton.containsSymbol('b')
				&& automaton.containsSymbol('c')
				&& automaton.containsSymbol('d'));
		
		assertTrue("Uncorrect creation (missing transitions)", 
				automaton.containsTransition(stateOne, stateTwo, 'a')
				&& automaton.containsTransition(stateOne, stateTwo, Grammar.EPSILON)
				&& automaton.containsTransition(stateTwo, stateOne, 'b')
				&& automaton.containsTransition(stateTwo, stateThree, 'c')
				&& automaton.containsTransition(stateTwo, stateFour, Grammar.EPSILON)
				&& automaton.containsTransition(stateThree, stateFour, 'c')
				&& automaton.containsTransition(stateFour, stateOne, 'd')
				&& automaton.containsTransition(stateFour, stateTwo, Grammar.EPSILON));
	}
	
	@Test public void createWithTransitions() {
		State stateOne = new State(1);
		State stateTwo = new State(2);
		State stateThree = new State(3);
		State stateFour = new State(4);
		
		TransitionGraph automaton = new TransitionGraph(stateOne);		
		automaton.addTransition(stateOne, stateTwo, 'a');
		automaton.addTransition(stateOne, stateTwo, Grammar.EPSILON);
		automaton.addTransition(stateTwo, stateOne, 'b');
		automaton.addTransition(stateTwo, stateThree, 'c');
		automaton.addTransition(stateTwo, stateFour, Grammar.EPSILON);
		automaton.addTransition(stateThree, stateFour, 'c');
		automaton.addTransition(stateFour, stateOne, 'd');
		automaton.addTransition(stateFour, stateTwo, Grammar.EPSILON);
		
		automaton.addAsFinalState(stateFour);
		
		assertTrue("Uncorrect creation (missing states)", 
				automaton.containsState(stateOne)
				&& automaton.containsState(stateTwo)
				&& automaton.containsState(stateThree)
				&& automaton.containsState(stateFour));
		
		assertTrue("Uncorrect creation (missing initial state)",
				automaton.isInitialState(stateOne));
		
		assertTrue("Uncorrect creation (missing final states)",
				automaton.isFinalState(stateFour));
		
		assertTrue("Uncorrect creation (missing symbols)", 
				automaton.containsSymbol('a')
				&& automaton.containsSymbol('b')
				&& automaton.containsSymbol('c')
				&& automaton.containsSymbol('d'));
		
		assertTrue("Uncorrect creation (missing transitions)", 
				automaton.containsTransition(stateOne, stateTwo, 'a')
				&& automaton.containsTransition(stateOne, stateTwo, Grammar.EPSILON)
				&& automaton.containsTransition(stateTwo, stateOne, 'b')
				&& automaton.containsTransition(stateTwo, stateThree, 'c')
				&& automaton.containsTransition(stateTwo, stateFour, Grammar.EPSILON)
				&& automaton.containsTransition(stateThree, stateFour, 'c')
				&& automaton.containsTransition(stateFour, stateOne, 'd')
				&& automaton.containsTransition(stateFour, stateTwo, Grammar.EPSILON));
	}
	
	@Test public void represent() {
		State stateOne = new State(1);
		State stateTwo = new State(2);
		State stateThree = new State(3);
		State stateFour = new State(4);
		
		TransitionGraph automaton = new TransitionGraph(stateOne);
		automaton.addState(stateTwo);
		automaton.addState(stateThree);
		automaton.addAsFinalState(stateFour);
		
		automaton.addTransition(stateOne, stateTwo, 'a');
		automaton.addTransition(stateOne, stateTwo, Grammar.EPSILON);
		automaton.addTransition(stateTwo, stateOne, 'b');
		automaton.addTransition(stateTwo, stateThree, 'c');
		automaton.addTransition(stateTwo, stateFour, Grammar.EPSILON);
		automaton.addTransition(stateThree, stateFour, 'c');
		automaton.addTransition(stateFour, stateOne, 'd');
		automaton.addTransition(stateFour, stateTwo, Grammar.EPSILON);
		
		assertTrue("Uncorrect creation (missing states)", 
				automaton.containsState(stateOne)
				&& automaton.containsState(stateTwo)
				&& automaton.containsState(stateThree)
				&& automaton.containsState(stateFour));
		
		assertTrue("Uncorrect creation (missing initial state)",
				automaton.isInitialState(stateOne));
		
		assertTrue("Uncorrect creation (missing final states)",
				automaton.isFinalState(stateFour));
		
		assertTrue("Uncorrect creation (missing symbols)", 
				automaton.containsSymbol('a')
				&& automaton.containsSymbol('b')
				&& automaton.containsSymbol('c')
				&& automaton.containsSymbol('d'));
		
		assertTrue("Uncorrect creation (missing transitions)", 
				automaton.containsTransition(stateOne, stateTwo, 'a')
				&& automaton.containsTransition(stateOne, stateTwo, Grammar.EPSILON)
				&& automaton.containsTransition(stateTwo, stateOne, 'b')
				&& automaton.containsTransition(stateTwo, stateThree, 'c')
				&& automaton.containsTransition(stateTwo, stateFour, Grammar.EPSILON)
				&& automaton.containsTransition(stateThree, stateFour, 'c')
				&& automaton.containsTransition(stateFour, stateOne, 'd')
				&& automaton.containsTransition(stateFour, stateTwo, Grammar.EPSILON));
		
		System.out.println(automaton);
		System.out.println(automaton.toFormattedAutomaton());
	}
	
	@Test public void computeImages() {
		State stateOne = new State(1);
		State stateTwo = new State(2);
		State stateThree = new State(3);
		State stateFour = new State(4);
		
		TransitionGraph automaton = new TransitionGraph(stateOne);
		automaton.addState(stateTwo);
		automaton.addState(stateThree);
		automaton.addAsFinalState(stateFour);
		
		automaton.addTransition(stateOne, stateTwo, 'a');
		automaton.addTransition(stateOne, stateTwo, Grammar.EPSILON);
		automaton.addTransition(stateTwo, stateOne, 'b');
		automaton.addTransition(stateTwo, stateThree, 'c');
		automaton.addTransition(stateTwo, stateFour, Grammar.EPSILON);
		automaton.addTransition(stateThree, stateFour, 'c');
		automaton.addTransition(stateFour, stateOne, 'd');
		automaton.addTransition(stateFour, stateTwo, Grammar.EPSILON);
		
		assertTrue("Uncorrect creation (missing states)", 
				automaton.containsState(stateOne)
				&& automaton.containsState(stateTwo)
				&& automaton.containsState(stateThree)
				&& automaton.containsState(stateFour));
		
		assertTrue("Uncorrect creation (missing initial state)",
				automaton.isInitialState(stateOne));
		
		assertTrue("Uncorrect creation (missing final states)",
				automaton.isFinalState(stateFour));
		
		assertTrue("Uncorrect creation (missing symbols)", 
				automaton.containsSymbol('a')
				&& automaton.containsSymbol('b')
				&& automaton.containsSymbol('c')
				&& automaton.containsSymbol('d'));
		
		assertTrue("Uncorrect creation (missing transitions)", 
				automaton.containsTransition(stateOne, stateTwo, 'a')
				&& automaton.containsTransition(stateOne, stateTwo, Grammar.EPSILON)
				&& automaton.containsTransition(stateTwo, stateOne, 'b')
				&& automaton.containsTransition(stateTwo, stateThree, 'c')
				&& automaton.containsTransition(stateTwo, stateFour, Grammar.EPSILON)
				&& automaton.containsTransition(stateThree, stateFour, 'c')
				&& automaton.containsTransition(stateFour, stateOne, 'd')
				&& automaton.containsTransition(stateFour, stateTwo, Grammar.EPSILON));
		
		States imageOneA = new States(2, 4);
		States imageOneB = new States(1, 2, 4);
		States imageOneC = new States(3);
		States imageOneD = new States(1, 2, 4);
		
		States imageTwoA = new States();
		States imageTwoB = new States(1, 2, 4);
		States imageTwoC = new States(3);
		States imageTwoD = new States(1, 2, 4);
		
		States imageThreeA = new States();
		States imageThreeB = new States();
		States imageThreeC = new States(2, 4);
		States imageThreeD = new States();
		
		States imageFourA = new States();
		States imageFourB = new States(1, 2, 4);
		States imageFourC = new States(3);
		States imageFourD = new States(1, 2, 4);
		
		States imageOneTwoA = new States(imageOneA, imageTwoA);
		States imageOneTwoB = new States(imageOneB, imageTwoB);
		States imageOneTwoC = new States(imageOneC, imageTwoC);
		States imageOneTwoD = new States(imageOneD, imageTwoD);
		
		States imageOneThreeA = new States(imageOneA, imageThreeA);
		States imageOneThreeB = new States(imageOneB, imageThreeB);
		States imageOneThreeC = new States(imageOneC, imageThreeC);
		States imageOneThreeD = new States(imageOneD, imageThreeD);
		
		States imageOneFourA = new States(imageOneA, imageFourA);
		States imageOneFourB = new States(imageOneB, imageFourB);
		States imageOneFourC = new States(imageOneC, imageFourC);
		States imageOneFourD = new States(imageOneD, imageFourD);
		
		States imageTwoThreeA = new States(imageTwoA, imageThreeA);
		States imageTwoThreeB = new States(imageTwoB, imageThreeB);
		States imageTwoThreeC = new States(imageTwoC, imageThreeC);
		States imageTwoThreeD = new States(imageTwoD, imageThreeD);
		
		States imageTwoFourA = new States(imageTwoA, imageFourA);
		States imageTwoFourB = new States(imageTwoB, imageFourB);
		States imageTwoFourC = new States(imageTwoC, imageFourC);
		States imageTwoFourD = new States(imageTwoD, imageFourD);
		
		States imageThreeFourA = new States(imageThreeA, imageFourA);
		States imageThreeFourB = new States(imageThreeB, imageFourB);
		States imageThreeFourC = new States(imageThreeC, imageFourC);
		States imageThreeFourD = new States(imageThreeD, imageFourD);
		
		assertTrue("Uncorrect state images computation", 
				automaton.getImage(stateOne, 'a').equals(imageOneA)
				&& automaton.getImage(stateOne, 'b').equals(imageOneB)
				&& automaton.getImage(stateOne, 'c').equals(imageOneC)
				&& automaton.getImage(stateOne, 'd').equals(imageOneD)
				&& automaton.getImage(stateTwo, 'a').equals(imageTwoA)
				&& automaton.getImage(stateTwo, 'b').equals(imageTwoB)
				&& automaton.getImage(stateTwo, 'c').equals(imageTwoC)
				&& automaton.getImage(stateTwo, 'd').equals(imageTwoD)
				&& automaton.getImage(stateThree, 'a').equals(imageThreeA)
				&& automaton.getImage(stateThree, 'b').equals(imageThreeB)
				&& automaton.getImage(stateThree, 'c').equals(imageThreeC)
				&& automaton.getImage(stateThree, 'd').equals(imageThreeD)
				&& automaton.getImage(stateFour, 'a').equals(imageFourA)
				&& automaton.getImage(stateFour, 'b').equals(imageFourB)
				&& automaton.getImage(stateFour, 'c').equals(imageFourC)
				&& automaton.getImage(stateFour, 'd').equals(imageFourD));
		
		assertTrue("Uncorrect states images computation",
				automaton.getImage(new States(stateOne, stateTwo), 'a').equals(imageOneTwoA)
				&& automaton.getImage(new States(stateOne, stateTwo), 'b').equals(imageOneTwoB)
				&& automaton.getImage(new States(stateOne, stateTwo), 'c').equals(imageOneTwoC)
				&& automaton.getImage(new States(stateOne, stateTwo), 'd').equals(imageOneTwoD)
				&& automaton.getImage(new States(stateOne, stateThree), 'a').equals(imageOneThreeA)
				&& automaton.getImage(new States(stateOne, stateThree), 'b').equals(imageOneThreeB)
				&& automaton.getImage(new States(stateOne, stateThree), 'c').equals(imageOneThreeC)
				&& automaton.getImage(new States(stateOne, stateThree), 'd').equals(imageOneThreeD)
				&& automaton.getImage(new States(stateOne, stateFour), 'a').equals(imageOneFourA)
				&& automaton.getImage(new States(stateOne, stateFour), 'b').equals(imageOneFourB)
				&& automaton.getImage(new States(stateOne, stateFour), 'c').equals(imageOneFourC)
				&& automaton.getImage(new States(stateOne, stateFour), 'd').equals(imageOneFourD)
				&& automaton.getImage(new States(stateTwo, stateThree), 'a').equals(imageTwoThreeA)
				&& automaton.getImage(new States(stateTwo, stateThree), 'b').equals(imageTwoThreeB)
				&& automaton.getImage(new States(stateTwo, stateThree), 'c').equals(imageTwoThreeC)
				&& automaton.getImage(new States(stateTwo, stateThree), 'd').equals(imageTwoThreeD)
				&& automaton.getImage(new States(stateTwo, stateFour), 'a').equals(imageTwoFourA)
				&& automaton.getImage(new States(stateTwo, stateFour), 'b').equals(imageTwoFourB)
				&& automaton.getImage(new States(stateTwo, stateFour), 'c').equals(imageTwoFourC)
				&& automaton.getImage(new States(stateTwo, stateFour), 'd').equals(imageTwoFourD)
				&& automaton.getImage(new States(stateThree, stateFour), 'a').equals(imageThreeFourA)
				&& automaton.getImage(new States(stateThree, stateFour), 'b').equals(imageThreeFourB)
				&& automaton.getImage(new States(stateThree, stateFour), 'c').equals(imageThreeFourC)
				&& automaton.getImage(new States(stateThree, stateFour), 'd').equals(imageThreeFourD));
	}
	
	@Test public void accept() {
		State stateOne = new State(1);
		State stateTwo = new State(2);
		State stateThree = new State(3);
		State stateFour = new State(4);
		
		TransitionGraph automaton = new TransitionGraph(stateOne);
		automaton.addState(stateTwo);
		automaton.addState(stateThree);
		automaton.addAsFinalState(stateFour);
		
		automaton.addTransition(stateOne, stateTwo, 'a');
		automaton.addTransition(stateOne, stateTwo, Grammar.EPSILON);
		automaton.addTransition(stateTwo, stateOne, 'b');
		automaton.addTransition(stateTwo, stateThree, 'c');
		automaton.addTransition(stateTwo, stateFour, Grammar.EPSILON);
		automaton.addTransition(stateThree, stateFour, 'c');
		automaton.addTransition(stateFour, stateOne, 'd');
		automaton.addTransition(stateFour, stateTwo, Grammar.EPSILON);
		
		assertTrue("Uncorrect creation (missing states)", 
				automaton.containsState(stateOne)
				&& automaton.containsState(stateTwo)
				&& automaton.containsState(stateThree)
				&& automaton.containsState(stateFour));
		
		assertTrue("Uncorrect creation (missing initial state)",
				automaton.isInitialState(stateOne));
		
		assertTrue("Uncorrect creation (missing final states)",
				automaton.isFinalState(stateFour));
		
		assertTrue("Uncorrect creation (missing symbols)", 
				automaton.containsSymbol('a')
				&& automaton.containsSymbol('b')
				&& automaton.containsSymbol('c')
				&& automaton.containsSymbol('d'));
		
		assertTrue("Uncorrect creation (missing transitions)", 
				automaton.containsTransition(stateOne, stateTwo, 'a')
				&& automaton.containsTransition(stateOne, stateTwo, Grammar.EPSILON)
				&& automaton.containsTransition(stateTwo, stateOne, 'b')
				&& automaton.containsTransition(stateTwo, stateThree, 'c')
				&& automaton.containsTransition(stateTwo, stateFour, Grammar.EPSILON)
				&& automaton.containsTransition(stateThree, stateFour, 'c')
				&& automaton.containsTransition(stateFour, stateOne, 'd')
				&& automaton.containsTransition(stateFour, stateTwo, Grammar.EPSILON));
		
		String wordToAccept[] = {"", "b", "d", "bdbbdd", "a", "ab", "ad", "adba", "abdbbddba", "abadacc", "bdbbdda", "bdbbddababadadabada", "cc", "cccc", "ccccbadabddcc"};
		String wordToNotAccept[] = {"abcd", "ababac", "adadac", "cababab", "aaabbcab"};
		
		for (String word : wordToAccept)
			assertTrue("Uncorrect acceptance (should be accepted: " + word + ")", 
					automaton.isAccepted(word));
		
		for (String word : wordToNotAccept)
			assertFalse("Uncorrect acceptance (should not be accepted: " + word + ")", 
					automaton.isAccepted(word));
	}

}
