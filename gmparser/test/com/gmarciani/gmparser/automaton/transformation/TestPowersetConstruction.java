package com.gmarciani.gmparser.automaton.transformation;

import static org.junit.Assert.*;

import org.junit.Test;

import com.gmarciani.gmparser.models.automaton.finite.FiniteAutomaton;
import com.gmarciani.gmparser.models.automaton.graph.TransitionGraph;
import com.gmarciani.gmparser.models.automaton.state.State;
import com.gmarciani.gmparser.models.grammar.Grammar;

public class TestPowersetConstruction {
	
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
	
	private FiniteAutomaton createExpectedAutomaton() {
		State stateOneTwoFour = new State(1, 2, 4);
		State stateTwoFour = new State(2, 4);
		State stateThree = new State(3);
		
		FiniteAutomaton automaton = new FiniteAutomaton(stateOneTwoFour);
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
		TransitionGraph graph = this.createTransitionGraph();
		FiniteAutomaton expectedAutomaton = this.createExpectedAutomaton();
		
		System.out.println(graph.toFormattedAutomaton());
		System.out.println(graph.powersetConstruction().toFormattedAutomaton());
		
		assertEquals("Uncorrect powerset-construction", 
				expectedAutomaton, graph.powersetConstruction());
	}

}
