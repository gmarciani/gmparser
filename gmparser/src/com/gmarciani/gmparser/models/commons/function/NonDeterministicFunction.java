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

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import com.bethecoder.ascii_table.ASCIITable;
import com.gmarciani.gmparser.models.commons.nple.Triple;
import com.gmarciani.gmparser.models.commons.set.AdvancedSet;

public class NonDeterministicFunction<X extends Comparable<X>, Y extends Comparable<Y>, Z extends Comparable<Z>> {
	
	private AdvancedSet<Triple<X, Y, Z>> set;
	private Set<X> domainX;
	private Set<Y> domainY;
	
	public NonDeterministicFunction(Set<X> domainX, Set<Y> domainY) {
		this.setSet(new AdvancedSet<Triple<X, Y, Z>>());
		this.setDomainX(domainX);
		this.setDomainY(domainY);		
	}
	
	public NonDeterministicFunction() {
		this.setSet(new AdvancedSet<Triple<X, Y, Z>>());
		this.setDomainX(new AdvancedSet<X>());
		this.setDomainY(new AdvancedSet<Y>());		
	}
	
	public AdvancedSet<Triple<X, Y, Z>> getSet() {
		return this.set;
	}
	
	private void setSet(AdvancedSet<Triple<X, Y, Z>> set) {
		this.set = set;
	}
	
	public Set<X> getDomainX() {
		return this.domainX;
	}
	
	private void setDomainX(Set<X> domainX) {
		this.domainX = domainX;
	}
	
	public Set<Y> getDomainY() {
		return this.domainY;
	}
	
	private void setDomainY(Set<Y> domainY) {
		this.domainY = domainY;
	}
	
	public boolean add(X x, Y y, Z z) {
		Triple<X, Y, Z> triple = new Triple<X, Y, Z>(x, y, z);
		this.getDomainX().add(x);
		this.getDomainY().add(y);
		return this.getSet().add(triple);
	}
	
	public boolean remove(X x, Y y, Z z) {
		Triple<X, Y, Z> triple = new Triple<X, Y, Z>(x, y, z);
		boolean removed = this.getSet().remove(triple);
		if (this.getAllForX(x).isEmpty())
			this.getDomainX().remove(x);
		if (this.getAllForY(y).isEmpty())
			this.getDomainY().remove(y);
		return removed;
	}
	
	public boolean removeAllForXY(X x, Y y) {
		boolean removedX = this.removeAllForX(x);
		boolean removedY = this.removeAllForY(y);
		return removedX || removedY;
	}
	
	public boolean removeAllForX(X x) {
		boolean removed = false;
		for (Triple<X, Y, Z> triple : this.getSet())
			if (triple.getX().equals(x))
				removed = this.remove(triple.getX(), triple.getY(), triple.getZ()) ? true : removed;		
		return removed;
	}
	
	public boolean removeAllForY(Y y) {
		boolean removed = false;
		for (Triple<X, Y, Z> triple : this.getSet())
			if (triple.getY().equals(y))
				removed = this.remove(triple.getX(), triple.getY(), triple.getZ()) ? true : removed;	
		return removed;
	}
	
	public AdvancedSet<Triple<X, Y, Z>> getAllForXY(X x, Y y) {
		AdvancedSet<Triple<X, Y, Z>> triples = new AdvancedSet<Triple<X, Y, Z>>();		
		for (Triple<X, Y, Z> triple : this.getSet()) 
			if (triple.getX().equals(x)
					&& triple.getY().equals(y))
				triples.add(triple);		
		return triples;
	}
	
	public AdvancedSet<Triple<X, Y, Z>> getAllForX(X x) {
		AdvancedSet<Triple<X, Y, Z>> triples = new AdvancedSet<Triple<X, Y, Z>>();		
		for (Triple<X, Y, Z> triple : this.getSet()) 
			if (triple.getX().equals(x))
				triples.add(triple);		
		return triples;
	}
	
	public AdvancedSet<Triple<X, Y, Z>> getAllForY(Y y) {
		AdvancedSet<Triple<X, Y, Z>> triples = new AdvancedSet<Triple<X, Y, Z>>();
		for (Triple<X, Y, Z> triple : this.getSet()) 
			if (triple.getY().equals(y))
				triples.add(triple);
		return triples;
	}

	public String toFormattedString() {
		String table = null;
		if (this.getDomainX().isEmpty()
				&& this.getDomainY().isEmpty()) {
			String header[] = {"#", "null"};
			String data[][] = {{"null", "null",}};
			table = ASCIITable.getInstance().getTable(header, ASCIITable.ALIGN_CENTER, data, ASCIITable.ALIGN_LEFT);
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
			table = ASCIITable.getInstance().getTable(header, ASCIITable.ALIGN_CENTER, data, ASCIITable.ALIGN_LEFT);
		} else if (this.getDomainY().isEmpty()) {
			String header[] = {"#", "null"};
			String data[][] = new String[this.getDomainX().size()][2];
			int r = 0;
			for (X x : this.getDomainX()) {
				data[r][0] = String.valueOf(x);
				data[r][1] = "null";
				r ++;
			}
			table = ASCIITable.getInstance().getTable(header, ASCIITable.ALIGN_CENTER, data, ASCIITable.ALIGN_LEFT);
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
					AdvancedSet<Triple<X, Y, Z>> triplesXY = this.getAllForXY(x, y);
					AdvancedSet<Z> z = new AdvancedSet<Z>();
					for (Triple<X, Y, Z> tripleXY : triplesXY)
						z.add(tripleXY.getZ());
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
			table = ASCIITable.getInstance().getTable(header, ASCIITable.ALIGN_CENTER, data, ASCIITable.ALIGN_LEFT);
		}			
        
        return table;
	}
	
	@Override public String toString() {
		String string = "{";
		
		Iterator<Triple<X, Y, Z>> iter = this.getSet().iterator();
		while(iter.hasNext()) {
			Triple<X, Y, Z> triple = iter.next();
			string += triple;
			if (iter.hasNext())
				string += ",";
		}
		
		string += "}";
		
		return string;
	}
	
	@SuppressWarnings("unchecked")
	@Override public boolean equals(Object obj) {
		if (this.getClass() != obj.getClass())
			return false;
		
		NonDeterministicFunction<X, Y, Z> other = (NonDeterministicFunction<X, Y, Z>) obj;
		
		return (this.getDomainX().equals(other.getDomainX())
				&& this.getDomainY().equals(other.getDomainY())
				&& this.getSet().containsAll(other.getSet())
				&& other.getSet().containsAll(this.getSet()));
	}
	
	@Override public int hashCode() {
		return Objects.hash(this.getDomainX(), this.getDomainY(), this.getSet());
	}

}
