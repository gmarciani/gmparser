package com.gmarciani.gmparser;

import static org.junit.Assert.*;

import org.junit.Test;

import com.gmarciani.gmparser.controllers.grammar.GrammarTransformer;
import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.GrammarBuilder;

public class TestGrammarTransformer {

	@SuppressWarnings("static-access")
	@Test
	public void testRemoveUngenerativeSymbols() {
		Grammar grammar = GrammarBuilder.hasProductionsAsString("S->XY|a;X->a", "->", "|", ";").create();
		
		GrammarTransformer.removeUngenerativeSymbols(grammar);
		
		Grammar shouldBe = GrammarBuilder.hasProductionsAsString("S->XY", "->", "|", ";").create();
		
		assertTrue("", 
				grammar.getProductions().equals(shouldBe.getProductions()));
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void removeUnreacheableSymbols() {
		Grammar grammar = GrammarBuilder.hasProductionsAsString("S->XY|a;X->a", "->", "|", ";").create();
		
		GrammarTransformer.removeUngenerativeSymbols(grammar);
		
		Grammar shouldBe = GrammarBuilder.hasProductionsAsString("S->a", "->", "|", ";").create();
		
		assertTrue("", 
				grammar.getProductions().equals(shouldBe.getProductions()));
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testRemoveEpsilonProductions() {
		Grammar grammar = GrammarBuilder.hasProductionsAsString("S->A;A" + Grammar.EMPTY_STRING, "->", "|", ";").create();
		
		GrammarTransformer.removeEpsilonProductions(grammar);
		
		Grammar shouldBe = GrammarBuilder.hasProductionsAsString("S->" + Grammar.EMPTY_STRING, "->", "|", ";").create();
		
		assertTrue("", 
				grammar.getProductions().equals(shouldBe.getProductions()));
	}

	@SuppressWarnings("static-access")
	@Test
	public void testRemoveUnitProductions() {
		Grammar grammar = GrammarBuilder.hasProductionsAsString("S->AS|A;A->B|a;B->A|S|b", "->", "|", ";").create();
		
		GrammarTransformer.removeUnitProductions(grammar);
		
		Grammar shouldBe = GrammarBuilder.hasProductionsAsString("S->AS|a|b;A->AS|a|b", "->", "|", ";").create();
		
		assertTrue("", 
				grammar.getProductions().equals(shouldBe.getProductions()));
	}	
	
	@SuppressWarnings("static-access")
	@Test
	public void testRemoveLeftRecursion() {
		Grammar grammar = GrammarBuilder.hasProductionsAsString("S->SA|a;A->a", "->", "|", ";").create();
		
		GrammarTransformer.removeUnitProductions(grammar);
		
		Grammar shouldBe = GrammarBuilder.hasProductionsAsString("S->aZ|a;Z->AZ|a;A->a", "->", "|", ";").create();
		
		assertTrue("", 
				grammar.getProductions().equals(shouldBe.getProductions()));
	}

}
