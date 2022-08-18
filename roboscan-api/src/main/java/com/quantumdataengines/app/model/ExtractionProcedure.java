package com.quantumdataengines.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


public class ExtractionProcedure {
	
	private int groupId;
	private int procedureNumber;
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public int getProcedureNumber() {
		return procedureNumber;
	}
	public void setProcedureNumber(int procedureNumber) {
		this.procedureNumber = procedureNumber;
	}
	public String getProcedureName() {
		return procedureName;
	}
	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}
	public boolean isInParallel() {
		return inParallel;
	}
	public void setInParallel(boolean inParallel) {
		this.inParallel = inParallel;
	}
	public boolean isEnable() {
		return isEnable;
	}
	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}
	public ExtractionProcedure(int groupId, int procedureNumber, String procedureName, boolean inParallel,
			boolean isEnable) {
		super();
		this.groupId = groupId;
		this.procedureNumber = procedureNumber;
		this.procedureName = procedureName;
		this.inParallel = inParallel;
		this.isEnable = isEnable;
	}
	private String procedureName;
	private boolean inParallel;
	private boolean isEnable;

}
