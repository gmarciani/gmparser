package com.gmarciani.gmparser.models.parser;

import com.gmarciani.gmparser.models.grammar.Grammar;

public interface Parser {
	
	public boolean parse(Grammar grammar, String word);

}
