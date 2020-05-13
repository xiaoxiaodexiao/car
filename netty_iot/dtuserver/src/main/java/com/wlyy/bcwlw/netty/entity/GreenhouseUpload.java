package com.wlyy.bcwlw.netty.entity;

/**
 * 
* @ClassName: GreenhouseUpload 
* @Description: 大棚上传信息实体类
* @author 287598457@qq.com 
* @date 2016年3月1日 下午11:49:48 
*
 */
public class GreenhouseUpload extends BaseData{
	
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

    /**
     * 一层空气温度
     */
    private short oneAirTemperature;

    /**
     * 一层空气湿度
     */
    private short oneAirHumidity;

    /**
     * 二层空气温度
     */
    private short twoAirTemperature;

    /**
     * 二层空气湿度
     */
    private short twoAirHumidity;

    /**
     * 二氧化碳
     */
    private int carbonDioxide;

    /**
     * 光照
     */
    private int illumination;

    /**
     * 土壤PH
     */
    private short soilPh;

    /**
     * 土壤EH
     */
    private short soilEh;
    
    /**
     * 电磁阀数目
     */
    private short radiotubeNum;
    
    /**
     * 电磁阀开关
     */
//    private String radiotubeSwitch;
    private byte[] radiotubeSwitch;
    
	public GreenhouseUpload(byte status, String devId, short oneSoilTemperature, short oneSoilHumidity, short twoSoilTemperature,
			short twoSoilHumidity, short threeSoilTemperature, short threeSoilHumidity, short fourSoilTemperature,
			short fourSoilHumidity, short oneAirTemperature, short oneAirHumidity, short twoAirTemperature,
			short twoAirHumidity, int carbonDioxide, int illumination, short soilPh, short soilEh, short radiotubeNum,
			byte[] radiotubeSwitch) {
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
		this.oneAirTemperature = oneAirTemperature;
		this.oneAirHumidity = oneAirHumidity;
		this.twoAirTemperature = twoAirTemperature;
		this.twoAirHumidity = twoAirHumidity;
		this.carbonDioxide = carbonDioxide;
		this.illumination = illumination;
		this.soilPh = soilPh;
		this.soilEh = soilEh;
		this.radiotubeNum = radiotubeNum;
		this.radiotubeSwitch = radiotubeSwitch;
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

	public short getOneAirTemperature() {
		return oneAirTemperature;
	}

	public void setOneAirTemperature(short oneAirTemperature) {
		this.oneAirTemperature = oneAirTemperature;
	}

	public short getOneAirHumidity() {
		return oneAirHumidity;
	}

	public void setOneAirHumidity(short oneAirHumidity) {
		this.oneAirHumidity = oneAirHumidity;
	}

	public short getTwoAirTemperature() {
		return twoAirTemperature;
	}

	public void setTwoAirTemperature(short twoAirTemperature) {
		this.twoAirTemperature = twoAirTemperature;
	}

	public short getTwoAirHumidity() {
		return twoAirHumidity;
	}

	public void setTwoAirHumidity(short twoAirHumidity) {
		this.twoAirHumidity = twoAirHumidity;
	}

	public int getCarbonDioxide() {
		return carbonDioxide;
	}

	public void setCarbonDioxide(int carbonDioxide) {
		this.carbonDioxide = carbonDioxide;
	}

	public int getIllumination() {
		return illumination;
	}

	public void setIllumination(int illumination) {
		this.illumination = illumination;
	}

	public short getSoilPh() {
		return soilPh;
	}

	public void setSoilPh(short soilPh) {
		this.soilPh = soilPh;
	}

	public short getSoilEh() {
		return soilEh;
	}

	public void setSoilEh(short soilEh) {
		this.soilEh = soilEh;
	}

	public short getRadiotubeNum() {
		return radiotubeNum;
	}

	public void setRadiotubeNum(short radiotubeNum) {
		this.radiotubeNum = radiotubeNum;
	}

	public byte[] getRadiotubeSwitch() {
		return radiotubeSwitch;
	}

	public void setRadiotubeSwitch(byte[] radiotubeSwitch) {
		this.radiotubeSwitch = radiotubeSwitch;
	}

//	public String getRadiotubeSwitch() {
//		return radiotubeSwitch;
//	}
//
//	public void setRadiotubeSwitch(String radiotubeSwitch) {
//		this.radiotubeSwitch = radiotubeSwitch;
//	}
    
    
}
