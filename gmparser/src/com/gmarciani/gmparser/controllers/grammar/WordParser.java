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

import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.GrammarBuilder;
import com.gmarciani.gmparser.models.parser.CYKParser;
import com.gmarciani.gmparser.models.parser.LROneParser;
import com.gmarciani.gmparser.models.parser.Parser;
import com.gmarciani.gmparser.models.parser.ParserType;

/**
 * The word parsing controller.
 * 
 * @see com.gmarciani.gmparser.controllers.app.App
 * @see com.gmarciani.gmparser.controllers.grammar.GrammarAnalyzer
 * @see com.gmarciani.gmparser.controllers.grammar.GrammarTransformer
 * 
 * @author Giacomo Marciani
 * @version 1.0
 */
public class WordParser {
	
	private static WordParser instance;
	
	private WordParser() {}
	
	/**
	 * Returns the word parser controller singleton instance.
	 * 
	 * @return the controller singleton instance.
	 */
	public synchronized static WordParser getInstance() {
		if (instance == null) {
			instance = new WordParser();
		}
		
		return instance;
	}
	
	/**
	 * Parses the specified {@code word} by the specified {@code parser}, according to the specified {@code grammar}.
	 * 
	 * @param grammar the grammar to parse according to.
	 * @param word the word to parse.
	 * @param parser parser class: can be Cock-Younger-Kasami, or LR(1).
	 * @return true, if the {@code word} can be parsed by the {@code parser} according to the {@code grammar}; false, otherwise.
	 */
	@SuppressWarnings("static-access")
	public boolean parse(String strGrammar, String word, ParserType parser) {
		Grammar grammar = GrammarBuilder.hasProductions(strGrammar)
				.withAxiom(Grammar.AXIOM)
				.withEmpty(Grammar.EMPTY)
				.create();
		
		return this.parse(grammar, word, parser);
	}	

	/**
	 * Parses the specified {@code word} by the specified {@code parser}, according to the specified {@code grammar}.
	 * 
	 * @param grammar the grammar to parse according to.
	 * @param word the word to parse.
	 * @param parser parser class: can be Cock-Younger-Kasami, or LR(1).
	 * @return true, if the {@code word} can be parsed by the {@code parser} according to the {@code grammar}; false, otherwise.
	 */
	public boolean parse(Grammar grammar, String word, ParserType parser) {
		if (parser == ParserType.CYK) {
			return parseCYK(grammar, word);
		} else if (parser == ParserType.LR1) {
			return parseLROne(grammar, word);
		} else {
			return false;
		}
	}	

	/**
	 * Parses the specified {@code word} by the Cock-Younger-Kasami parser, according to the specified {@code grammar}.
	 * 
	 * @param grammar the grammar to parse according to.
	 * @param word the word to parse.
	 * @return true, if the {@code word} can be parsed by the Cock-Younger-Kasami parser, according to the {@code grammar}; false, otherwise.
	 */
	public boolean parseCYK(Grammar grammar, String word) {
		CYKParser parser = new CYKParser();
		return parser.parse(grammar, word);
	}	
	
	/**
	 * Parses the specified {@code word} by the LR(1) parser, according to the specified {@code grammar}.
	 * 
	 * @param grammar the grammar to parse according to.
	 * @param word word the word to parse.
	 * @return true, if the {@code word} can be parsed by the LR(1) parser, according to the {@code grammar}; false, otherwise.
	 */
	public boolean parseLROne(Grammar grammar, String word) {
		Parser parser = new LROneParser();
		return parser.parse(grammar, word);
	}

}
