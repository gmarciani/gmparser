package com.gmarciani.gmparser.automaton.base;

import static org.junit.Assert.*;

import org.junit.Test;

import com.gmarciani.gmparser.models.automaton.state.State;
import com.gmarciani.gmparser.models.automaton.state.StateId;
import com.gmarciani.gmparser.models.commons.set.GSet;

public class TestState {

	@Test public void equal() {
		State<Object> stateOne = new State<Object>(new StateId(1));
		State<Object> stateTwo = new State<Object>(new StateId(2));
		State<Object> stateOneTwo = new State<Object>(new StateId(1, 2));
		State<Object> stateTwoOne = new State<Object>(new StateId(2, 1));
		State<Object> stateOneTwoThree = new State<Object>(new StateId(1, 2, 3));
		State<Object> stateThreeOneTwo = new State<Object>(new StateId(3, 1, 2));		
		State<Object> stateOneTwoThreeFour = new State<Object>(new StateId(1, 2, 3, 4));
		
		assertEquals("Uncorrect state equality. Should be equals: " + stateOne + " and " + stateOne, 
				stateOne, stateOne);
		assertEquals("Uncorrect state equality. Should be equals: " + stateOneTwo + " and " + stateOneTwo, 
				stateOneTwo, stateOneTwo);
		assertEquals("Uncorrect state equality. Should be equals: " + stateOneTwo + " and " + stateTwoOne, 
				stateOneTwo, stateTwoOne);
		assertEquals("Uncorrect state equality. Should be equals: " + stateOneTwoThree + " and " + stateThreeOneTwo, 
				stateOneTwoThree, stateThreeOneTwo);
		assertNotEquals("Uncorrect state equality. Should not be equals: " + stateOne + " and " + stateTwo, 
				stateOne, stateTwo);
		assertNotEquals("Uncorrect state equality. Should not be equals: " + stateOneTwoThree + " and " + stateOneTwo, 
				stateOneTwoThree, stateOneTwo);
		assertNotEquals("Uncorrect state equality. Should not be equals: " + stateOneTwoThree + " and " + stateOneTwoThreeFour, 
				stateOneTwoThree, stateOneTwoThreeFour);
	}
	
	@Test public void represent() {
		State<String> stateOne = new State<String>(new StateId(1), "one");
		State<String> stateTwo = new State<String>(new StateId(2), "two");
		State<String> stateOneTwo = new State<String>(new StateId(1, 2), "one-two");
		State<String> stateTwoOne = new State<String>(new StateId(2, 1), "two-one");
		
		GSet<State<String>> states = new GSet<State<String>>();
		states.add(stateOne);
		states.add(stateTwo);
		states.add(stateOneTwo);
		states.add(stateTwoOne);
		
		for (State<String> state : states)
			System.out.println(state);
		
		for (State<String> state : states)
			System.out.println(state.toExtendedString());
	}

}
