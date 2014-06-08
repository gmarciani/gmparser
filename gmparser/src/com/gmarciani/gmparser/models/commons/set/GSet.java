package com.gmarciani.gmparser.models.commons.set;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentSkipListSet;

public class GSet<T> extends ConcurrentSkipListSet<T> {

	private static final long serialVersionUID = 5790342796025820425L;
	
	public GSet() {
		super();
	}
	
	@SafeVarargs
	public GSet(T ... members) {
		super();
		for (T member : members)
			this.add(member);
	}
	
	@SafeVarargs
	public GSet(T[] ... membersArray) {
		super();
		for (T[] members : membersArray)
			for (T member : members)
				this.add(member);
	}

	@SafeVarargs
	public GSet(Collection<T> ... sets) {
		super();
		for (Collection<T> set : sets)
			this.addAll(set);
	}
	
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
