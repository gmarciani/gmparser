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

import com.gmarciani.gmparser.models.commons.nple.Triple;
import com.gmarciani.gmparser.models.commons.set.GSet;

public abstract class AbstractFunction<X extends Comparable<X>,
							  Y extends Comparable<Y>,
							  Z extends Comparable<Z>> 
							  implements Function<X, Y, Z> {	
	
	protected GSet<X> domainX;
	protected GSet<Y> domainY;
	protected GSet<Z> codomain;
	protected GSet<Triple<X, Y, Z>> function;
	
	public AbstractFunction(GSet<X> domainX, GSet<Y>domainY) {
		this.function = new GSet<Triple<X, Y, Z>>();
		this.domainX = domainX;
		this.domainY = domainY;	
	}
	
	public AbstractFunction() {
		this.function = new GSet<Triple<X, Y, Z>>();
		this.domainX = new GSet<X>();
		this.domainY = new GSet<Y>();
	}

	@Override public GSet<X> getDomainX() {
		return this.domainX;
	}

	@Override public GSet<Y> getDomainY() {
		return this.domainY;
	}

	@Override public GSet<Z> getCodomain() {
		return this.codomain;
	}

	protected GSet<Triple<X, Y, Z>> getFunction() {
		return this.function;
	}

	@Override public int getDomainXCardinality() {
		return this.domainX.size();
	}

	@Override public int getDomainYCardinality() {
		return this.domainY.size();
	}

	@Override public int getCodomainCardinality() {
		return this.codomain.size();
	}

	@Override public abstract boolean add(X x, Y y, Z z);

	@Override public boolean remove(X x, Y y, Z z) {
		Triple<X, Y, Z> triple = new Triple<X, Y, Z>(x, y, z);
		boolean removed = this.getFunction().remove(triple);
		if (this.getAllForX(x).isEmpty())
			this.getDomainX().remove(x);
		if (this.getAllForY(y).isEmpty())
			this.getDomainY().remove(y);
		return removed;
	}

	@Override public boolean removeAllForXY(X x, Y y) {
		boolean removed = false;
		for (Triple<X, Y, Z> triple : this.getFunction())
			if (triple.getX().equals(x)
					&& triple.getY().equals(y))
				removed = this.remove(triple.getX(), triple.getY(), triple.getZ()) ? true : removed;
		return removed;
	}

	@Override public boolean removeAllForX(X x) {
		boolean removed = false;
		for (Triple<X, Y, Z> triple : this.getFunction())
			if (triple.getX().equals(x))
				removed = this.remove(triple.getX(), triple.getY(), triple.getZ()) ? true : removed;		
		return removed;
	}

	@Override public boolean removeAllForY(Y y) {
		boolean removed = false;
		for (Triple<X, Y, Z> triple : this.getFunction())
			if (triple.getY().equals(y))
				removed = this.remove(triple.getX(), triple.getY(), triple.getZ()) ? true : removed;	
		return removed;
	}

	@Override public GSet<Triple<X, Y, Z>> getAllForXY(X x, Y y) {
		GSet<Triple<X, Y, Z>> triples = new GSet<Triple<X, Y, Z>>();		
		for (Triple<X, Y, Z> triple : this.getFunction()) 
			if (triple.getX().equals(x)
					&& triple.getY().equals(y))
				triples.add(triple);		
		return triples;
	}

	@Override public GSet<Triple<X, Y, Z>> getAllForX(X x) {
		GSet<Triple<X, Y, Z>> triples = new GSet<Triple<X, Y, Z>>();		
		for (Triple<X, Y, Z> triple : this.getFunction()) 
			if (triple.getX().equals(x))
				triples.add(triple);		
		return triples;
	}

	@Override public GSet<Triple<X, Y, Z>> getAllForY(Y y) {
		GSet<Triple<X, Y, Z>> triples = new GSet<Triple<X, Y, Z>>();
		for (Triple<X, Y, Z> triple : this.getFunction()) 
			if (triple.getY().equals(y))
				triples.add(triple);
		return triples;
	}

	@Override public boolean containsXYZ(X x, Y y, Z z) {
		for (Triple<X, Y, Z> triple : this.getFunction())
			if (triple.getX().equals(x)
					&& triple.getY().equals(y)
					&& triple.getZ().equals(z))
				return true;
		return false;
	}

	@Override public boolean containsXY(X x, Y y) {
		for (Triple<X, Y, Z> triple : this.getFunction())
			if (triple.getX().equals(x)
					&& triple.getY().equals(y))
				return true;
		return false;
	}

	@Override public boolean containsX(X x) {
		for (Triple<X, Y, Z> triple : this.getFunction())
			if (triple.getX().equals(x))
				return true;
		return false;
	}

	@Override public boolean containsY(Y y) {
		for (Triple<X, Y, Z> triple : this.getFunction())
			if (triple.getY().equals(y))
				return true;
		return false;
	}

	@Override public boolean containsZ(Z z) {
		for (Triple<X, Y, Z> triple : this.getFunction())
			if (triple.getZ().equals(z))
				return true;
		return false;
	}
	
	@Override public abstract String toFormattedFunction();
	
	@Override public String toString() {
		String string = "{";
		
		Iterator<Triple<X, Y, Z>> iter = this.getFunction().iterator();
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
		
		AbstractFunction<X, Y, Z> other = (AbstractFunction<X, Y, Z>) obj;
		
		return (this.getDomainX().equals(other.getDomainX())
				&& this.getDomainY().equals(other.getDomainY())
				&& this.getFunction().containsAll(other.getFunction())
				&& other.getFunction().containsAll(this.getFunction()));
	}
	
	@Override public int hashCode() {
		return Objects.hash(this.getDomainX(), this.getDomainY(), this.getFunction());
	}
	
}
