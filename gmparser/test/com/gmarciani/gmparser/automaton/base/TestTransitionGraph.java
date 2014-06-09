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
	
	private TransitionGraph createTransitionGraph() {
		State stateOne = new State(1);
		State stateTwo = new State(2);
		State stateThree = new State(3);
		State stateFour = new State(4);
		
		TransitionGraph graph = new TransitionGraph(stateOne);		
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
		TransitionGraph graph = this.createTransitionGraph();
		
		State stateOne = graph.getStates().getState(1);
		State stateTwo = graph.getStates().getState(2);
		State stateThree = graph.getStates().getState(3);
		State stateFour = graph.getStates().getState(4);
		
		States expectedStates = new States(stateOne, stateTwo, stateThree, stateFour);
		State expectedInitialState = stateOne;
		States expectedFinalStates = new States(stateFour);
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
	
	@Test public void computeImages() {
		TransitionGraph graph = this.createTransitionGraph();
		
		State stateOne = graph.getStates().getState(1);
		State stateTwo = graph.getStates().getState(2);
		State stateThree = graph.getStates().getState(3);
		State stateFour = graph.getStates().getState(4);
		
		States imageOneA = new States(stateTwo, stateFour);
		States imageOneB = new States(stateOne, stateTwo, stateFour);
		States imageOneC = new States(stateThree);
		States imageOneD = new States(stateOne, stateTwo, stateFour);
		
		States imageTwoA = new States();
		States imageTwoB = new States(stateOne, stateTwo, stateFour);
		States imageTwoC = new States(stateThree);
		States imageTwoD = new States(stateOne, stateTwo, stateFour);
		
		States imageThreeA = new States();
		States imageThreeB = new States();
		States imageThreeC = new States(stateTwo, stateFour);
		States imageThreeD = new States();
		
		States imageFourA = new States();
		States imageFourB = new States(stateOne, stateTwo, stateFour);
		States imageFourC = new States(stateThree);
		States imageFourD = new States(stateOne, stateTwo, stateFour);
		
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
				graph.getImage(new States(stateOne, stateTwo), 'a').equals(imageOneTwoA)
				&& graph.getImage(new States(stateOne, stateTwo), 'b').equals(imageOneTwoB)
				&& graph.getImage(new States(stateOne, stateTwo), 'c').equals(imageOneTwoC)
				&& graph.getImage(new States(stateOne, stateTwo), 'd').equals(imageOneTwoD)
				&& graph.getImage(new States(stateOne, stateThree), 'a').equals(imageOneThreeA)
				&& graph.getImage(new States(stateOne, stateThree), 'b').equals(imageOneThreeB)
				&& graph.getImage(new States(stateOne, stateThree), 'c').equals(imageOneThreeC)
				&& graph.getImage(new States(stateOne, stateThree), 'd').equals(imageOneThreeD)
				&& graph.getImage(new States(stateOne, stateFour), 'a').equals(imageOneFourA)
				&& graph.getImage(new States(stateOne, stateFour), 'b').equals(imageOneFourB)
				&& graph.getImage(new States(stateOne, stateFour), 'c').equals(imageOneFourC)
				&& graph.getImage(new States(stateOne, stateFour), 'd').equals(imageOneFourD)
				&& graph.getImage(new States(stateTwo, stateThree), 'a').equals(imageTwoThreeA)
				&& graph.getImage(new States(stateTwo, stateThree), 'b').equals(imageTwoThreeB)
				&& graph.getImage(new States(stateTwo, stateThree), 'c').equals(imageTwoThreeC)
				&& graph.getImage(new States(stateTwo, stateThree), 'd').equals(imageTwoThreeD)
				&& graph.getImage(new States(stateTwo, stateFour), 'a').equals(imageTwoFourA)
				&& graph.getImage(new States(stateTwo, stateFour), 'b').equals(imageTwoFourB)
				&& graph.getImage(new States(stateTwo, stateFour), 'c').equals(imageTwoFourC)
				&& graph.getImage(new States(stateTwo, stateFour), 'd').equals(imageTwoFourD)
				&& graph.getImage(new States(stateThree, stateFour), 'a').equals(imageThreeFourA)
				&& graph.getImage(new States(stateThree, stateFour), 'b').equals(imageThreeFourB)
				&& graph.getImage(new States(stateThree, stateFour), 'c').equals(imageThreeFourC)
				&& graph.getImage(new States(stateThree, stateFour), 'd').equals(imageThreeFourD));
	}
	
	@Test public void accept() {
		TransitionGraph graph = this.createTransitionGraph();
		
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
		TransitionGraph graph = this.createTransitionGraph();
		
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
		TransitionGraph graph = this.createTransitionGraph();
		
		System.out.println(graph);
		System.out.println(graph.toFormattedAutomaton());
	}

}
