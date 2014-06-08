package com.gmarciani.gmparser.automaton.base;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.gmarciani.gmparser.models.automaton.state.State;
import com.gmarciani.gmparser.models.automaton.state.States;

public class TestStates {

	@Test public void add() {
		State stateOne = new State(1);
		State stateTwo = new State(2);
		State stateOneTwo = new State(1, 2);
		State stateTwoOne = new State(2, 1);
		State stateOneTwoThree = new State(1, 2, 3);
		State stateThreeOneTwo = new State(3, 1, 2);
		
		List<State> stateList = new ArrayList<State>();
		stateList.add(stateOne);
		stateList.add(stateTwo);
		stateList.add(stateOneTwo);
		stateList.add(stateTwoOne);
		stateList.add(stateOneTwoThree);
		stateList.add(stateThreeOneTwo);
		
		List<State> list = new ArrayList<State>();
		list.add(stateOne);
		list.add(stateTwo);
		list.add(stateOneTwo);
		list.add(stateOneTwoThree);
		
		States states = new States();
		
		for (State state : list)
			assertTrue("Uncorrect state insertion. Should be added: " + state + " in " + states, states.add(state));
		
		for (State state : list)
			assertFalse("Uncorrect state insertion. Should not be added: " + state + " in " + states, states.add(state));
	}

}
