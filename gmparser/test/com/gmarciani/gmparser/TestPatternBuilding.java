package com.gmarciani.gmparser;

import static org.junit.Assert.*;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.junit.Test;

import com.gmarciani.gmparser.models.grammar.PatternBuilder;

public class TestPatternBuilding {

	@SuppressWarnings("static-access")
	@Test
	public void testCreatePattern() {
		Set<Character> charsetA = new LinkedHashSet<Character>();
		Set<Character> charsetB = new LinkedHashSet<Character>();
		Set<Character> charsetC = new LinkedHashSet<Character>();
		
		charsetA.add('a');
		charsetA.add('b');
		charsetA.add('c');
		
		charsetB.add('d');
		charsetB.add('e');
		charsetB.add('f');
		
		charsetC.add('g');
		charsetC.add('h');
		charsetC.add('i');
		
		String patternIn = "1A*2B*3C*";
		
		Pattern pattern = PatternBuilder.hasPattern(patternIn)
				.withItem('A', charsetA)
				.withItem('B', charsetB)
				.withItem('C', charsetC)
				.create();
		
		String regShouldBe = "1[abc]*2[def]*3[ghi]*";
		Pattern patternShouldBe = Pattern.compile(regShouldBe);
		
		assertTrue("Incorrect pattern building",
				pattern.toString().equals(patternShouldBe.toString()));		
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testMatchPattern() {
		Set<Character> charsetA = new LinkedHashSet<Character>();
		Set<Character> charsetB = new LinkedHashSet<Character>();
		Set<Character> charsetC = new LinkedHashSet<Character>();
		
		charsetA.add('a');
		charsetA.add('b');
		charsetA.add('c');
		
		charsetB.add('d');
		charsetB.add('e');
		charsetB.add('f');
		
		charsetC.add('g');
		charsetC.add('h');
		charsetC.add('i');
		
		String patternIn = "1A*2B*3C*";
		
		Pattern pattern = PatternBuilder.hasPattern(patternIn)
				.withItem('A', charsetA)
				.withItem('B', charsetB)
				.withItem('C', charsetC)
				.create();
		
		String stringToMatchTrue = "1cabab2defdef3";
		String stringToMatchFalse = "1cab2cab3";
		
		assertTrue("Incorrect pattern matching",
				stringToMatchTrue.matches(pattern.pattern()) 
				&& !stringToMatchFalse.matches(pattern.pattern()));	
		
	}

}
