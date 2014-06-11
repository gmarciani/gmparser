package com.gmarciani.gmparser.models.parser.lr.action;

import java.util.Objects;

public class LROneAction implements Comparable<LROneAction>{
	
	private final LROneActionType actionType;
	private final Integer value;

	public LROneAction(LROneActionType actionType, Integer value) {
		this.actionType = actionType;
		this.value = value;
	}

	public LROneActionType getActionType() {
		return this.actionType;
	}

	public Integer getValue() {
		return this.value;
	}
	
	public boolean isActionType(LROneActionType actionType) {
		return this.getActionType() == actionType;
	}
	
	@Override public String toString() {
		return "("+ this.getActionType().getShortName() + ":" + this.getValue() + ")";
	}

	@Override public int compareTo(LROneAction other) {
		int byActionType = this.getActionType().getId().compareTo(other.getActionType().getId());
		int byValue = this.getValue().compareTo(other.getValue());
		if (byActionType == 0)
			return byValue;
		return byActionType;
	}
	
	@Override public boolean equals(Object obj) {
		if (this.getClass() != obj.getClass())
			return false;
		
		LROneAction other = (LROneAction) obj;
		
		return (this.getActionType().equals(other.getActionType())
				&& this.getValue().equals(other.getValue()));
	}
	
	@Override public int hashCode() {
		return Objects.hash(this.getActionType(), this.getValue());
	}
	
}
