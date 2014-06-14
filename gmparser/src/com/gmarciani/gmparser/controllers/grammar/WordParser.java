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
import com.gmarciani.gmparser.models.parser.cyk.CYKParser;
import com.gmarciani.gmparser.models.parser.cyk.CYKParsingSession;
import com.gmarciani.gmparser.models.parser.cyk.matrix.CYKMatrix;
import com.gmarciani.gmparser.models.parser.lr.LROneParser;
import com.gmarciani.gmparser.models.parser.lr.LROneParsingSession;
import com.gmarciani.gmparser.models.parser.lr.matrix.LROneMatrix;

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
		if (instance == null)
			instance = new WordParser();
		return instance;
	}
	
	public CYKParsingSession getCYKParsingSession(Grammar grammar, String word) {
		CYKMatrix recognitionMatrix = CYKParser.getRecognitionMatrix(grammar, word);
		boolean result = CYKParser.parse(grammar, word);
		return new CYKParsingSession(grammar, word, recognitionMatrix, result);
	}
	
	public LROneParsingSession getLROneParsingSession(Grammar grammar, String word) {
		LROneMatrix recognitionMatrix = LROneParser.getRecognitionMatrix(grammar);
		boolean result = LROneParser.parse(grammar, word);
		return new LROneParsingSession(grammar, word, recognitionMatrix, result);
	}

}
