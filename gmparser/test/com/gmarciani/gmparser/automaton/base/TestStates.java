package com.gmarciani.gmparser.automaton.base;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.gmarciani.gmparser.models.automaton.state.State;
import com.gmarciani.gmparser.models.automaton.state.StateId;
import com.gmarciani.gmparser.models.automaton.state.States;

public class TestStates {

	@Test public void add() {
		State<String> stateOne = new State<String>(new StateId(1));
		State<String> stateTwo = new State<String>(new StateId(2));
		State<String> stateOneTwo = new State<String>(new StateId(1, 2));
		State<String> stateTwoOne = new State<String>(new StateId(2, 1));
		State<String> stateOneTwoThree = new State<String>(new StateId(1, 2, 3));
		State<String> stateThreeOneTwo = new State<String>(new StateId(3, 1, 2));
		
		List<State<String>> stateList = new ArrayList<State<String>>();
		stateList.add(stateOne);
		stateList.add(stateTwo);
		stateList.add(stateOneTwo);
		stateList.add(stateTwoOne);
		stateList.add(stateOneTwoThree);
		stateList.add(stateThreeOneTwo);
		
		List<State<String>> list = new ArrayList<State<String>>();
		list.add(stateOne);
		list.add(stateTwo);
		list.add(stateOneTwo);
		list.add(stateOneTwoThree);
		
		States<String> states = new States<String>();
		
		for (State<String> state : list)
			assertTrue("Uncorrect state insertion. Should be added: " + state + " in " + states, states.add(state));
		
		for (State<String> state : list)
			assertFalse("Uncorrect state insertion. Should not be added: " + state + " in " + states, states.add(state));
	}
	
	@Test public void represent() {
		State<String> stateOne = new State<String>(new StateId(1), "one");
		State<String> stateTwo = new State<String>(new StateId(2), "two");
		State<String> stateOneTwo = new State<String>(new StateId(1, 2), "one-two");
		State<String> stateTwoOne = new State<String>(new StateId(2, 1), "two-one");
		
		States<String> states = new States<String>();
		states.add(stateOne);
		states.add(stateTwo);
		states.add(stateOneTwo);
		states.add(stateTwoOne);
		
		States<String> emptyStates = new States<String>();
		
		System.out.println(states);		
		System.out.println(states.toExtendedString());
		System.out.println(emptyStates);		
		System.out.println(emptyStates.toExtendedString());
	}

}
