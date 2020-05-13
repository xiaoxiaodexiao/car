package com.wlyy.bcwlw.netty.entity;

/**
 * 
* @ClassName: PumpUpload 
* @Description: 泵站上传实体类 
* @author 287598457@qq.com 
* @date 2016年3月1日 下午11:50:30 
*
 */
public class PumpUpload extends BaseData{
	/**
     * 设备id
     */
    private String devId;

    /**
     * 运行状态
     */
    private String velanStatus;
    
    /**
     * 控制模式
     */
    private String controlStatus;
    
    /**
     * 故障码
     */
    private byte troubleCode;
    /**
     * 水压
     */
    private short presure;

    /**
     * 水流量
     */
    private short flow;

    /**
     * 地下水位
     */
    private short level;
    
    
    private byte reserveCode = 0x00;

    
    
	public PumpUpload(byte status, String devId, String velanStatus,String controlStatus,byte troubleCode, short presure, short flow, short level) {
		super(status);
		this.devId = devId;
		this.velanStatus = velanStatus;
		this.controlStatus = controlStatus;
		this.troubleCode = troubleCode;
		this.presure = presure;
		this.flow = flow;
		this.level = level;
	}

	public String getDevId() {
		return devId;
	}

	public void setDevId(String devId) {
		this.devId = devId;
	}


	public short getPresure() {
		return presure;
	}

	public void setPresure(short presure) {
		this.presure = presure;
	}

	public short getFlow() {
		return flow;
	}

	public void setFlow(short flow) {
		this.flow = flow;
	}

	public short getLevel() {
		return level;
	}

	public void setLevel(short level) {
		this.level = level;
	}

	public String getVelanStatus() {
		return velanStatus;
	}

	public void setVelanStatus(String velanStatus) {
		this.velanStatus = velanStatus;
	}

	public String getControlStatus() {
		return controlStatus;
	}

	public void setControlStatus(String controlStatus) {
		this.controlStatus = controlStatus;
	}

	public byte getTroubleCode() {
		return troubleCode;
	}

	public void setTroubleCode(byte troubleCode) {
		this.troubleCode = troubleCode;
	}

	public byte getReserveCode() {
		return reserveCode;
	}

	public void setReserveCode(byte reserveCode) {
		this.reserveCode = reserveCode;
	}
	
//	public String toString(){
//		return "设备编号为："+this.devId+" 状态为："+this.getStatus() +" 运行状态为："+this.velanStatus+" 控制状态为："+this.controlStatus+" 水压力为："+this.presure+" 水流量为："+this.flow+" 水位为："+this.level+"故障码："+this.troubleCode;
//		
//	}
    

}
