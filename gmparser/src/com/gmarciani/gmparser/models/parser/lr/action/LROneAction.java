package com.gmarciani.gmparser.models.parser.lr.action;

public class LROneAction {
	
	private LROneActionType actionType;
	private Integer value;

	public LROneAction() {
		// TODO Auto-generated constructor stub
	}

	public LROneActionType getActionType() {
		return this.actionType;
	}

	public void setActionType(LROneActionType actionType) {
		this.actionType = actionType;
	}

	public Integer getValue() {
		return this.value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
	
	public boolean isActionType(LROneActionType actionType) {
		return this.getActionType() == actionType;
	}

}
