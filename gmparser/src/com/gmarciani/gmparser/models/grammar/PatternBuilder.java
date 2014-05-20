package com.gmarciani.gmparser.models.grammar;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class PatternBuilder {
	
	private static PatternBuilder instance = new PatternBuilder();
	
	private static String string;
	private static Map<Character, Set<Character>> map;	

	public PatternBuilder() {
		string = "";
		map = new HashMap<Character, Set<Character>>();
	}
	
	private static void reset() {
		string = "";
		map.clear();
	}
	
	public static PatternBuilder hasPattern(String pattern) {
		string = pattern;
		
		return instance;
	}
	
	public static PatternBuilder withItem(Character patternItem, Set<Character> alphabet) {
		map.put(patternItem, alphabet);
		
		return instance;
	}
	
	public static Pattern create() {
		String reg = "";
		
		for (Character symbol : string.toCharArray()) {
			if (map.containsKey(symbol)) {
				String expression = "[";
				Iterator<Character> iter = map.get(symbol).iterator();
				while (iter.hasNext()) {
					expression += iter.next();
				}
				expression += "]";
				reg += expression;
			} else {
				reg += symbol;
			}
		}
		
		reset();
		
		Pattern pattern = Pattern.compile(reg);
		return pattern;
	}

}
