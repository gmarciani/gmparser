package com.gmarciani.gmparser.models.commons.nple;

import java.util.Objects;

public class Pair<X extends Comparable<X>, Y extends Comparable<Y>> 
	implements Comparable<Pair<X, Y>>{

	private final X x;
	private final Y y;

	public Pair(X x, Y y) {
		this.x = x;
		this.y = y;
	}
	
	public X getX() {
		return this.x;
	}
	
	public Y getY() {
		return this.y;
	}
	
	@Override public String toString() {
		return "(" + this.getX() + "," + this.getY() + ")";
	}

	@Override public int compareTo(Pair<X, Y> other) {
		int byX = this.getX().compareTo(other.getX());
		int byY = this.getY().compareTo(other.getY());		
		if (byX == 0)
			return byY;		
		return byX;
	}
	
	@Override public boolean equals(Object obj) {
		if (this.getClass() != obj.getClass())
			return false;
		
		Pair<?, ?> other = (Pair<?, ?>) obj;
		
		return (this.getX().equals(other.getX())
				&& this.getY().equals(other.getY()));
	}
	
	@Override public int hashCode() {
		return Objects.hash(this.getX(), this.getY());
	}

}
