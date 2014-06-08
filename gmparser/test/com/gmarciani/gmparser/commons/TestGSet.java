package com.gmarciani.gmparser.commons;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import com.gmarciani.gmparser.models.commons.set.GSet;

public class TestGSet {

	@Test public void create() {
		Character symbols[] = {'a', 'b', 'c'};
		Character pSymbolsOne[] = {'a'};
		Character pSymbolsTwo[] = {'b', 'c'};
		
		Set<Character> setOne = new GSet<Character>();
		setOne.add('a');
		setOne.add('b');
		setOne.add('c');		
		
		Set<Character> setTwo = new GSet<Character>('a');
		setTwo.add('b');
		setTwo.add('c');
		
		Set<Character> setThree = new GSet<Character>('a', 'b', 'c');
		
		Set<Character> setFour = new GSet<Character>(symbols);		
		
		Set<Character> setFive = new GSet<Character>(pSymbolsOne, pSymbolsTwo);
		
		Set<Character> setSix = new GSet<Character>(setOne, setTwo, setThree);
		
		Set<Character> setSeven = new GSet<Character>(setOne);
		
		assertTrue("Uncorrect AdvancedSet creation" , 
				setOne.equals(setTwo)
				&& setTwo.equals(setThree)
				&& setThree.equals(setFour)
				&& setFour.equals(setFive)
				&& setFive.equals(setSix)
				&& setSix.equals(setSeven));
	}

}
