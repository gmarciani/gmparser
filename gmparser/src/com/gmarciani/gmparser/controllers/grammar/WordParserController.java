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

import com.gmarciani.gmparser.controllers.ui.Listener;
import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.parser.CYKParser;
import com.gmarciani.gmparser.models.parser.LROneParser;
import com.gmarciani.gmparser.models.parser.Parser;
import com.gmarciani.gmparser.models.parser.ParserType;

public class WordParserController {
	
	private static Listener output;
	
	public static void setOutput(Listener listener) {
		output = listener;
	}

	public static boolean parse(Grammar grammar, String word, ParserType parser) {
		if (parser == ParserType.CYK) {
			return parseCYK(grammar, word);
		} else if (parser == ParserType.LR1) {
			return parseLROne(grammar, word);
		} else {
			return false;
		}
	}	

	public static boolean parseCYK(Grammar grammar, String word) {
		CYKParser parser = new CYKParser();
		return parser.parse(grammar, word);
	}	
	
	public static boolean parseLROne(Grammar grammar, String word) {
		Parser parser = new LROneParser();
		return parser.parse(grammar, word);
	}

}
