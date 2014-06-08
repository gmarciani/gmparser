package com.gmarciani.gmparser.models.automaton.state;

import java.util.Set;
import java.util.TreeSet;

public class StateId extends TreeSet<Integer> implements Comparable<StateId> {

	private static final long serialVersionUID = -8343356753287186117L;

	public StateId(Integer id) {
		super();
		this.add(id);
	}
	
	public StateId(Integer ... ids) {
		super();
		for (Integer id : ids)
			this.add(id);
	}
	
	public StateId(Set<StateId> ids) {
		super();
		for (StateId id : ids)
			this.addAll(id);
	}

	@Override public int compareTo(StateId other) {
		int byLength = Integer.valueOf(this.size()).compareTo(Integer.valueOf(other.size()));
		if (byLength == 0)
			for (Integer thisId : this)
				for (Integer otherId : other)
					if (thisId.compareTo(otherId) != 0)
						return thisId.compareTo(otherId);
		return byLength;
	}
	
	@Override public boolean equals(Object obj) {
		if (this.getClass() != obj.getClass())
			return false;
		
		StateId other = (StateId) obj;
		
		return (this.containsAll(other)
				&& other.containsAll(this));
	}

}
