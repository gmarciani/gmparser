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

package com.gmarciani.gmparser.models.commons.menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.bethecoder.ascii_table.ASCIITable;


public class Menu {
	
	private String name;
	private String description;	
	private Map<Integer, String> choices = new HashMap<Integer, String>();
	
	public Menu() {
		
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

	public Map<Integer, String> getChoices() {
		return this.choices;
	}

	public void setChoices(Map<Integer, String> choices) {
		this.choices = choices;
	}
	
	public void addChoice(int key, String choice) {
		this.choices.put(key, choice);
	}
	
	public int run() {
		this.show();
		System.out.print("[choice]> ");
		System.out.flush();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    int choice = -1;
	    try {
			choice = Integer.parseInt(br.readLine());
		} catch (NumberFormatException | IOException e) {
			
		}
	    
	    System.out.print("\n");
	    
		return choice;
	}
	
	private void show() {		
		String header[] = {this.getName(), this.getDescription()};
		String data[][] = new String[this.getChoices().size()][2];
		int c = 0;
		for (Map.Entry<Integer, String> choice : this.getChoices().entrySet()) {
			data[c][0] = choice.getKey().toString();
			data[c][1] = choice.getValue();
			c++;
		}	
		
		ASCIITable.getInstance().printTable(header, ASCIITable.ALIGN_LEFT, data, ASCIITable.ALIGN_LEFT);
	}
	
	@Override
	public String toString() {
		String s = "Menu(" + this.getName() + " | " + this.getDescription() + " | " + this.getChoices() + ")";
		return s;
	}

}
