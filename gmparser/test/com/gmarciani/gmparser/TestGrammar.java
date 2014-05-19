package com.gmarciani.gmparser;


import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.junit.Test;

import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.GrammarBuilder;

public class TestGrammar {
	
	private static final int NON_TERMINALS = 10;
	private static final int TERMINALS = 10;
	private static final int PRODUCTIONS = 10;
	private static final int PRODUCTION_LENGHT = 3;
	private static final int EMPTY_LENGHT = 1;

	@Test
	public void testGrammarBuildings() {
		Map<String, Grammar> grammars = new HashMap<String, Grammar>();
		
		Set<Character> nonTerminals = getRandomNonTerminals(NON_TERMINALS);
		Set<Character> terminals = getRandomTerminals(TERMINALS);	
		
		Character axiom = getRandomCharacter(nonTerminals);
		String empty = getRandomString(terminals, EMPTY_LENGHT);
		
		Map<Character, Set<String>> productions = getRandomProductions(nonTerminals, terminals, PRODUCTIONS, PRODUCTION_LENGHT);	
		
		Grammar grammarOne = buildGrammarSingleProduction(productions, axiom, empty);
		grammars.put("Single Production", grammarOne);
		Grammar grammarTwo = buildGrammarSingleProductionString(productions, axiom, empty);
		grammars.put("Single Production String", grammarTwo);
		Grammar grammarThree = buildGrammarProductionsSameNonTerminalString(productions, axiom, empty);
		grammars.put("Productions String for single non terminal", grammarThree);
		Grammar grammarFour = buildGrammarProductionsDifferentNonTerminalsString(productions, axiom, empty);
		grammars.put("Productions String for different non terminals", grammarFour);
		
		for (Map.Entry<String, Grammar> entry : grammars.entrySet()) {
			String modality = entry.getKey();
			Grammar grammar = entry.getValue();
			
			for (Character nonTerminal : productions.keySet()) {
				for (String sentential : productions.get(nonTerminal)) {
					String production = nonTerminal + "->" + sentential;
					assertTrue("Grammar built with " + modality + " doesn't contain the production: " + production, grammar.getProductionsForNonTerminalSymbol(nonTerminal).contains(sentential));
				}			
			}
		}		
		
		System.out.println("#INPUT");
		System.out.println("- NON TERMINAL ALPHABET: " + nonTerminals);
		System.out.println("- TERMINAL ALPHABET: " + terminals);
		System.out.println("- AXIOM: " + axiom);
		System.out.println("- EMPTY: " + empty);
		System.out.println("- PRODUCTIONS: " + productions);
		
		System.out.println("\n");		

		for (Map.Entry<String, Grammar> entry : grammars.entrySet()) {
			String modality = entry.getKey();
			Grammar grammar = entry.getValue();
			
			System.out.println("#OUTPUT " + modality);
			System.out.println(grammar);
			System.out.println("\n");
		}
	}	
	
	@SuppressWarnings("static-access")
	private Grammar buildGrammarSingleProduction(Map<Character, Set<String>> productions, Character axiom, String empty) {
		GrammarBuilder builder = GrammarBuilder.withAxiom(axiom).withEmpty(empty);
		
		for (Map.Entry<Character, Set<String>> production : productions.entrySet()) {
			Character nonTerminal = production.getKey();
			
			for (String sentential : production.getValue()) {
				builder.hasProduction(nonTerminal, sentential);
			}
		}
		
		Grammar grammar = builder.create();
		
		return grammar;
	}
	
	@SuppressWarnings("static-access")
	private Grammar buildGrammarSingleProductionString(Map<Character, Set<String>> productions, Character axiom, String empty) {
		GrammarBuilder builder = GrammarBuilder.withAxiom(axiom).withEmpty(empty);
		
		for (Character nonTerminal : productions.keySet()) {
			for (String sentential : productions.get(nonTerminal)) {
				String production = nonTerminal + "->" + sentential;
				builder.hasProduction(production, "->");
			}			
		}
		
		Grammar grammar = builder.create();
		
		return grammar;
	}
	
	@SuppressWarnings("static-access")
	private Grammar buildGrammarProductionsSameNonTerminalString(Map<Character, Set<String>> productions, Character axiom, String empty) {
		GrammarBuilder builder = GrammarBuilder.withAxiom(axiom).withEmpty(empty);
		
		for (Character nonTerminal : productions.keySet()) {
			String production = nonTerminal + "->";
			for (String sentential : productions.get(nonTerminal)) {
				production += sentential + "|";
			}			
			production = production.substring(0, production.length() - 1);
			builder.hasProductions(production, "->", "\\|");
		}
		
		Grammar grammar = builder.create();
		
		return grammar;
	}
	
	@SuppressWarnings("static-access")
	private Grammar buildGrammarProductionsDifferentNonTerminalsString(Map<Character, Set<String>> productions, Character axiom, String empty) {
		GrammarBuilder builder = GrammarBuilder.withAxiom(axiom).withEmpty(empty);
		
		String production = "";
		for (Character nonTerminal : productions.keySet()) {
			production += nonTerminal + "->";
			for (String sentential : productions.get(nonTerminal)) {
				production += sentential + "|";
			}			
			production = production.substring(0, production.length() - 1);
			production += ";";
		}
		production = production.substring(0, production.length() -1);
		builder.hasProductions(production, "->", "\\|", ";");
		
		Grammar grammar = builder.create();
		
		return grammar;
	}

	private Map<Character, Set<String>> getRandomProductions(Set<Character> nonTerminals, Set<Character> terminals, int n, int lenght) {
		Map<Character, Set<String>> productions = new HashMap<Character, Set<String>>();
		
		Set<Character> alphabet = new LinkedHashSet<Character>();
		alphabet.addAll(nonTerminals);
		alphabet.addAll(terminals);
		
		for (int i = 1; i <= n; i++) {
			Character nonTerminal = getRandomCharacter(nonTerminals);
			
			if (!productions.containsKey(nonTerminal)) {
				productions.put(nonTerminal, new LinkedHashSet<String>());
			}
			
			String sentential = getRandomString(alphabet, lenght);
			
			productions.get(nonTerminal).add(sentential);
		}
		
		return productions;
	}

	private Set<Character> getRandomNonTerminals(int n) {
		Set<Character> nonTerminals = new LinkedHashSet<Character>();
		
		Random r = new Random();
		
		for (int i = 1; i <= n; i++) {
			Character c = (char)(r.nextInt(26) + 'A');
			nonTerminals.add(c);
		}
		
		return nonTerminals;
	}
	
	private Set<Character> getRandomTerminals(int n) {
		Set<Character> terminals = new LinkedHashSet<Character>();
		
		Random r = new Random();
		
		for (int i = 1; i <= n; i++) {
			Character c = (char)(r.nextInt(26) + 'a');
			terminals.add(c);
		}
		
		return terminals;
	}		
	
	private String getRandomString(Set<Character> alphabet, int length) {
		String s = "";
		
		for (int i = 1; i <= length; i++) {
			Character c = getRandomCharacter(alphabet);
			s += String.valueOf(c);
		}
		
		return s;
	}
	
	private Character getRandomCharacter(Set<Character> alphabet) {
		Random r = new Random();
		int item = r.nextInt(alphabet.size());
		Character c = (Character) alphabet.toArray()[item];
		
		return c;
	}	

}
