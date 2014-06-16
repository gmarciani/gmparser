package com.gmarciani.gmparser.automaton.transformation;

import static org.junit.Assert.*;

import org.junit.Test;

import com.gmarciani.gmparser.models.automaton.finite.FiniteAutomaton;
import com.gmarciani.gmparser.models.automaton.graph.TransitionGraph;
import com.gmarciani.gmparser.models.automaton.state.State;
import com.gmarciani.gmparser.models.grammar.Grammar;

public class TestPowersetConstruction {
	
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
	
	private FiniteAutomaton<String> createExpectedAutomaton() {
		State<String> stateOneTwoFour = new State<String>(0, "one-two-four");
		State<String> stateTwoFour = new State<String>(1, "two-four");
		State<String> stateThree = new State<String>(2, "three");
		
		FiniteAutomaton<String> automaton = new FiniteAutomaton<String>(stateOneTwoFour);
		automaton.addAsFinalState(stateOneTwoFour);
		automaton.addAsFinalState(stateTwoFour);
		automaton.addState(stateThree);
		
		automaton.addSymbol('a');
		automaton.addSymbol('b');
		automaton.addSymbol('c');
		automaton.addSymbol('d');
		
		automaton.addTransition(stateOneTwoFour, stateTwoFour, 'a');
		automaton.addTransition(stateOneTwoFour, stateOneTwoFour, 'b');
		automaton.addTransition(stateOneTwoFour, stateThree, 'c');
		automaton.addTransition(stateOneTwoFour, stateOneTwoFour, 'd');
		automaton.addTransition(stateTwoFour, stateOneTwoFour, 'b');
		automaton.addTransition(stateTwoFour, stateThree, 'c');
		automaton.addTransition(stateTwoFour, stateOneTwoFour, 'd');
		automaton.addTransition(stateThree, stateTwoFour, 'c');
		
		return automaton;
	}

	@Test public void powersetConstruction() {
		TransitionGraph<String> graph = this.createTransitionGraph();
		FiniteAutomaton<String> expectedAutomaton = this.createExpectedAutomaton();
		
		assertEquals("Uncorrect powerset-construction.", 
				expectedAutomaton, graph.powersetConstruction());
	}

}
