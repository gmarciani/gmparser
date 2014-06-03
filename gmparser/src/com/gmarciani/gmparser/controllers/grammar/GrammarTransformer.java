/*	The MIT License (MIT)
 *
 *	Copyright (c) 2014 Giacomo Marciani
 *	
 *	Permission is hereby granted, free of charge, to any person obtaining a copy
 *	of this software and associated documentation files (the "Software"), to deal
 *	in the Software without restriction, including without limitation the rights
 *	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *	copies of the Software, and to permit persons to whom the Software is
 *	furnished to do so, subject to the following conditions:
 *	
 *	The above copyright notice and this permission notice shall be included in all
 *	copies or substantial portions of the Software.
 *	
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *	SOFTWARE.
*/

package com.gmarciani.gmparser.controllers.grammar;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.GrammarBuilder;
import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;
import com.gmarciani.gmparser.models.grammar.alphabet.AlphabetType;
import com.gmarciani.gmparser.models.grammar.production.Production;
import com.gmarciani.gmparser.models.grammar.transformation.GrammarTransformation;

/**
 * <p>The grammar transformation controller.<p>
 * <p>Deep description<p>
 * 
 * @see com.gmarciani.gmparser.controllers.app.App
 * @see com.gmarciani.gmparser.controllers.grammar.GrammarAnalyzer
 * @see com.gmarciani.gmparser.controllers.grammar.WordParser
 * 
 * @author Giacomo Marciani
 * @version 1.0
 */
public class GrammarTransformer {
	
	private static GrammarTransformer instance;
	
	private GrammarTransformer() {}
	
	/**
	 * <p>Returns the grammar transformer controller {@code singleton} instance.<p>
	 * <p>Singleton descrition<p>
	 * 
	 * @return controller singleton instance.
	 */
	public synchronized static GrammarTransformer getInstance() {
		if (instance == null) {
			instance = new GrammarTransformer();
		}
		
		return instance;
	}
	
	/**
	 * <p>Executes the specified transformation to the specified grammar, represented as string.<p>
	 * <p>All available transformation<p>
	 * 
	 * @param grammar target grammar, represented as string.
	 * @param transformation target grammar transformation. 
	 * @return transformed grammar.
	 */
	@SuppressWarnings("static-access")
	public Grammar transform(String strGrammar, GrammarTransformation transformation) {
		Grammar grammar = GrammarBuilder.hasProductions(strGrammar)
				.withAxiom(Grammar.AXIOM)
				.withEmpty(Grammar.EMPTY)
				.create();
		
		this.transform(grammar, transformation);
		
		return grammar;
	}

	/**
	 * <p>Executes the specified transformation to the specified grammar.<p>
	 * <p>All available transformation<p>
	 * 
	 * @param grammar target grammar.
	 * @param transformation target grammar transformation. 
	 * @return transformed grammar.
	 */
	public void transform(Grammar grammar, GrammarTransformation transformation) {
		if (transformation == GrammarTransformation.RGS) {
			this.removeUngenerativeSymbols(grammar);
		} else if (transformation == GrammarTransformation.RRS) {
			this.removeUnreacheableSymbols(grammar);
		} else if (transformation == GrammarTransformation.RUS) {
			this.removeUselessSymbols(grammar);
		} else if (transformation == GrammarTransformation.REP) {
			this.removeEpsilonProductions(grammar);
		} else if (transformation == GrammarTransformation.RUP) {
			this.removeUnitProductions(grammar);
		} else {
			System.out.println("Unavailable grammar transformation");
		}		
	}
	
	/**
	 * <p>Removes ungenerative symbols from the specified {@code grammar}.<p>
	 * <p>An ungenerative symbol is a non terminal symbol that does not generate any terminal symbol.<p>
	 * 
	 * @param grammar the target grammar.
	 */
	public void removeUngenerativeSymbols(Grammar grammar) {	
		Alphabet generativeNonTerminals = new Alphabet(AlphabetType.NON_TERMINAL);
		Alphabet generativeAlphabet = new Alphabet(grammar.getTerminals(), generativeNonTerminals);
		
		boolean loop = true;
		while(loop) {
			loop = false;		
			
			generativeAlphabet.addAll(generativeNonTerminals);
			
			for (Production production : grammar.getProductions()) {
				if (production.isRightWithin(generativeAlphabet))
					loop = generativeNonTerminals.addAll(production.getLeft().getNonTerminalAlphabet()) ? true : loop;	
			}			
		}		
		
		grammar.retainAllProductionsWithin(generativeAlphabet);
	}
		
	/**
	 * <p>Removes unreacheables symbols from the specified {@code grammar}.<p>
	 * <p>An unreacheable symbol is a non terminal symbol that is not reacheable from the axiom.<p>
	 * 
	 * @param grammar the target grammar.
	 */
	public void removeUnreacheableSymbols(Grammar grammar) {		
		Alphabet reacheableTerminals = new Alphabet(AlphabetType.TERMINAL);
		Alphabet reacheableNonTerminals = new Alphabet(AlphabetType.NON_TERMINAL);	
		reacheableNonTerminals.add(grammar.getAxiom());
		
		boolean loop = true;
		while(loop) {
			loop = false;
			
			for (Production production : grammar.getProductions()) {	
				if (production.isLeftWithin(reacheableNonTerminals))
					loop = reacheableNonTerminals.addAll(production.getRight().getNonTerminalAlphabet()) ? true : loop;
				
				if (production.isLeftWithin(reacheableNonTerminals))
					reacheableTerminals.addAll(production.getRight().getTerminalAlphabet());
			}
		}		
		
		Alphabet acceptedAlphabet = new Alphabet();
		acceptedAlphabet.addAll(reacheableTerminals);
		acceptedAlphabet.addAll(reacheableNonTerminals);
		
		grammar.retainAllProductionsWithin(acceptedAlphabet);
	}
	
	/**
	 * <p>Removes useless symbols from the specified {@code grammar}.<p>
	 * <p>A useless symbol is a non terminal symbol that is ungenerative and/or unreacheable.<p>
	 * 
	 * @param grammar the target grammar.
	 */
	public void removeUselessSymbols(Grammar grammar) {
		this.removeUngenerativeSymbols(grammar);
		this.removeUnreacheableSymbols(grammar);
	}
	
	public void removeEpsilonProductions(Grammar grammar) {
		Alphabet nullables = grammar.getNullables();	
		
		for (Production production : grammar.getProductions()) {
			Alphabet nullablesForProduction = grammar.getNullablesForProduction(production);
			if (!nullablesForProduction.isEmpty()) {
				Map<Character, List<Integer>> nullablesOccurencesMap = production.getRight().getSymbolsOccurrences(nullablesForProduction);
				for (Character nullable : nullablesForProduction) {
					List<Integer> nullableOccurrences = nullablesOccurencesMap.get(nullable);
					for (int nullableOccurrence : nullableOccurrences) {
						String lhs = production.getLeft().getValue();
						StringBuilder builder = new StringBuilder(production.getRight().getValue());
						builder.insert(nullableOccurrence, Grammar.EMPTY);
						String rhs = builder.toString();
						Production productionWithoutNullable = new Production(lhs, rhs);
						grammar.addProduction(productionWithoutNullable);
					}						
				}
				
				String lhs = production.getLeft().getValue();
				String rhs = production.getRight().getValue().replaceAll(nullables.getUnionRegex(), Grammar.EMPTY);
				Production productionWithoutNullables = new Production(lhs, rhs);
				grammar.addProduction(productionWithoutNullables);
			}
		}
		
		for (Production epsilonProduction : grammar.getEpsilonProductions())
			grammar.removeProduction(epsilonProduction);
		
		if (nullables.contains(grammar.getAxiom()))
			grammar.addProduction(grammar.getAxiom(), Grammar.EMPTY);
	}
	
	/**
	 * <p>Removes unit productions from the specified {@code grammar}.<p>
	 * <p>A unit production is ...<p>
	 * 
	 * @param grammar the target grammar.
	 */
	public void removeUnitProductions(Grammar grammar) {
		this.removeEpsilonProductions(grammar);
		
		for (Production trivialUnitProduction : grammar.getTrivialUnitProductions())
			grammar.removeProduction(trivialUnitProduction);
		
		Queue<Production> queue = new ConcurrentLinkedQueue<Production>(grammar.getNonTrivialUnitProductions());
		while(!queue.isEmpty()) {
			Production nonTrivialUnitProduction = queue.poll();
			String lhs = nonTrivialUnitProduction.getLeft().getValue();
			Character rhs = nonTrivialUnitProduction.getRight().getValue().charAt(0);
			
			Set<String> unfoldings = grammar.getRightForNonTerminal(rhs);
			for (String unfolding : unfoldings)
				grammar.addProduction(lhs, unfolding);
			
			grammar.removeProduction(nonTrivialUnitProduction);
			
			for (Production trivialUnitProduction : grammar.getTrivialUnitProductions())
				grammar.removeProduction(trivialUnitProduction);
			
			queue.addAll(grammar.getNonTrivialUnitProductions());
		}
	}	
	
	/**
	 * <p>Converts to Chomsky Normal Form the specified {@code grammar}.<p>
	 * <p>The Chomsky Normal Form is ...<p>
	 * 
	 * @param grammar the target grammar.
	 */
	public void toChomskyNormalForm(Grammar grammar) {
		this.removeEpsilonProductions(grammar);
		this.removeUnitProductions(grammar);
		
		boolean emptyGeneration = grammar.getNullables().contains(grammar.getAxiom());
		
		boolean loop = true;
		while(loop) {
			loop = false;
			
			for (Production production : grammar.getProductions()) {
				if (production.getRight().getSize() > 2) {
					loop = true;
					Character newNonTerminal = grammar.getNewNonTerminal();
					Production reducedProductionOne = new Production();
					reducedProductionOne.setLeft(production.getLeft());
					reducedProductionOne.setRight(production.getRight().getValue().substring(0, 1) + newNonTerminal);
					
					Production reducedProductionTwo = new Production();
					reducedProductionTwo.setLeft(newNonTerminal);
					reducedProductionTwo.setRight(production.getRight().getValue().substring(1));
					
					grammar.removeProduction(production);
					grammar.addProduction(reducedProductionOne);
					grammar.addProduction(reducedProductionTwo);
				}
			}
		}
		
		loop = true;
		while(loop) {
			loop = false;
			
			for (Production production : grammar.getProductions()) {
				if (production.getRight().getSize() > 1
						&& !production.getRight().getTerminalAlphabet().isEmpty()) {
					
					loop = true;
					Character newNonTerminal = grammar.getNewNonTerminal();
					Character terminal = production.getRight().getTerminalAlphabet().first();
					
					Production promotionProductionOne = new Production();
					promotionProductionOne.setLeft(production.getLeft());
					promotionProductionOne.setRight(production.getRight().getValue().replace(terminal, newNonTerminal));
					
					Production promotionProductionTwo = new Production();
					promotionProductionTwo.setLeft(newNonTerminal);
					promotionProductionTwo.setRight(terminal);
					
					grammar.removeProduction(production);
					grammar.addProduction(promotionProductionOne);
					grammar.addProduction(promotionProductionTwo);
				}
			}
		}
		
		if (emptyGeneration)
			grammar.addProduction(grammar.getAxiom(), grammar.getEmpty());		
	}
	
}
