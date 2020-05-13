package com.wlyy.bcwlw.netty.entity;

public class FieldValveUpload extends BaseData{
	
	/**
     * 设备id
     */
    private String devId;
    
    /**
     * 保留字节
     */
    private byte reserveCode = 0x00;

    /**
     * 网络号
     */
    private int netNum;

    /**
     * 序列号
     */
    private short orderNum;

    /**
     * 开关1
     */
    private int switchOneStatus;

    /**
     * 开关2
     */
    private int switchTwoStatus;

    /**
     * 电压
     */
    private int batteryStatus;

    /**
     * 传感器
     */
    private int sensorStatus;

    /**
     * 流量1
     */
    private int flowOne;

    /**
     * 流量2
     */
    private int flowTwo;

    /**
     * 压力1
     */
    private int pressureOne;

    /**
     * 压力2
     */
    private int pressureTwo;
    
    
	public FieldValveUpload(byte status, String devId, byte netNum,short orderNum,String switchOneStatus,String switchTwoStatus,String batteryStatus,String sensorStatus,byte flowOne,byte flowTwo,byte pressureOne,byte pressureTwo) {
		super(status);
		this.devId = devId;
		this.netNum = netNum&0xff;
		this.orderNum = orderNum;
		this.switchOneStatus = Integer.parseInt(switchOneStatus);
		this.switchTwoStatus = Integer.parseInt(switchTwoStatus);
		this.batteryStatus = Integer.parseInt(batteryStatus);
		this.sensorStatus = Integer.parseInt(sensorStatus);
		this.flowOne = flowOne&0xff;
		this.flowTwo = flowTwo&0xff;
		this.pressureOne = pressureOne&0xff;
		this.pressureTwo = pressureTwo&0xff;
	}


	public String getDevId() {
		return devId;
	}


	public void setDevId(String devId) {
		this.devId = devId;
	}


	public byte getReserveCode() {
		return reserveCode;
	}


	public void setReserveCode(byte reserveCode) {
		this.reserveCode = reserveCode;
	}


	public int getNetNum() {
		return netNum;
	}


	public void setNetNum(int netNum) {
		this.netNum = netNum;
	}


	public short getOrderNum() {
		return orderNum;
	}


	public void setOrderNum(short orderNum) {
		this.orderNum = orderNum;
	}


	public int getSwitchOneStatus() {
		return switchOneStatus;
	}


	public void setSwitchOneStatus(int switchOneStatus) {
		this.switchOneStatus = switchOneStatus;
	}


	public int getSwitchTwoStatus() {
		return switchTwoStatus;
	}


	public void setSwitchTwoStatus(int switchTwoStatus) {
		this.switchTwoStatus = switchTwoStatus;
	}


	public int getBatteryStatus() {
		return batteryStatus;
	}


	public void setBatteryStatus(int batteryStatus) {
		this.batteryStatus = batteryStatus;
	}


	public int getSensorStatus() {
		return sensorStatus;
	}


	public void setSensorStatus(int sensorStatus) {
		this.sensorStatus = sensorStatus;
	}


	public int getFlowOne() {
		return flowOne;
	}


	public void setFlowOne(int flowOne) {
		this.flowOne = flowOne;
	}


	public int getFlowTwo() {
		return flowTwo;
	}


	public void setFlowTwo(int flowTwo) {
		this.flowTwo = flowTwo;
	}


	public int getPressureOne() {
		return pressureOne;
	}


	public void setPressureOne(int pressureOne) {
		this.pressureOne = pressureOne;
	}


	public int getPressureTwo() {
		return pressureTwo;
	}


	public void setPressureTwo(int pressureTwo) {
		this.pressureTwo = pressureTwo;
	}

	
	
	
}
