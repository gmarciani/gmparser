package com.gmarciani.gmparser.models.commons.set;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;

public class GSet<T> extends CopyOnWriteArraySet<T> {

	private static final long serialVersionUID = 5790342796025820425L;
	
	public GSet() {
		super();
	}
	
	public GSet(T member) {
		super();
		super.add(member);
	}
	
	public GSet(Collection<T> collection) {
		super(collection);
	}
	
	public boolean containsSome(Collection<T> collection) {
		for (T element : collection)
			if (this.contains(element))
				return true;
		return false;
	}
	
	public T get(T element) {
		for (T e : this)
			if (e.equals(element))
				return e;
		return null;
	}
	
	public T getFirst() {
		return new ArrayList<T>(this).get(0);
	}
	
	@Override public String toString() {
		String string = "{";
		
		Iterator<T> iter = this.iterator();
		while(iter.hasNext()) {
			string += String.valueOf(iter.next());
			if (iter.hasNext())
				string += ",";
		}
		
		string += "}";
		
		return string;
	}

}
