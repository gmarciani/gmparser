package com.gmarciani.gmparser.automaton.base;

import static org.junit.Assert.*;

import org.junit.Test;

import com.gmarciani.gmparser.models.automaton.FiniteStateAutomaton;
import com.gmarciani.gmparser.models.automaton.State;
import com.gmarciani.gmparser.models.automaton.TransitionFunction;

public class TestFiniteStateAutomaton {

	@Test
	public void create() {
		FiniteStateAutomaton automaton = new FiniteStateAutomaton(new State(1));
		TransitionFunction function = automaton.getTransitionFunction();
		
		System.out.println(function);
		System.out.println(automaton);
	}

}
