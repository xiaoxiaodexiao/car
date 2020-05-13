package com.wlyy.bcwlw.netty.entity;

/**
 * 
* @ClassName: UnderwaterUpload 
* @Description: 地下水上传实体类
* @author 287598457@qq.com 
* @date 2016年3月1日 下午11:52:06 
*
 */
public class UnderwaterUpload extends BaseData{
	/**
     * 设备id
     */
    private String devId;

    /**
     * 水位值
     */
    private short waterDepth;
    
	public UnderwaterUpload(byte status, String devId, short waterDepth) {
		super(status);
		this.devId = devId;
		this.waterDepth = waterDepth;
	}

	public String getDevId() {
		return devId;
	}

	public void setDevId(String devId) {
		this.devId = devId;
	}

	public short getWaterDepth() {
		return waterDepth;
	}

	public void setWaterDepth(short waterDepth) {
		this.waterDepth = waterDepth;
	}
    
    
}
