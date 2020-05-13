package com.wlyy.bcwlw.netty.entity;

public class BaseData {
	private String type;
	
	private byte status;

	public BaseData(byte status) {
	super();
	this.status = status;
}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
