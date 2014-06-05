package com.gmarciani.gmparser.models.commons;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentSkipListSet;

public class AdvancedSet<T> extends ConcurrentSkipListSet<T> {

	private static final long serialVersionUID = 5790342796025820425L;
	
	
	// CONSTRUCTION
	
	public AdvancedSet() {
		super();
	}
	
	@SafeVarargs
	public AdvancedSet(T ... members) {
		super();
		for (T member : members)
			this.add(member);
	}
	
	@SafeVarargs
	public AdvancedSet(T[] ... membersArray) {
		super();
		for (T[] members : membersArray)
			for (T member : members)
				this.add(member);
	}

	@SafeVarargs
	public AdvancedSet(Collection<T> ... sets) {
		super();
		for (Collection<T> set : sets)
			this.addAll(set);
	}
	
	
	// INSERTION
	
	public boolean addAll(@SuppressWarnings("unchecked") T ... members) {
		boolean added = false;
		for (T member : members)
			added = this.add(member) ? true : added;
		return added;
	}
	
	@Override public String toString() {
		String string = "{";
		
		Iterator<T> iter = this.iterator();
		while(iter.hasNext()) {
			string += iter.next().toString();
			if (iter.hasNext())
				string += ",";
		}
		
		string += "}";
		
		return string;
	}

}
