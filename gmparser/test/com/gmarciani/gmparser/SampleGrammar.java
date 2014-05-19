package com.gmarciani.gmparser;


import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.GrammarBuilder;

public class SampleGrammar {

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		Grammar grammarOne = GrammarBuilder.hasProduction('S', "Aa")
										.hasProduction("A->AbB", "->")
										.hasProductions("B->C|Bba|b", "->", "\\|")
										.hasProductions("C->Cc|Dc|c;D->d", "->", "\\|", ";")
										.withAxiom('S')
										.withEmpty("e")
										.create();
		
		System.out.println(grammarOne);

		Grammar grammarTwo = GrammarBuilder.hasProduction('S', "Aa")
										.hasProduction('S', "Aa")
										.hasProduction('A', "a")
										.withAxiom('S')
										.withEmpty("e")
										.create();

		System.out.println(grammarTwo);
		
		String strGrammarThree = "S->Aa;S->Aa;S->Ba;A->a;B->b";
		
		Grammar grammarThree = GrammarBuilder.hasProductions(strGrammarThree, "->", "\\|", ";")
											.withAxiom('S')
											.withEmpty("e")
											.create();
		
		System.out.println(grammarThree);
		
	}

}
