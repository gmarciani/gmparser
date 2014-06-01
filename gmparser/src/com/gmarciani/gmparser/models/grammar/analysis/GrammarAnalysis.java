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

package com.gmarciani.gmparser.models.grammar.analysis;

import java.io.StringWriter;
import java.util.Set;

import com.brsanthu.dataexporter.DataExporter;
import com.brsanthu.dataexporter.output.texttable.TextTableExporter;
import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.NormalForm;
import com.gmarciani.gmparser.models.grammar.Type;
import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;
import com.gmarciani.gmparser.models.grammar.production.Productions;

public class GrammarAnalysis {
	
	private Character axiom;
	private String empty;
	private Alphabet nonTerminalAlphabet;
	private Alphabet terminalAlphabet;
	private Productions productions;
	private Type grammarType;
	private Set<NormalForm> normalForms;
	private Productions epsilonProductions;
	private Productions unitProductions;
	private Productions nonTrivialUnitProductions;
	private Productions trivialUnitProductions;
	private Alphabet nullables;

	public GrammarAnalysis(Grammar grammar) {
		System.out.println(grammar);
		Grammar copy = new Grammar(grammar);
		System.out.println(copy);
		this.axiom = copy.getAxiom();
		this.empty = copy.getEmpty();
		this.nonTerminalAlphabet = new Alphabet(copy.getNonTerminals());
		this.terminalAlphabet = new Alphabet(copy.getTerminals());
		this.productions = new Productions(copy.getProductions());
		this.grammarType = copy.getType();
		this.normalForms = copy.getNormalForm();
		this.epsilonProductions = new Productions(copy.getEpsilonProductions());
		this.unitProductions = new Productions(copy.getUnitProductions());
		this.nonTrivialUnitProductions = new Productions(copy.getNonTrivialUnitProductions());
		this.trivialUnitProductions = new Productions(copy.getTrivialUnitProductions());
		//this.nullables = new Alphabet(copy.getNullables());		
	}

	public Character getAxiom() {
		return this.axiom;
	}

	public String getEmpty() {
		return this.empty;
	}

	public Alphabet getNonTerminalAlphabet() {
		return this.nonTerminalAlphabet;
	}

	public Alphabet getTerminalAlphabet() {
		return this.terminalAlphabet;
	}

	public Productions getProductions() {
		return this.productions;
	}

	public Type getGrammarType() {
		return this.grammarType;
	}

	public Set<NormalForm> getNormalForms() {
		return this.normalForms;
	}

	public Productions getEpsilonProductions() {
		return this.epsilonProductions;
	}

	public Productions getUnitProductions() {
		return this.unitProductions;
	}

	public Productions getNonTrivialUnitProductions() {
		return this.nonTrivialUnitProductions;
	}

	public Productions getTrivialUnitProductions() {
		return this.trivialUnitProductions;
	}

	public Alphabet getNullables() {
		return this.nullables;
	}
	
	@Override public String toString() {
		StringWriter sw = new StringWriter();
        
        DataExporter exporter = new TextTableExporter(sw);
        exporter.addColumn("Hello");
        exporter.addRow("World!");
        exporter.finishExporting();
        
        return sw.toString();
	}

}
