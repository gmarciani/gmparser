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

package com.gmarciani.gmparser.models.parser;

import com.bethecoder.ascii_table.ASCIITable;
import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.analysis.GrammarAnalysis;

/**
 * @author Giacomo Marciani
 * @version 1.0
 */
public abstract class ParsingSession {	
	
	private final Grammar grammar;
	private final String word;
	private final GrammarAnalysis grammarAnalysis;
	private final ParserType parserType;
	private final boolean result;
	
	public ParsingSession(Grammar grammar, 
			String word, 
			ParserType parserType,
			boolean result) {
		this.grammar = grammar;
		this.word = word;
		this.grammarAnalysis = grammar.generateGrammarAnalysis();
		this.parserType = parserType;
		this.result = result;
	}
	
	public ParserType getParserType() {
		return this.parserType;
	}
	
	public Grammar getGrammar() {
		return this.grammar;
	}
	
	public String getWord() {
		return this.word;
	}
	
	public GrammarAnalysis getGrammarAnalysis() {
		return this.grammarAnalysis;
	}
	
	public boolean getResult() {
		return this.result;
	}
	
	protected String getFormattedHeader() {
		String string = "\n###########################";
		string +=         "##### PARSING SESSION #####";
		string +=         "###########################";
		return string;
	}
	
	protected String getFormattedFooter() {
		String string = "###########################";
		string +=         "##### END OF PARSING SESSION #####";
		string +=         "###########################\n";
		return string;
	}
	
	protected String getFormattedSummary() {
		String grammar = this.getGrammarAnalysis().toString();
		String header[] = {"WORD", "PARSER", "RESULT"};
		String data[][] = {{this.getWord(), this.getParserType().getName(), String.valueOf(this.getResult())}};
		String wordParserResult = ASCIITable.getInstance().getTable(header, ASCIITable.ALIGN_CENTER, data, ASCIITable.ALIGN_LEFT);
		String string = grammar + wordParserResult;
		return string;
	}
	
	protected abstract String getFormattedSessionContent();
	
	public String toFormattedParsingSession() {
		String header = this.getFormattedHeader();
		String session = this.getFormattedSummary() + this.getFormattedSessionContent();
		String footer = this.getFormattedFooter();
		String string = header + "\n\n" + session + "\n\n" + footer;
		return string;
	}

}
