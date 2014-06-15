package com.gmarciani.gmparser.models.parser.cyk.session;

import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.parser.ParserType;
import com.gmarciani.gmparser.models.parser.ParsingSession;
import com.gmarciani.gmparser.models.parser.cyk.matrix.CYKMatrix;

public class CYKParsingSession extends ParsingSession {
	
	private final CYKMatrix recognitionMatrix;

	public CYKParsingSession(Grammar grammar, 
			String word, 
			CYKMatrix recognitionMatrix, 
			boolean result) {
		super(grammar, word, ParserType.CYK, result);
		this.recognitionMatrix = recognitionMatrix;
	}
	
	public CYKMatrix getRecognitionMatrix() {
		return this.recognitionMatrix;
	}

	@Override protected String getFormattedSessionContent() {
		return this.getRecognitionMatrix().toFormattedMatrix();
	}

}
