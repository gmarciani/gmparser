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

package com.gmarciani.gmparser;

import org.apache.commons.cli.ParseException;

import com.gmarciani.gmparser.controllers.App;

/**
 * <p>GMParser entry-point.<p>
 * 
 * @see com.gmarciani.gmparser.controllers.App
 * @see com.gmarciani.gmparser.controllers.Output
 * 
 * @author Giacomo Marciani
 * @version 1.0
 */
public final class GMParser {
	
	protected GMParser() {}

	/**
	 * <p>The GMParser app main.<p>
	 * 
	 * @param args command-line arguments.
	 */
	public static void main(String[] args) {	
		App app = App.getInstance();
		app.printWelcome();
		try {
			app.play(args);
		} catch (ParseException exc) {
			app.getOutput().onWarning(exc.getMessage());
			app.quit();
		}	
	}	

}
