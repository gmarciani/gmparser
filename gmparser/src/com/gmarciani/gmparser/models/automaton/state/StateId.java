package com.gmarciani.gmparser.models.automaton.state;

import java.util.TreeSet;

public class StateId extends TreeSet<Integer> implements Comparable<StateId> {

	private static final long serialVersionUID = -8343356753287186117L;

	public StateId(Integer id) {
		super();
		this.add(id);
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

}
