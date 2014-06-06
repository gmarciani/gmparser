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

package com.gmarciani.gmparser.models.commons.function;

import java.util.Set;

import com.bethecoder.ascii_table.ASCIITable;
import com.gmarciani.gmparser.models.commons.nple.Triple;
import com.gmarciani.gmparser.models.commons.set.AdvancedSet;

public class DeterministicFunction<X extends Comparable<X>, Y extends Comparable<Y>, Z extends Comparable<Z>> 
	extends NonDeterministicFunction<X, Y, Z> {
	
	public DeterministicFunction(Set<X> domainX, Set<Y> domainY) {
		super(domainX, domainY);
	}

	public DeterministicFunction() {
		super();
	}
	
	@Override public boolean add(X x, Y y, Z z) {
		super.removeAllForXY(x, y);
		this.getDomainX().add(x);
		this.getDomainY().add(y);
		Triple<X, Y, Z> triple = new Triple<X, Y, Z>(x, y, z);
		return this.getSet().add(triple);
	}
	
	public boolean remove(X x, Y y) {
		return super.removeAllForXY(x, y);
	}
	
	public Z get(X x, Y y) {
		AdvancedSet<Triple<X, Y, Z>> values = super.getAllForXY(x, y);
		if (values.isEmpty())
			return null;
		
		return values.first().getZ();
	}
	
	@Override public String toFormattedString() {
		String table = null;
		if (this.getDomainX().isEmpty()
				&& this.getDomainY().isEmpty()) {
			String header[] = {"#", "null"};
			String data[][] = {{"null", "null",}};
			table = ASCIITable.getInstance().getTable(header, ASCIITable.ALIGN_CENTER, data, ASCIITable.ALIGN_CENTER);
		} else if (this.getDomainX().isEmpty()) {
			String header[] = new String[this.getDomainY().size() + 1];
			header[0] = "#";
			int c = 1;
			for (Y y : this.getDomainY()) {			
				header[c] = String.valueOf(y);
				c ++;
			}
			String data[][] = new String[1][this.getDomainY().size() + 1];
			for (c = 0 ; c < data[0].length; c ++) 
				data[0][c] = "null";
			table = ASCIITable.getInstance().getTable(header, ASCIITable.ALIGN_CENTER, data, ASCIITable.ALIGN_CENTER);
		} else if (this.getDomainY().isEmpty()) {
			String header[] = {"#", "null"};
			String data[][] = new String[this.getDomainX().size()][2];
			int r = 0;
			for (X x : this.getDomainX()) {
				data[r][0] = String.valueOf(x);
				data[r][1] = "null";
				r ++;
			}
			table = ASCIITable.getInstance().getTable(header, ASCIITable.ALIGN_CENTER, data, ASCIITable.ALIGN_CENTER);
		} else {
			String header[] = new String[this.getDomainY().size() + 1];
			header[0] = "#";
			int c = 1;
			for (Y y : this.getDomainY()) {			
				header[c] = String.valueOf(y);
				c ++;
			}
			
			String data[][] = new String[this.getDomainX().size()][this.getDomainY().size() + 1];
			int r = 0;
			for (X x : this.getDomainX()) {
				data[r][0] = String.valueOf(x);
				r ++;
			}
			
			for (X x : this.getDomainX()) {
				for (Y y : this.getDomainY()) {
					Z z = this.get(x, y);
					int xIndex;
					for (xIndex = 0; xIndex < data.length; xIndex ++)
						if (data[xIndex][0].equals(String.valueOf(x)))
							break;
					
					int yIndex;
					for (yIndex = 0; yIndex < header.length; yIndex ++)
						if (header[yIndex].equals(String.valueOf(y)))
							break;						
					
					data[xIndex][yIndex] = String.valueOf(z);
				}
			}
			table = ASCIITable.getInstance().getTable(header, ASCIITable.ALIGN_CENTER, data, ASCIITable.ALIGN_CENTER);
		}			
        
        return table;
	}

}
