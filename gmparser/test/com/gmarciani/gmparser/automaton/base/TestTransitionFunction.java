package com.gmarciani.gmparser.automaton.base;

import org.junit.Test;

import com.gmarciani.gmparser.models.automaton.States;
import com.gmarciani.gmparser.models.automaton.TransitionFunction;
import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;

public class TestTransitionFunction {

	@Test
	public void test() {
		Alphabet alphabet = new Alphabet();
		alphabet.addAll("abcd");
		
		States states = new States();
		states.addAll(1,2,3,4,5);
		
		TransitionFunction function = new TransitionFunction(states, alphabet);
		
		//System.out.println(function);		
	}

}
