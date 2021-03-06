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

package com.gmarciani.gmparser.models.commons.nple;

import java.util.Objects;

public class Triple<X extends Comparable<X>, Y extends Comparable<Y>, Z extends Comparable<Z>> 
	implements Comparable<Triple<X, Y, Z>> {
	
	private final X x;
	private final Y y;
	private final Z z;

	public Triple(X x, Y y, Z z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public X getX() {
		return this.x;
	}
	
	public Y getY() {
		return this.y;
	}
	
	public Z getZ() {
		return this.z;
	}
	
	@Override public String toString() {
		return "(" + String.valueOf(this.getX()) + "," + String.valueOf(this.getY()) + "," + String.valueOf(this.getZ()) + ")";
	}

	@Override public int compareTo(Triple<X, Y, Z> other) {
		int byX = this.getX().compareTo(other.getX());
		int byY = this.getY().compareTo(other.getY());
		int byZ = this.getZ().compareTo(other.getZ());		
		if (byX == 0 && byY == 0)
			return byZ;		
		if (byX == 0)
			return byY;		
		return byX;
	}
	
	@Override public boolean equals(Object obj) {
		if (this.getClass() != obj.getClass())
			return false;
		
		Triple<?, ?, ?> other = (Triple<?, ?, ?>) obj;
		
		return (this.getX().equals(other.getX())
				&& this.getY().equals(other.getY())
				&& this.getZ().equals(other.getZ()));
	}
	
	@Override public int hashCode() {
		return Objects.hash(this.getX(), this.getY(), this.getZ());
	}

}
