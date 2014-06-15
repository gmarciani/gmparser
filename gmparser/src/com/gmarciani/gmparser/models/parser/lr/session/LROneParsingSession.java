package com.gmarciani.gmparser.models.parser.lr.session;

import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.parser.ParserType;
import com.gmarciani.gmparser.models.parser.ParsingSession;
import com.gmarciani.gmparser.models.parser.lr.matrix.LROneMatrix;

public class LROneParsingSession extends ParsingSession {
	
	private final LROneMatrix recognitionMatrix;

	public LROneParsingSession(Grammar grammar, 
			String word,
			LROneMatrix recognitionMatrix, 
			boolean result) {
		super(grammar, word, ParserType.LR1, result);
		this.recognitionMatrix = recognitionMatrix;
	}
	
	public LROneMatrix getRecognitionMatrix() {
		return this.recognitionMatrix;
	}

	@Override protected String getFormattedSessionContent() {
		return this.getRecognitionMatrix().toExtendedFormattedMatrix();
	}

}
