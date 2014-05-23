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

package com.gmarciani.gmparser.views.interaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.bethecoder.ascii_table.ASCIITable;

public class Interaction {

	private String name;
	private String description;
	
	public Interaction() {
		
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String run() {
		this.show();
		System.out.print("input: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = null;
	    try {
	    	input = br.readLine();
		} catch (NumberFormatException | IOException e) {
			
		}
	    System.out.println("\n");
		return input;
	}
	
	private void show() {		
		String header[] = {this.getName()};
		String data[][] = {{this.getDescription()}};
		
		ASCIITable.getInstance().printTable(header, -1, data, -1);
	}
	
	@Override
	public String toString() {
		String s = "Interaction(" + this.getName() + " | " + this.getDescription() + ")";
		return s;
	}
}
