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

package com.gmarciani.gmparser.models.grammar.transformation;

/**
 * <p>Grammar transformation enumeration model.<p>
 * <p>Supported grammar transformations are:
 * removal of ungenerative symbols (RGS),
 * removal of unreacheable symbols (RRS),
 * removal of useless symbols (RUS),
 * removal of epsilon productions (REP),
 * removal of unit productions (RUP),
 * generation of Chomsky Normal Form (CNF).<p>
 * 
 * @author Giacomo Marciani
 * @version 1.0
 */
public enum GrammarTransformation {
	
	RGS("Remove ungenerative symbols"),
	RRS("Remove unreacheable symbols"),
	RUS("Remove useless symbols"),
	REP("Remove epsilon productions"),
	RUP("Remove unit productions"),
	CNF("Generate Chomsky-Normal-Form");
	
	private String name;
	
	private GrammarTransformation(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the name of the grammar transformation.
	 * 
	 * @return the name of the grammar transformation.
	 */
	public String getName() {
		return this.name;
	}
	
	@Override public String toString() {
		return this.getName();
	}

}
