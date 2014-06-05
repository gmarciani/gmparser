package com.gmarciani.gmparser.commons;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import com.gmarciani.gmparser.models.commons.AdvancedSet;

public class TestAdvancedSet {

	@Test public void create() {
		Character symbols[] = {'a', 'b', 'c'};
		Character pSymbolsOne[] = {'a'};
		Character pSymbolsTwo[] = {'b', 'c'};
		
		Set<Character> setOne = new AdvancedSet<Character>();
		setOne.add('a');
		setOne.add('b');
		setOne.add('c');		
		
		Set<Character> setTwo = new AdvancedSet<Character>('a');
		setTwo.add('b');
		setTwo.add('c');
		
		Set<Character> setThree = new AdvancedSet<Character>('a', 'b', 'c');
		
		Set<Character> setFour = new AdvancedSet<Character>(symbols);		
		
		Set<Character> setFive = new AdvancedSet<Character>(pSymbolsOne, pSymbolsTwo);
		
		Set<Character> setSix = new AdvancedSet<Character>(setOne, setTwo, setThree);
		
		Set<Character> setSeven = new AdvancedSet<Character>(setOne);
		
		assertTrue("Uncorrect AdvancedSet creation" , 
				setOne.equals(setTwo)
				&& setTwo.equals(setThree)
				&& setThree.equals(setFour)
				&& setFour.equals(setFive)
				&& setFive.equals(setSix)
				&& setSix.equals(setSeven));
	}

}
