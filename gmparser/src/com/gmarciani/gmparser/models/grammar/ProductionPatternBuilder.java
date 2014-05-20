package com.gmarciani.gmparser.models.grammar;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class ProductionPatternBuilder {
	
	private static ProductionPatternBuilder instance = new ProductionPatternBuilder();
	
	private static String string;
	private static Map<Character, Set<Character>> map;	
	private static Productions productions;

	public ProductionPatternBuilder() {
		string = "";
		map = new HashMap<Character, Set<Character>>();
	}
	
	private static void reset() {
		string = "";
		map.clear();
	}
	
	//(A,Aa)
	public static ProductionPatternBuilder hasPattern(Production patternProduction) {
		productions.add(patternProduction);
		
		return instance;
	}
	
	//"A","Aa"
	public static ProductionPatternBuilder hasPattern(String leftPattern, String rightPattern) {
		Production patternProduction = new Production(leftPattern, rightPattern);
		productions.add(patternProduction);

		return instance;
	}	
	
	//A->Aa
	public static ProductionPatternBuilder hasPatternAsString(String pattern, String memberSeparator) {
		String patternAsArray[] = pattern.split(Pattern.quote(memberSeparator));
		String leftPattern = patternAsArray[0];
		String rightPattern = patternAsArray[1];
		hasPattern(leftPattern, rightPattern);
		
		return instance;
	}
	
	//A->Aa|Ba
	public static ProductionPatternBuilder hasPatternAsString(String pattern, String memberSeparator, String infixSeparator) {
		String patternAsArray[] = pattern.split(Pattern.quote(memberSeparator));		
		String leftPattern = patternAsArray[0];
		String rightsPattern[] = patternAsArray[1].split(Pattern.quote(infixSeparator));
		
		for (String rightPattern : rightsPattern) {
			hasPatternAsString(leftPattern + memberSeparator + rightPattern, memberSeparator);
		}		
		
		return instance;
	}
	
	//A->Aa|Ba;B->Bb|b
	public static ProductionPatternBuilder hasPatternAsString(String pattern, String memberSeparator, String infixSeparator, String productionSeparator) {
		String patternAsArray[] = pattern.split(Pattern.quote(productionSeparator));
		
		for (String patternSameNonTerminal : patternAsArray) {
			hasPatternAsString(patternSameNonTerminal, memberSeparator, infixSeparator);
		}
		
		return instance;
	}
	
	public static ProductionPatternBuilder withItem(Character patternItem, Set<Character> alphabet) {
		map.put(patternItem, alphabet);
		
		return instance;
	}
	
	public static ProductionPattern create() {
		ProductionPattern productionPattern = new ProductionPattern();
		
		reset();
		
		return productionPattern;
	}

}
