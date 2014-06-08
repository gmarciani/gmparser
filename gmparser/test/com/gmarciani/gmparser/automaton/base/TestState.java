package com.gmarciani.gmparser.automaton.base;

import static org.junit.Assert.*;

import org.junit.Test;

import com.gmarciani.gmparser.models.automaton.state.State;

public class TestState {

	@Test public void equal() {
		State stateOne = new State(1);
		State stateTwo = new State(2);
		State stateOneTwo = new State(1, 2);
		State stateTwoOne = new State(2, 1);
		State stateOneTwoThree = new State(1, 2, 3);
		State stateThreeOneTwo = new State(3, 1, 2);		
		State stateOneTwoThreeFour = new State(1, 2, 3, 4);
		
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

}
