package com.wlyy.bcwlw.netty.entity;

/**
 * 
* @ClassName: SoilUpload 
* @Description: 墒情上传实体类
* @author 287598457@qq.com 
* @date 2016年3月1日 下午11:51:50 
*
 */
public class SoilUpload extends BaseData{
	/**
     * 设备id
     */
    private String devId;

    /**
     * 一层土壤温度
     */
    private short oneSoilTemperature;

    /**
     * 一层土壤湿度
     */
    private short oneSoilHumidity;

    /**
     * 二层土壤温度
     */
    private short twoSoilTemperature;

    /**
     * 二层土壤湿度
     */
    private short twoSoilHumidity;

    /**
     * 三层土壤温度
     */
    private short threeSoilTemperature;

    /**
     * 三层土壤湿度
     */
    private short threeSoilHumidity;

    /**
     * 四层土壤温度
     */
    private short fourSoilTemperature;

    /**
     * 四层土壤湿度
     */
    private short fourSoilHumidity;
    

	public SoilUpload(byte status, String devId, short oneSoilTemperature, short oneSoilHumidity, short twoSoilTemperature,
			short twoSoilHumidity, short threeSoilTemperature, short threeSoilHumidity, short fourSoilTemperature,
			short fourSoilHumidity) {
		super(status);
		this.devId = devId;
		this.oneSoilTemperature = oneSoilTemperature;
		this.oneSoilHumidity = oneSoilHumidity;
		this.twoSoilTemperature = twoSoilTemperature;
		this.twoSoilHumidity = twoSoilHumidity;
		this.threeSoilTemperature = threeSoilTemperature;
		this.threeSoilHumidity = threeSoilHumidity;
		this.fourSoilTemperature = fourSoilTemperature;
		this.fourSoilHumidity = fourSoilHumidity;
	}

	public String getDevId() {
		return devId;
	}

	public void setDevId(String devId) {
		this.devId = devId;
	}

	public short getOneSoilTemperature() {
		return oneSoilTemperature;
	}

	public void setOneSoilTemperature(short oneSoilTemperature) {
		this.oneSoilTemperature = oneSoilTemperature;
	}

	public short getOneSoilHumidity() {
		return oneSoilHumidity;
	}

	public void setOneSoilHumidity(short oneSoilHumidity) {
		this.oneSoilHumidity = oneSoilHumidity;
	}

	public short getTwoSoilTemperature() {
		return twoSoilTemperature;
	}

	public void setTwoSoilTemperature(short twoSoilTemperature) {
		this.twoSoilTemperature = twoSoilTemperature;
	}

	public short getTwoSoilHumidity() {
		return twoSoilHumidity;
	}

	public void setTwoSoilHumidity(short twoSoilHumidity) {
		this.twoSoilHumidity = twoSoilHumidity;
	}

	public short getThreeSoilTemperature() {
		return threeSoilTemperature;
	}

	public void setThreeSoilTemperature(short threeSoilTemperature) {
		this.threeSoilTemperature = threeSoilTemperature;
	}

	public short getThreeSoilHumidity() {
		return threeSoilHumidity;
	}

	public void setThreeSoilHumidity(short threeSoilHumidity) {
		this.threeSoilHumidity = threeSoilHumidity;
	}

	public short getFourSoilTemperature() {
		return fourSoilTemperature;
	}

	public void setFourSoilTemperature(short fourSoilTemperature) {
		this.fourSoilTemperature = fourSoilTemperature;
	}

	public short getFourSoilHumidity() {
		return fourSoilHumidity;
	}

	public void setFourSoilHumidity(short fourSoilHumidity) {
		this.fourSoilHumidity = fourSoilHumidity;
	}
    
}
