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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.bethecoder.ascii_table.ASCIITable;
import com.gmarciani.gmparser.models.grammar.Grammar;
import com.gmarciani.gmparser.models.grammar.alphabet.Alphabet;
import com.gmarciani.gmparser.models.grammar.production.Productions;

/**
 * <p>Grammar analysis report model.<p>
 * <p>A grammar analysis report is a complete report of the most important parameters of a grammar<p>
 * <p>It exposes the following parameters:
 * terminal alphabet, non terminal alphabet, axiom, epsilon, productions, 
 * grammar type, grammar extension, grammar normal forms,
 * epsilon productions, unit productions, trivial unit productions, non trivial unit productions,
 * nullables symbols, ungenerative symbols, unreacheable symbols and useless symbols.
 * 
 * @see com.gmarciani.gmparser.models.grammar.Grammar
 * 
 * @author Giacomo Marciani
 * @version 1.0
 */
public class GrammarAnalysis {
	
	private String title;
	
	private Character axiom;
	private Character epsilon;
	private Alphabet nonTerminalAlphabet;
	private Alphabet terminalAlphabet;
	private Productions productions;
	private Type grammarType;
	private Extension grammarExtension;
	private Set<NormalForm> normalForms;
	private Productions epsilonProductions;
	private Productions unitProductions;
	private Productions nonTrivialUnitProductions;
	private Productions trivialUnitProductions;
	private Alphabet nullables;
	private Alphabet ungeneratives;
	private Alphabet unreacheables;
	private Alphabet useless;
	
	private Map<String, String> parameters;

	/**
	 * Constructs a grammar analysis for the specified grammar.
	 * 
	 * @param grammar the grammar to analyze.
	 */
	public GrammarAnalysis(Grammar grammar) {
		this.title = "GRAMMAR ANALYSIS";
		Grammar copy = new Grammar(grammar);
		this.productions = new Productions(copy.getProductions());
		this.nonTerminalAlphabet = new Alphabet(copy.getNonTerminals());
		this.terminalAlphabet = new Alphabet(copy.getTerminals());		
		this.axiom = copy.getAxiom();
		this.epsilon = copy.getEpsilon();
		this.grammarType = copy.getType();
		this.grammarExtension = copy.getExtension();
		this.normalForms = copy.getNormalForm();
		this.epsilonProductions = new Productions(copy.getEpsilonProductions());
		this.unitProductions = new Productions(copy.getUnitProductions());
		this.nonTrivialUnitProductions = new Productions(copy.getNonTrivialUnitProductions());
		this.trivialUnitProductions = new Productions(copy.getTrivialUnitProductions());
		this.nullables = new Alphabet(copy.getNullables());	
		this.ungeneratives = new Alphabet(copy.getUngeneratives());
		this.unreacheables = new Alphabet(copy.getUnreacheables());
		this.useless = new Alphabet(copy.getUseless());
		
		this.parameters = new LinkedHashMap<String, String>();
		this.parameters.put("Productions", this.getProductions().toString());		
		this.parameters.put("Non Terminals", this.getNonTerminalAlphabet().toString());
		this.parameters.put("Terminals", this.getTerminalAlphabet().toString());
		this.parameters.put("Axiom", this.getAxiom().toString());
		this.parameters.put("Epsilon", this.getEpsilon().toString());
		this.parameters.put("Grammar Type", this.getGrammarType().getName());
		this.parameters.put("Extension", this.getExtension().getName());
		this.parameters.put("Normal Forms", this.getNormalForms().toString());
		this.parameters.put("Epsilon Productions", this.getEpsilonProductions().toString());
		this.parameters.put("Unit Productions", this.getUnitProductions().toString());
		this.parameters.put("Non Trivial Unit Productions", this.getNonTrivialUnitProductions().toString());
		this.parameters.put("Trivial Unit Productions", this.getTrivialUnitProductions().toString());
		this.parameters.put("Nullables", this.getNullables().toString());
		this.parameters.put("Ungeneratives", this.getUngeneratives().toString());
		this.parameters.put("Unreacheables", this.getUnreacheables().toString());
		this.parameters.put("Useless", this.getUseless().toString());
	}
	
	/**
	 * Returns the title of the grammar analysis.
	 * 
	 * @return the title of the grammar analysis.
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * Sets the title of the grammar analysis.
	 * 
	 * @param title the title of the grammar analysis.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Returns the axiom of the grammar.
	 * 
	 * @return the axiom of the grammar.
	 */
	public Character getAxiom() {
		return this.axiom;
	}

	/**
	 * Returns the epsilon representation of the grammar.
	 * 
	 * @return the epsilon representation of the grammar.
	 */
	public Character getEpsilon() {
		return this.epsilon;
	}
	
	/**
	 * Returns the non terminal alphabet of the grammar.
	 * 
	 * @return the non terminal alphabet of the grammar.
	 */
	public Alphabet getTerminalAlphabet() {
		return this.terminalAlphabet;
	}

	/**
	 * Returns the non terminal alphabet of the grammar.
	 * 
	 * @return the non terminal alphabet of the grammar.
	 */
	public Alphabet getNonTerminalAlphabet() {
		return this.nonTerminalAlphabet;
	}	

	/**
	 * Returns the set of productions of the grammar.
	 * 
	 * @return the set of productions of the grammar.
	 */
	public Productions getProductions() {
		return this.productions;
	}

	/**
	 * Returns the grammar type of the grammar.
	 * 
	 * @return the grammar type of the grammar.
	 */
	public Type getGrammarType() {
		return this.grammarType;
	}
	
	/**
	 * Returns the extension of the grammar.
	 * 
	 * @return the extension of the grammar.
	 */
	public Extension getExtension() {
		return this.grammarExtension;
	}

	/**
	 * Returns the matching normal forms of the grammar.
	 * 
	 * @return the matching normal forms of the grammar.
	 */
	public Set<NormalForm> getNormalForms() {
		return this.normalForms;
	}

	/**
	 * Returns the epsilon productions of the grammar.
	 * 
	 * @return the epsilon productions of the grammar.
	 */
	public Productions getEpsilonProductions() {
		return this.epsilonProductions;
	}

	/**
	 * Returns the set of unit productions of the grammar.
	 * 
	 * @return the set of unit productions of the grammar.
	 */
	public Productions getUnitProductions() {
		return this.unitProductions;
	}

	/**
	 * Returns the set of non trivial unit productions of the grammar.
	 * 
	 * @return the set of non trivial unit productions of the grammar.
	 */
	public Productions getNonTrivialUnitProductions() {
		return this.nonTrivialUnitProductions;
	}

	/**
	 * Returns the set of trivial unit productions of the grammar.
	 * 
	 * @return the set of trivial unit productions of the grammar.
	 */
	public Productions getTrivialUnitProductions() {
		return this.trivialUnitProductions;
	}

	/**
	 * Returns the set of nullable symbols of the grammar.
	 * 
	 * @return the set of nullable symbols of the grammar.
	 */
	public Alphabet getNullables() {
		return this.nullables;
	}
	
	/**
	 * Returns the set of ungenerative symbols of the grammar.
	 * 
	 * @return the set of ungenerative symbols of the grammar.
	 */
	public Alphabet getUngeneratives() {
		return this.ungeneratives;
	}
	
	/**
	 * Returns the set of unreacheable symbols of the grammar.
	 * 
	 * @return the set of unreacheable symbols of the grammar.
	 */
	public Alphabet getUnreacheables() {
		return this.unreacheables;
	}
	
	/**
	 * Returns the set of useless symbols of the grammar.
	 * 
	 * @return the set of useless symbols of the grammar.
	 */
	public Alphabet getUseless() {
		return this.useless;
	}
	
	/**
	 * Returns the set of all parameters of the grammar analysis.
	 * 
	 * @return the map of all parameters of the grammar analysis.
	 */
	public Map<String, String> getParameters() {
		return this.parameters;
	}
	
	@Override public String toString() {
		String header[] = {this.getTitle()};
		String data[][] = new String[this.getParameters().size()][1];
		
        int i = 0;
        for (Map.Entry<String, String> parameter : this.getParameters().entrySet()) {
        	data[i][0] = "\u0700" + " " + parameter.getKey() + ": " + parameter.getValue();
        	i ++;
        }        
        return ASCIITable.getInstance().getTable(header, ASCIITable.ALIGN_CENTER, data, ASCIITable.ALIGN_LEFT);  
	}

}
