package com.wlyy.bcwlw.netty.entity;

/**
 * 
* @ClassName: WeatherDownload 
* @Description: 气象上传实体类
* @author 287598457@qq.com 
* @date 2016年3月1日 下午11:52:34 
*
 */
public class WeatherUpload extends BaseData{
	/**
     * 设备id
     */
    private String devId;

    /**
     * 温度
     */
    private short temperature;

    /**
     * 湿度
     */
    private short humidity;

    /**
     * 大气压强
     */
    private short airPresure;

    /**
     * 风速
     */
    private short windSpeed;

    /**
     * 风向
     */
    private short windDirection;

    /**
     * 雨量
     */
    private short rainFall;

    /**
     * 光照度
     */
    private int illuminance;
    
	public WeatherUpload(byte status, String devId, short temperature, short humidity, short airPresure, short windSpeed,
			short windDirection, short rainFall, int illuminance) {
		super(status);
		this.devId = devId;
		this.temperature = temperature;
		this.humidity = humidity;
		this.airPresure = airPresure;
		this.windSpeed = windSpeed;
		this.windDirection = windDirection;
		this.rainFall = rainFall;
		this.illuminance = illuminance;
	}

	public String getDevId() {
		return devId;
	}

	public void setDevId(String devId) {
		this.devId = devId;
	}

	public short getTemperature() {
		return temperature;
	}

	public void setTemperature(short temperature) {
		this.temperature = temperature;
	}

	public short getHumidity() {
		return humidity;
	}

	public void setHumidity(short humidity) {
		this.humidity = humidity;
	}

	public short getAirPresure() {
		return airPresure;
	}

	public void setAirPresure(short airPresure) {
		this.airPresure = airPresure;
	}

	public short getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(short windSpeed) {
		this.windSpeed = windSpeed;
	}

	public short getWindDirection() {
		return windDirection;
	}

	public void setWindDirection(short windDirection) {
		this.windDirection = windDirection;
	}

	public short getRainFall() {
		return rainFall;
	}

	public void setRainFall(short rainFall) {
		this.rainFall = rainFall;
	}

	public int getIlluminance() {
		return illuminance;
	}

	public void setIlluminance(int illuminance) {
		this.illuminance = illuminance;
	}
    
    
}
