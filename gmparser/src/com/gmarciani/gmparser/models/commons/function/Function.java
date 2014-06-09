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

import com.gmarciani.gmparser.models.commons.nple.Triple;
import com.gmarciani.gmparser.models.commons.set.GSet;

public interface Function<X extends Comparable<X>, 
						  Y extends Comparable<Y>, 
						  Z extends Comparable<Z>> {
	
	public GSet<X> getDomainX();	
	public GSet<Y> getDomainY();	
	public GSet<Z> getCodomain();
	
	public GSet<X> getUsedDomainX();
	public GSet<Y> getUsedDomainY();
	public GSet<Z> getUsedCodomain();
	
	public int getDomainXCardinality();
	public int getDomainYCardinality();
	public int getCodomainCardinality();
	
	public boolean add(X x, Y y, Z z);	
		
	public boolean removeAllForX(X x);	
	public boolean removeAllForY(Y y);
	public boolean removeAllForZ(Z z);
	public boolean removeAllForXY(X x, Y y);
	public boolean removeAllForXZ(X x, Z z);
	public boolean removeAllForYZ(Y y, Z z);
	public boolean removeXYZ(X x, Y y, Z z);
			
	public GSet<Triple<X, Y, Z>> getAllForX(X x);	
	public GSet<Triple<X, Y, Z>> getAllForY(Y y);
	public GSet<Triple<X, Y, Z>> getAllForZ(Z z);
	public GSet<Triple<X, Y, Z>> getAllForXY(X x, Y y);
	public GSet<Triple<X, Y, Z>> getAllForXZ(X x, Z z);
	public GSet<Triple<X, Y, Z>> getAllForYZ(Y y, Z z);
	public Triple<X, Y, Z> getXYZ(X x, Y y, Z z);
	
	public boolean containsX(X x);
	public boolean containsY(Y y);
	public boolean containsZ(Z z);
	public boolean containsXY(X x, Y y);
	public boolean containsXZ(X x, Z z);
	public boolean containsYZ(Y y, Z z);
	public boolean containsXYZ(X x, Y y, Z z);
	
	public boolean isDefined(X x, Y y, Z z);
	
	public String toFormattedFunction();
	
}
