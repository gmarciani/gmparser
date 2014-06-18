package com.gmarciani.gmparser.models.parser.cyk.session;

import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.parser.ParserType;
import com.gmarciani.gmparser.models.parser.ParsingSession;
import com.gmarciani.gmparser.models.parser.cyk.recognition.CYKMatrix;

/**
 * <p>The parsing session report for the Cocke-Younger-Kasami parser.<p>
 * <p>A parsing session is a container of all input/output parameters used/provided by the Cocke-Younger-Kasami parser.<p>
 * 
 * @see com.gmarciani.gmparser.models.parser.cyk.CYKParser
 * 
 * @author Giacomo Marciani
 * @version 1.0
 */
public class CYKParsingSession extends ParsingSession {
	
	private final CYKMatrix recognitionMatrix;

	/**
	 * Creates a new Cocke-Younger-Kasami parsing session.
	 * 
	 * @param grammar the grammar to parser with.
	 * @param word the word to parse.
	 * @param recognitionMatrix	the Cocke-Younger-Kasami parser recognition matrix.
	 * @param result the parsing result.
	 */
	public CYKParsingSession(Grammar grammar, 
			String word, 
			CYKMatrix recognitionMatrix, 
			boolean result) {
		super(grammar, word, ParserType.CYK, result);
		this.recognitionMatrix = recognitionMatrix;
	}
	
	/**
	 * Returns the recognition matrix used by the Cocke-Younger-Kasami parser.
	 * 
	 * @return the recognition matrix used by the Cocke-Younger-Kasami parser.
	 */
	public CYKMatrix getRecognitionMatrix() {
		return this.recognitionMatrix;
	}

	@Override protected String getFormattedSessionContent() {
		return this.getRecognitionMatrix().toFormattedMatrix();
	}

}
