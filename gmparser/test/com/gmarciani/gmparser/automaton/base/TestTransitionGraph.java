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

import com.gmarciani.gmparser.models.automaton.graph.TransitionGraph;
import com.gmarciani.gmparser.models.automaton.state.State;
import com.gmarciani.gmparser.models.automaton.state.States;
import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;

public class TestTransitionGraph {
	
	private TransitionGraph<String> createTransitionGraph() {
		State<String> stateOne = new State<String>(1, "one");
		State<String> stateTwo = new State<String>(2, "two");
		State<String> stateThree = new State<String>(3, "three");
		State<String> stateFour = new State<String>(4, "four");
		
		TransitionGraph<String> graph = new TransitionGraph<String>(stateOne);		
		graph.addState(stateTwo);
		graph.addState(stateThree);
		graph.addAsFinalState(stateFour);
		
		graph.addSymbol('a');
		graph.addSymbol('b');
		graph.addSymbol('c');
		graph.addSymbol('d');
		graph.addSymbol(Grammar.EPSILON);
		
		graph.addTransition(stateOne, stateTwo, 'a');
		graph.addTransition(stateOne, stateTwo, Grammar.EPSILON);
		graph.addTransition(stateTwo, stateOne, 'b');
		graph.addTransition(stateTwo, stateThree, 'c');
		graph.addTransition(stateTwo, stateFour, Grammar.EPSILON);
		graph.addTransition(stateThree, stateFour, 'c');
		graph.addTransition(stateFour, stateOne, 'd');
		graph.addTransition(stateFour, stateTwo, Grammar.EPSILON);
		
		return graph;
	}

	@Test public void create() {
		TransitionGraph<String> graph = this.createTransitionGraph();
		
		State<String> stateOne = graph.getStates().getState(1);
		State<String> stateTwo = graph.getStates().getState(2);
		State<String> stateThree = graph.getStates().getState(3);
		State<String> stateFour = graph.getStates().getState(4);
		
		States<String> expectedStates = new States<String>();
		expectedStates.add(stateOne);
		expectedStates.add(stateTwo);
		expectedStates.add(stateThree);
		expectedStates.add(stateFour);
		State<String> expectedInitialState = stateOne;
		States<String> expectedFinalStates = new States<String>(stateFour);
		Alphabet expectedAlphabet = new Alphabet('a', 'b', 'c', 'd', Grammar.EPSILON);
		
		assertEquals("Uncorrect transition-graph creation (states)", 
				expectedStates, graph.getStates());
		
		assertEquals("Uncorrect transition-graph creation (initial state)",
				expectedInitialState, graph.getInitialState());
		
		assertEquals("Uncorrect transition-graph creation (final states)",
				expectedFinalStates, graph.getFinalStates());
		
		assertEquals("Uncorrect transition-graph creation (alphabet)", 
				expectedAlphabet, graph.getAlphabet());
		
		assertTrue("Uncorrect transition-graph creation (missing transitions)", 
				graph.containsTransition(stateOne, stateTwo, 'a')
				&& graph.containsTransition(stateOne, stateTwo, Grammar.EPSILON)
				&& graph.containsTransition(stateTwo, stateOne, 'b')
				&& graph.containsTransition(stateTwo, stateThree, 'c')
				&& graph.containsTransition(stateTwo, stateFour, Grammar.EPSILON)
				&& graph.containsTransition(stateThree, stateFour, 'c')
				&& graph.containsTransition(stateFour, stateOne, 'd')
				&& graph.containsTransition(stateFour, stateTwo, Grammar.EPSILON));
	}
	/*
	@Test public void computeImages() {
		TransitionGraph<String> graph = this.createTransitionGraph();
		
		State<String> stateOne = graph.getStates().getState(1);
		State<String> stateTwo = graph.getStates().getState(2);
		State<String> stateThree = graph.getStates().getState(3);
		State<String> stateFour = graph.getStates().getState(4);
		
		States<String> imageOneA = new States<String>(stateTwo, stateFour);
		States<String> imageOneB = new States<String>(stateOne, stateTwo, stateFour);
		States<String> imageOneC = new States<String>(stateThree);
		States<String> imageOneD = new States<String>(stateOne, stateTwo, stateFour);
		
		States<String> imageTwoA = new States<String>();
		States<String> imageTwoB = new States<String>(stateOne, stateTwo, stateFour);
		States<String> imageTwoC = new States<String>(stateThree);
		States<String> imageTwoD = new States<String>(stateOne, stateTwo, stateFour);
		
		States<String> imageThreeA = new States<String>();
		States<String> imageThreeB = new States<String>();
		States<String> imageThreeC = new States<String>(stateTwo, stateFour);
		States<String> imageThreeD = new States<String>();
		
		States<String> imageFourA = new States<String>();
		States<String> imageFourB = new States<String>(stateOne, stateTwo, stateFour);
		States<String> imageFourC = new States<String>(stateThree);
		States<String> imageFourD = new States<String>(stateOne, stateTwo, stateFour);
		
		States<String> imageOneTwoA = new States<String>(imageOneA, imageTwoA);
		States<String> imageOneTwoB = new States<String>(imageOneB, imageTwoB);
		States<String> imageOneTwoC = new States<String>(imageOneC, imageTwoC);
		States<String> imageOneTwoD = new States<String>(imageOneD, imageTwoD);
		
		States<String> imageOneThreeA = new States<String>(imageOneA, imageThreeA);
		States<String> imageOneThreeB = new States<String>(imageOneB, imageThreeB);
		States<String> imageOneThreeC = new States<String>(imageOneC, imageThreeC);
		States<String> imageOneThreeD = new States<String>(imageOneD, imageThreeD);
		
		States<String> imageOneFourA = new States<String>(imageOneA, imageFourA);
		States<String> imageOneFourB = new States<String>(imageOneB, imageFourB);
		States<String> imageOneFourC = new States<String>(imageOneC, imageFourC);
		States<String> imageOneFourD = new States<String>(imageOneD, imageFourD);
		
		States<String> imageTwoThreeA = new States<String>(imageTwoA, imageThreeA);
		States<String> imageTwoThreeB = new States<String>(imageTwoB, imageThreeB);
		States<String> imageTwoThreeC = new States<String>(imageTwoC, imageThreeC);
		States<String> imageTwoThreeD = new States<String>(imageTwoD, imageThreeD);
		
		States<String> imageTwoFourA = new States<String>(imageTwoA, imageFourA);
		States<String> imageTwoFourB = new States<String>(imageTwoB, imageFourB);
		States<String> imageTwoFourC = new States<String>(imageTwoC, imageFourC);
		States<String> imageTwoFourD = new States<String>(imageTwoD, imageFourD);
		
		States<String> imageThreeFourA = new States<String>(imageThreeA, imageFourA);
		States<String> imageThreeFourB = new States<String>(imageThreeB, imageFourB);
		States<String> imageThreeFourC = new States<String>(imageThreeC, imageFourC);
		States<String> imageThreeFourD = new States<String>(imageThreeD, imageFourD);		
		
		assertTrue("Uncorrect transition-graph state images computation", 
				graph.getImage(stateOne, 'a').equals(imageOneA)
				&& graph.getImage(stateOne, 'b').equals(imageOneB)
				&& graph.getImage(stateOne, 'c').equals(imageOneC)
				&& graph.getImage(stateOne, 'd').equals(imageOneD)
				&& graph.getImage(stateTwo, 'a').equals(imageTwoA)
				&& graph.getImage(stateTwo, 'b').equals(imageTwoB)
				&& graph.getImage(stateTwo, 'c').equals(imageTwoC)
				&& graph.getImage(stateTwo, 'd').equals(imageTwoD)
				&& graph.getImage(stateThree, 'a').equals(imageThreeA)
				&& graph.getImage(stateThree, 'b').equals(imageThreeB)
				&& graph.getImage(stateThree, 'c').equals(imageThreeC)
				&& graph.getImage(stateThree, 'd').equals(imageThreeD)
				&& graph.getImage(stateFour, 'a').equals(imageFourA)
				&& graph.getImage(stateFour, 'b').equals(imageFourB)
				&& graph.getImage(stateFour, 'c').equals(imageFourC)
				&& graph.getImage(stateFour, 'd').equals(imageFourD));
		
		assertTrue("Uncorrect transition-graph states images computation",
				graph.getImage(new States<String>(stateOne, stateTwo), 'a').equals(imageOneTwoA)
				&& graph.getImage(new States<String>(stateOne, stateTwo), 'b').equals(imageOneTwoB)
				&& graph.getImage(new States<String>(stateOne, stateTwo), 'c').equals(imageOneTwoC)
				&& graph.getImage(new States<String>(stateOne, stateTwo), 'd').equals(imageOneTwoD)
				&& graph.getImage(new States<String>(stateOne, stateThree), 'a').equals(imageOneThreeA)
				&& graph.getImage(new States<String>(stateOne, stateThree), 'b').equals(imageOneThreeB)
				&& graph.getImage(new States<String>(stateOne, stateThree), 'c').equals(imageOneThreeC)
				&& graph.getImage(new States<String>(stateOne, stateThree), 'd').equals(imageOneThreeD)
				&& graph.getImage(new States<String>(stateOne, stateFour), 'a').equals(imageOneFourA)
				&& graph.getImage(new States<String>(stateOne, stateFour), 'b').equals(imageOneFourB)
				&& graph.getImage(new States<String>(stateOne, stateFour), 'c').equals(imageOneFourC)
				&& graph.getImage(new States<String>(stateOne, stateFour), 'd').equals(imageOneFourD)
				&& graph.getImage(new States<String>(stateTwo, stateThree), 'a').equals(imageTwoThreeA)
				&& graph.getImage(new States<String>(stateTwo, stateThree), 'b').equals(imageTwoThreeB)
				&& graph.getImage(new States<String>(stateTwo, stateThree), 'c').equals(imageTwoThreeC)
				&& graph.getImage(new States<String>(stateTwo, stateThree), 'd').equals(imageTwoThreeD)
				&& graph.getImage(new States<String>(stateTwo, stateFour), 'a').equals(imageTwoFourA)
				&& graph.getImage(new States<String>(stateTwo, stateFour), 'b').equals(imageTwoFourB)
				&& graph.getImage(new States<String>(stateTwo, stateFour), 'c').equals(imageTwoFourC)
				&& graph.getImage(new States<String>(stateTwo, stateFour), 'd').equals(imageTwoFourD)
				&& graph.getImage(new States<String>(stateThree, stateFour), 'a').equals(imageThreeFourA)
				&& graph.getImage(new States<String>(stateThree, stateFour), 'b').equals(imageThreeFourB)
				&& graph.getImage(new States<String>(stateThree, stateFour), 'c').equals(imageThreeFourC)
				&& graph.getImage(new States<String>(stateThree, stateFour), 'd').equals(imageThreeFourD));
	}*/
	
	@Test public void accept() {
		TransitionGraph<String> graph = this.createTransitionGraph();
		
		String wordToAccept[] = {"", "b", "d", "bdbbdd", "a", "ab", "ad", "adba", "abdbbddba", "abadacc", "bdbbdda", "bdbbddababadadabada", "cc", "cccc", "ccccbadabddcc"};
		String wordToNotAccept[] = {"abcd", "ababac", "adadac", "cababab", "aaabbcab"};
		
		for (String word : wordToAccept)
			assertTrue("Uncorrect acceptance (should be accepted: " + word + ")", 
					graph.isAccepted(word));
		
		for (String word : wordToNotAccept)
			assertFalse("Uncorrect acceptance (should not be accepted: " + word + ")", 
					graph.isAccepted(word));
	}
	
	@Test public void acceptWithPowerset() {
		TransitionGraph<String> graph = this.createTransitionGraph();
		
		String wordToAccept[] = {"", "b", "d", "bdbbdd", "a", "ab", "ad", "adba", "abdbbddba", "abadacc", "bdbbdda", "bdbbddababadadabada", "cc", "cccc", "ccccbadabddcc"};
		String wordToNotAccept[] = {"abcd", "ababac", "adadac", "cababab", "aaabbcab"};
		
		for (String word : wordToAccept) {
			boolean acceptedByTransitionGraph = graph.isAccepted(word);
			boolean acceptedByFiniteAutomaton = graph.powersetConstruction().isAccepted(word);
			assertTrue("Uncorrect acceptance with Powerset Construction (should be accepted: " + word + "); transition-graph:" + acceptedByTransitionGraph + ", finite-automaton:" + acceptedByFiniteAutomaton, 
					acceptedByTransitionGraph && acceptedByFiniteAutomaton);
		}			
		
		for (String word : wordToNotAccept) {
			boolean acceptedByTransitionGraph = graph.isAccepted(word);
			boolean acceptedByFiniteAutomaton = graph.powersetConstruction().isAccepted(word);
			assertFalse("Uncorrect acceptance with Powerset Construction (should not be accepted: " + word + "); transition-graph:" + acceptedByTransitionGraph + ", finite-automaton:" + acceptedByFiniteAutomaton, 
					acceptedByTransitionGraph || acceptedByFiniteAutomaton);
		}
	}
	
	@Test public void represent() {
		TransitionGraph<String> graph = this.createTransitionGraph();
		
		System.out.println(graph);
		System.out.println(graph.toExtendedFormattedAutomaton());
	}

}
