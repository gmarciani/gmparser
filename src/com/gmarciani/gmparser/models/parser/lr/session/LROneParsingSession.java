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

package com.gmarciani.gmparser.models.parser.lr.session;

import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.parser.ParserType;
import com.gmarciani.gmparser.models.parser.ParsingSession;
import com.gmarciani.gmparser.models.parser.lr.recognition.LROneMatrix;

/**
 * <p>The parsing session report for the LR(1) parser.<p>
 * <p>A parsing session is a container of all input/output parameters used/provided by the LR(1) parser.<p>
 * 
 * @see com.gmarciani.gmparser.models.parser.lr.LROneParser
 * 
 * @author Giacomo Marciani
 * @version 1.0
 */
public class LROneParsingSession extends ParsingSession {
	
	private final LROneMatrix recognitionMatrix;

	/**
	 * Creates a new LR(1) parsing session.
	 * 
	 * @param grammar the grammar to parser with.
	 * @param word the word to parse.
	 * @param recognitionMatrix	the LR(1) parser recognition matrix.
	 * @param result the parsing result.
	 */
	public LROneParsingSession(Grammar grammar, 
			String word,
			LROneMatrix recognitionMatrix, 
			boolean result) {
		super(grammar, word, ParserType.LR1, result);
		this.recognitionMatrix = recognitionMatrix;
	}
	
	/**
	 * Returns the recognition matrix used by the LR(1) parser.
	 * 
	 * @return the recognition matrix used by the LR(1) parser.
	 */
	public LROneMatrix getRecognitionMatrix() {
		return this.recognitionMatrix;
	}

	@Override protected String getFormattedSessionContent() {
		return this.getRecognitionMatrix().toExtendedFormattedMatrix();
	}

}
