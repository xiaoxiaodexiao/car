package com.wlyy.bcwlw.netty.handler.impl;

import java.util.Arrays;

import org.apache.commons.lang.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.wlyy.bcwlw.netty.consts.Const;
import com.wlyy.bcwlw.netty.entity.BaseData;
import com.wlyy.bcwlw.netty.entity.Connection;
import com.wlyy.bcwlw.netty.entity.DTUMsg;
import com.wlyy.bcwlw.netty.entity.FieldValveUpload;
import com.wlyy.bcwlw.netty.entity.GreenhouseUpload;
import com.wlyy.bcwlw.netty.entity.PumpUpload;
import com.wlyy.bcwlw.netty.entity.SoilUpload;
import com.wlyy.bcwlw.netty.entity.UnderwaterUpload;
import com.wlyy.bcwlw.netty.entity.WeatherUpload;
import com.wlyy.bcwlw.netty.utils.ByteUtil;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class DTUServiceHandler extends ChannelHandlerAdapter{

	
	
	private static final Logger logger = LogManager.getLogger("dtu_server");
	
	//活跃的
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
	}

	//channel.close 后调用
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Connection conn = ctx.channel().attr(Const.connection).get();
		if (conn == null) {
			super.channelInactive(ctx);
			return;
		}
		
		if(logger.isDebugEnabled()){
			logger.debug("编号为"+conn.getIdentityId()+"的设备状态为------离线。");
		}
		//deviceExternalService.deviceLogOut(conn.getIdentityId());
		conn.logOut();
		super.channelInactive(ctx);
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.channel().close();
		super.exceptionCaught(ctx, cause);
	}
		
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		DTUMsg dtuMsg = (DTUMsg) msg;
		
		if (dtuMsg.getType() == 0) {
			return ;
		}
		
		switch(dtuMsg.getType()) {
			case Const.req01:{
				Connection connection = new Connection(ctx.channel(), dtuMsg.getIdentityId());
				handlerReq01(dtuMsg, connection);
				break;
			}
			case Const.req02:{
				Connection connection = new Connection(ctx.channel(), dtuMsg.getIdentityId());
				handlerReq02(dtuMsg, connection);
				break;
			}
			case Const.req03:{
				Connection connection = new Connection(ctx.channel(), dtuMsg.getIdentityId());
				handlerReq03(dtuMsg, connection);
				break;
			}
			case Const.req05:{
				handlerReq05(dtuMsg);
				break;
			}
			case Const.req06:{
				handlerReq06(dtuMsg);
				break;
			}
			default:
				break;
		}
		super.channelRead(ctx, msg);
	}

	/**
	 * 
	* @Title: handlerReq01 
	* @Description: 处理 终端DTU请求注册
	* @param @param dtuMsg    设定文件 
	* @return void    返回类型 
	* @throws.
	 */
	private void handlerReq01(DTUMsg dtuMsg, Connection connection) {
		// TODO 调用注册接口
		if(logger.isDebugEnabled()){
			logger.debug("编号为"+dtuMsg.getIdentityId()+"的设备，请求注册。");
		}
		
		byte[] res = new byte[]{Const.resp80,0x7B ,(byte) 0x81 ,0x00 ,0x11};
		res = ArrayUtils.addAll(res, dtuMsg.getIdentityId().getBytes());
		res = ArrayUtils.addAll(res, new byte[]{0x7B});
		connection.write(res);
		
	}
	
	/**
	 * 
	* @Title: handlerReq02 
	* @Description: 处理 终端DTU请求注销
	* @param @param dtuMsg    设定文件 
	* @return void    返回类型 
	* @throws.
	 */
	private void handlerReq02(DTUMsg dtuMsg, Connection conn) {
		// TODO 调用注销接口，并关闭连接
		if(logger.isDebugEnabled()){
			logger.debug("编号为"+dtuMsg.getIdentityId()+"的设备，请求注销。");
		}
		byte[] res = new byte[]{Const.resp80,0x7B ,(byte) 0x82 ,0x00 ,0x11};
		res = ArrayUtils.addAll(res, dtuMsg.getIdentityId().getBytes());
		res = ArrayUtils.addAll(res, new byte[]{0x7B});
		conn.write(res);
		
	}
	
	/**
	 * 
	* @Title: handlerReq03 
	* @Description: 处理 终端DTU自动发送的心跳包
	* @param @param dtuMsg    设定文件 
	* @return void    返回类型 
	* @throws.
	 */
	private void handlerReq03(DTUMsg dtuMsg, Connection conn) {
		if(conn.getChannel().attr(Const.connection).get() == null){
			if(logger.isDebugEnabled()){
				logger.debug("编号为"+dtuMsg.getIdentityId()+"的设备状态为------在线。");
			}
			conn.register(conn);
			//deviceExternalService.deviceLogin(dtuMsg.getIdentityId());
		}
		byte[] res = new byte[]{Const.resp80,0x7B,(byte)0x83,0x00,0x11};
		res=ArrayUtils.addAll(res, dtuMsg.getIdentityId().getBytes());
		res=ArrayUtils.addAll(res, new byte[]{0x7B});
		conn.write(res);
		if(logger.isDebugEnabled()){
			logger.debug("发送心跳包给-----编号为"+dtuMsg.getIdentityId()+"的设备。");
		}
	}
	
	/**
	 * 
	* @Title: handlerReq05 
	* @Description: 处理 接收到DSC数据中心的用户数据的应答包
	* @param @param dtuMsg    设定文件 
	* @return void    返回类型 
	* @throws.
	 */
	private void handlerReq05(DTUMsg dtuMsg) {
		if(logger.isDebugEnabled()){
			logger.debug("收到编号为"+dtuMsg.getIdentityId()+"的设备的应答数据包。");
		}
		byte[] dataA = dtuMsg.getData();
		byte[] typeA = Arrays.copyOfRange(dataA, 0, 3);
		String type = new String(typeA);
		BaseData data = null;
		if(type.equals("SWM")){
			data = parseSWM(dataA, dtuMsg.getIdentityId());
			data.setType(type);
		}else if(type.equals("TJG")){
			data = parseTJG(dataA, dtuMsg.getIdentityId());
			data.setType(type);
		}else if(type.equals("SWZ")){
			data =parseReplaySWZ(dataA, dtuMsg.getIdentityId());
			if(data != null){
				data.setType(type);
			}
		}
//		deviceExternalService.receiveDeviceMsg(data);
	}
	
	/**
	 * 
	* @Title: handlerReq06 
	* @Description: 处理 发送给DSC数据中心的终端DTU数据包
	* @param @param dtuMsg    设定文件 
	* @return void    返回类型 
	* @throws.
	 */
	private void handlerReq06(DTUMsg dtuMsg) {
		if(Connection.getConnection(dtuMsg.getIdentityId()) == null){
			logger.error("编号为"+dtuMsg.getIdentityId()+"的设备，并未在线，其数据包不做处理。");
			return ;
		}
		if(logger.isDebugEnabled()){
			logger.debug("收到编号为"+dtuMsg.getIdentityId()+"的设备的数据包。");
		}
		byte[] dataB = dtuMsg.getData();
		byte[] typeB = Arrays.copyOfRange(dataB, 0, 3);
		String type = new String(typeB);
		BaseData data = null;
		if (type.equals("SPM")) {
			data = parseSPM(dataB, dtuMsg.getIdentityId());
			data.setType(type);
		} else if (type.equals("SWM")) {
			data = parseSWM(dataB, dtuMsg.getIdentityId());
			data.setType(type);
		} else if (type.equals("QXZ")) {
			data = parseQXZ(dataB, dtuMsg.getIdentityId());
			data.setType(type);
		} else if (type.equals("SQZ")) {
			data = parseSQZ(dataB, dtuMsg.getIdentityId());
			data.setType(type);
		} else if (type.equals("SWZ")) {
			data = parseSWZ(dataB, dtuMsg.getIdentityId());
			data.setType(type);
		} else if (type.equals("TJG")) {
			data = parseTJG(dataB, dtuMsg.getIdentityId());
			data.setType(type);
		}
		//deviceExternalService.receiveDeviceMsg(data);
		logger.debug("业务已处理，设备类型为:"+type+" 设备编号为："+dtuMsg.getIdentityId());
	}

	private BaseData parseSPM(byte[] dataB, String identityId) {
		int nowIndex = 3;
		byte[] devIdB = Arrays.copyOfRange(dataB, nowIndex, nowIndex + 1);
		nowIndex += 1;
	
		byte status  = dataB[nowIndex];
		nowIndex += 1;
		
		byte[] oneSoilTemperatureB = Arrays.copyOfRange(dataB, nowIndex, nowIndex + 2);
		Short oneSoilTemperature = ByteUtil.bytesToShort(oneSoilTemperatureB);
//		oneSoilTemperature = (short) (oneSoilTemperature / 10.0);
		nowIndex += 2;
		
		byte[] oneSoilHumidityB = Arrays.copyOfRange(dataB, nowIndex, nowIndex + 2);
		Short oneSoilHumidity = ByteUtil.bytesToShort(oneSoilHumidityB);
//		oneSoilTemperature = (short) (oneSoilHumidity / 10.0);
		nowIndex += 2;
		
		byte[] twoSoilTemperatureB = Arrays.copyOfRange(dataB, nowIndex, nowIndex + 2);
		Short twoSoilTemperature = ByteUtil.bytesToShort(twoSoilTemperatureB);
//		twoSoilTemperature = (short) (twoSoilTemperature / 10.0);
		nowIndex += 2;
		
		byte[] twoSoilHumidityB = Arrays.copyOfRange(dataB, nowIndex, nowIndex + 2);
		Short twoSoilHumidity = ByteUtil.bytesToShort(twoSoilHumidityB);
//		twoSoilHumidity = (short) (twoSoilHumidity / 10.0);
		nowIndex += 2;
		
		byte[] threeSoilTemperatureB = Arrays.copyOfRange(dataB, nowIndex, nowIndex + 2);
		Short threeSoilTemperature = ByteUtil.bytesToShort(threeSoilTemperatureB);
//		threeSoilTemperature = (short) (threeSoilTemperature / 10.0);
		nowIndex += 2;
		
		byte[] threeSoilHumidityB = Arrays.copyOfRange(dataB, nowIndex, nowIndex + 2);
		Short threeSoilHumidity = ByteUtil.bytesToShort(threeSoilHumidityB);
//		threeSoilHumidity = (short) (threeSoilHumidity / 10.0);
		nowIndex += 2;
		
		byte[] fourSoilTemperatureB = Arrays.copyOfRange(dataB, nowIndex, nowIndex + 2);
		Short fourSoilTemperature = ByteUtil.bytesToShort(fourSoilTemperatureB);
//		fourSoilTemperature = (short) (fourSoilTemperature / 10.0);
		nowIndex += 2;
		
		byte[] fourSoilHumidityB = Arrays.copyOfRange(dataB, nowIndex, nowIndex + 2);
		Short fourSoilHumidity = ByteUtil.bytesToShort(fourSoilHumidityB);
//		fourSoilHumidity = (short) (fourSoilHumidity / 10.0);
		nowIndex += 2;
		
		byte[] oneAirTemperatureB = Arrays.copyOfRange(dataB, nowIndex, nowIndex + 2);
		Short oneAirTemperature = ByteUtil.bytesToShort(oneAirTemperatureB);
//		oneAirTemperature = (short) (oneAirTemperature / 10.0);
		nowIndex += 2;
		
		byte[] oneAirHumidityB = Arrays.copyOfRange(dataB, nowIndex, nowIndex + 2);
		Short oneAirHumidity = ByteUtil.bytesToShort(oneAirHumidityB);
//		oneAirHumidity = (short) (oneAirHumidity / 10.0);
		nowIndex += 2;
		
		byte[] twoAirTemperatureB = Arrays.copyOfRange(dataB, nowIndex, nowIndex + 2);
		Short twoAirTemperature = ByteUtil.bytesToShort(twoAirTemperatureB);
//		twoAirTemperature = (short) (twoAirTemperature / 10.0);
		nowIndex += 2;
		
		byte[] twoAirHumidityB = Arrays.copyOfRange(dataB, nowIndex, nowIndex + 2);
		Short twoAirHumidity = ByteUtil.bytesToShort(twoAirHumidityB);
//		twoAirHumidity = (short) (twoAirHumidity / 10.0);
		nowIndex += 2;
		
		byte[] carbonDioxideB = Arrays.copyOfRange(dataB, nowIndex, nowIndex + 4);
		int carbonDioxide = ByteUtil.bytesToInt(carbonDioxideB);
		nowIndex += 4;
		
		byte[] illuminationB = Arrays.copyOfRange(dataB, nowIndex, nowIndex + 4);
		int illumination = ByteUtil.bytesToInt(illuminationB);
		nowIndex += 4;
		
		byte[] soilPhB = Arrays.copyOfRange(dataB, nowIndex, nowIndex + 2);
		short soilPh = ByteUtil.bytesToShort(soilPhB);
		nowIndex += 2;
		
		byte[] soilEhB = Arrays.copyOfRange(dataB, nowIndex, nowIndex + 2);
		short soilEh = ByteUtil.bytesToShort(soilEhB);
		nowIndex += 2;
		
		byte[] radiotubeNumB = Arrays.copyOfRange(dataB, nowIndex, nowIndex + 2);
		short radiotubeNum = ByteUtil.bytesToShort(radiotubeNumB);
		nowIndex += 2;
		
		int bytesNum4Radiotube = radiotubeNum / 8;
		byte[] radiotubeSwitch = {};
		while (bytesNum4Radiotube != 0) {
			bytesNum4Radiotube--;
			byte[] radiotubeB = Arrays.copyOfRange(dataB, nowIndex, nowIndex + 1);
			nowIndex += 1;
			radiotubeSwitch = ArrayUtils.addAll(radiotubeSwitch, radiotubeB);
		}
		int lastSome = bytesNum4Radiotube % 8;
		if (lastSome != 0) {
			byte[] radiotubeB = Arrays.copyOfRange(dataB, nowIndex, nowIndex + 1);
			nowIndex += 1;
			radiotubeSwitch = ArrayUtils.addAll(radiotubeSwitch, radiotubeB);
		}
		
		GreenhouseUpload greenhouseUpload = 
				new GreenhouseUpload(status, identityId, oneSoilTemperature, 
						oneSoilHumidity, twoSoilTemperature, 
						twoSoilHumidity, threeSoilTemperature, 
						threeSoilHumidity, fourSoilTemperature, 
						fourSoilHumidity, oneAirTemperature, 
						oneAirHumidity, twoAirTemperature, 
						twoAirHumidity, carbonDioxide, 
						illumination, soilPh, soilEh, 
						radiotubeNum, radiotubeSwitch);
		return greenhouseUpload;
	}
	
	private BaseData parseSWM(byte[] dataB, String identityId) {
		int nowIndex = 3;
		
		byte status = 0x01;
		//运行状态
		byte operationStatus = dataB[nowIndex];
		nowIndex += 1;
		String operationStatusStr=ByteUtil.byteToBit(operationStatus);
		String velanStatus = String.valueOf(operationStatusStr.charAt(0));
		String controlStatus = String.valueOf(operationStatusStr.charAt(1));
		//故障码
		byte troubleCode  = dataB[nowIndex];
		nowIndex += 1;
		if(troubleCode != 0x00){
			status = 0x01;
		}
		//水压力
		byte[] presureB = Arrays.copyOfRange(dataB, nowIndex, nowIndex + 2);
		Short presure = ByteUtil.bytesToShort(presureB);
//		presure = (short) (presure / 100);
		nowIndex += 2;
		
		byte[] flowB = Arrays.copyOfRange(dataB, nowIndex, nowIndex + 2);
		Short flow = ByteUtil.bytesToShort(flowB);
//		flow = (short) (flow / 100);
		nowIndex += 2;
		
		byte[] levelB = Arrays.copyOfRange(dataB, nowIndex, nowIndex + 2);
		Short level = ByteUtil.bytesToShort(levelB);
//		level = (short) (level / 100);
		nowIndex += 2;
		//保留字节
		byte reserveCode = dataB[nowIndex];
		nowIndex += 1;
		System.err.println(reserveCode);
		if(reserveCode == Const.reserveCode){
			//System.err.println(reserveCode);
		}
		
		PumpUpload pumpUpload = new PumpUpload(status, identityId, 
				velanStatus, controlStatus,troubleCode, 
				presure, flow, level);
		
		logger.debug(pumpUpload.toString());
		return pumpUpload;
	}
	
	private BaseData parseQXZ(byte[] dataB, String identityId) {
		int nowIndex = 3;
		byte[] devIdB = Arrays.copyOfRange(dataB, nowIndex, nowIndex + 1);
		nowIndex += 1;
		
		byte status  = dataB[nowIndex];
		nowIndex += 1;
		
		byte[] temperatureB = Arrays.copyOfRange(dataB, nowIndex, nowIndex + 2);
		Short temperature = ByteUtil.bytesToShort(temperatureB);
//		temperature = (short) (temperature / 10.0);
		nowIndex += 2;
		
		byte[] humidityB = Arrays.copyOfRange(dataB, nowIndex, nowIndex + 2);
		Short humidity = ByteUtil.bytesToShort(humidityB);
//		humidity = (short) (humidity / 10.0);
		nowIndex += 2;
		
		byte[] airPresureB = Arrays.copyOfRange(dataB, nowIndex, nowIndex + 2);
		Short airPresure = ByteUtil.bytesToShort(airPresureB);
//		airPresure = (short) (airPresure / 100.0);
		nowIndex += 2;
		
		byte[] windSpeedB = Arrays.copyOfRange(dataB, nowIndex, nowIndex + 2);
		Short windSpeed = ByteUtil.bytesToShort(windSpeedB);
//		windSpeed = (short) (windSpeed / 10.0);
		nowIndex += 2;
		
		byte[] windDirectionB = Arrays.copyOfRange(dataB, nowIndex, nowIndex + 2);
		Short windDirection = ByteUtil.bytesToShort(windDirectionB);
		nowIndex += 2;
		
		byte[] rainFallB = Arrays.copyOfRange(dataB, nowIndex, nowIndex + 2);
		Short rainFall = ByteUtil.bytesToShort(rainFallB);
//		rainFall = (short) (rainFall / 10.0);
		nowIndex += 2;
		
		byte[] illuminanceB = Arrays.copyOfRange(dataB, nowIndex, nowIndex + 4);
		int illuminance = ByteUtil.bytesToInt(illuminanceB);
		nowIndex += 4;
		
		WeatherUpload weatherUpload = new WeatherUpload(status, identityId,
				temperature, humidity, 
				airPresure, windSpeed, 
				windDirection, rainFall, illuminance);
		return weatherUpload;
	}
	
	private BaseData parseSQZ(byte[] dataB, String identityId) {
		int nowIndex = 3;
		byte[] devIdB = Arrays.copyOfRange(dataB, nowIndex, nowIndex + 1);
		nowIndex += 1;
		
		byte status  = dataB[nowIndex];
		nowIndex += 1;
		
		byte[] oneSoilTemperatureB = Arrays.copyOfRange(dataB, nowIndex, nowIndex + 2);
		Short oneSoilTemperature = ByteUtil.bytesToShort(oneSoilTemperatureB);
//		oneSoilTemperature = (short) (oneSoilTemperature / 10.0);
		nowIndex += 2;
		
		byte[] oneSoilHumidityB = Arrays.copyOfRange(dataB, nowIndex, nowIndex + 2);
		Short oneSoilHumidity = ByteUtil.bytesToShort(oneSoilHumidityB);
//		oneSoilTemperature = (short) (oneSoilHumidity / 10.0);
		nowIndex += 2;
		
		byte[] twoSoilTemperatureB = Arrays.copyOfRange(dataB, nowIndex, nowIndex + 2);
		Short twoSoilTemperature = ByteUtil.bytesToShort(twoSoilTemperatureB);
//		twoSoilTemperature = (short) (twoSoilTemperature / 10.0);
		nowIndex += 2;
		
		byte[] twoSoilHumidityB = Arrays.copyOfRange(dataB, nowIndex, nowIndex + 2);
		Short twoSoilHumidity = ByteUtil.bytesToShort(twoSoilHumidityB);
//		twoSoilHumidity = (short) (twoSoilHumidity / 10.0);
		nowIndex += 2;
		
		byte[] threeSoilTemperatureB = Arrays.copyOfRange(dataB, nowIndex, nowIndex + 2);
		Short threeSoilTemperature = ByteUtil.bytesToShort(threeSoilTemperatureB);
//		threeSoilTemperature = (short) (threeSoilTemperature / 10.0);
		nowIndex += 2;
		
		byte[] threeSoilHumidityB = Arrays.copyOfRange(dataB, nowIndex, nowIndex + 2);
		Short threeSoilHumidity = ByteUtil.bytesToShort(threeSoilHumidityB);
//		threeSoilHumidity = (short) (threeSoilHumidity / 10.0);
		nowIndex += 2;
		
		byte[] fourSoilTemperatureB = Arrays.copyOfRange(dataB, nowIndex, nowIndex + 2);
		Short fourSoilTemperature = ByteUtil.bytesToShort(fourSoilTemperatureB);
//		fourSoilTemperature = (short) (fourSoilTemperature / 10.0);
		nowIndex += 2;
		
		byte[] fourSoilHumidityB = Arrays.copyOfRange(dataB, nowIndex, nowIndex + 2);
		Short fourSoilHumidity = ByteUtil.bytesToShort(fourSoilHumidityB);
//		fourSoilHumidity = (short) (fourSoilHumidity / 10.0);
		nowIndex += 2;
		
		SoilUpload soilUpload = new SoilUpload(status, identityId, 
				oneSoilTemperature, oneSoilHumidity, 
				twoSoilTemperature, twoSoilHumidity, 
				threeSoilTemperature, threeSoilHumidity, 
				fourSoilTemperature, fourSoilHumidity);
		return soilUpload;
	}

	private BaseData parseSWZ(byte[] dataB, String identityId) {
		int nowIndex = 3;
		byte[] devIdB = Arrays.copyOfRange(dataB, nowIndex, nowIndex + 1);
		nowIndex += 1;
		
		byte status  = dataB[nowIndex];
		nowIndex += 1;
		
		byte[] waterDepthB = Arrays.copyOfRange(dataB, nowIndex, nowIndex + 2);
		Short waterDepth = ByteUtil.bytesToShort(waterDepthB);
//		waterDepth = (short) (waterDepth / 100);
		nowIndex += 2;
		
		UnderwaterUpload underwaterUpload = new UnderwaterUpload(status, identityId, waterDepth);
		return underwaterUpload;
	}
	
	private BaseData parseTJG(byte[] dataB, String identityId) {
		int nowIndex = 3;
		//保留字节
		byte reserveCode = dataB[nowIndex];
		nowIndex += 1;
		
		byte status = 0x01;
		//网络号
		byte netNum = dataB[nowIndex];
		nowIndex += 1;
		
		//序列号
		byte[] orderNumB = Arrays.copyOfRange(dataB, nowIndex, nowIndex + 2);
		Short orderNum = ByteUtil.bytesToShort(orderNumB);
		nowIndex += 2;
		
		byte operationStatus = dataB[nowIndex];
		nowIndex += 1;
		String operationStatusStr=ByteUtil.byteToBit(operationStatus);
		String switchOneStatus = String.valueOf(operationStatusStr.charAt(0));
		String switchTwoStatus = String.valueOf(operationStatusStr.charAt(1));
		String batteryStatus = String.valueOf(operationStatusStr.charAt(4));
		String sensorStatus = String.valueOf(operationStatusStr.charAt(5));
		
		if("1".equals(batteryStatus) || "1".equals(sensorStatus)){
			status = 0x00;
		}
		
		byte flowOne = dataB[nowIndex];
		nowIndex += 1;
		
		byte flowTwo = dataB[nowIndex];
		nowIndex += 1;
		
		byte pressureOne = dataB[nowIndex];
		nowIndex += 1;
		
		byte pressureTwo = dataB[nowIndex];
		nowIndex += 1;
		
		
		FieldValveUpload fieldValveUpload = new FieldValveUpload(status, identityId,netNum,orderNum,
				switchOneStatus,switchTwoStatus,batteryStatus,sensorStatus,flowOne,flowTwo,pressureOne,pressureTwo);
		logger.debug(fieldValveUpload.toString());
		return fieldValveUpload;
	}
	
	public void parseReplaySWM(byte[] dataA,String identityId){
		int nowIndex = 3;
		byte commond = dataA[nowIndex];
		nowIndex += 1;
		if(commond == Const.downloadPumpVelanCommand){
			byte velan = dataA[nowIndex];
			System.err.println(velan&0xff);
			//deviceExternalService.receiveSWMVelanReplyMsg(velan&0xff,null,identityId);
		}else if(commond == Const.downloadPumpValueCommand){
			byte[] value = Arrays.copyOfRange(dataA, nowIndex, nowIndex+2);
			Short transducer = ByteUtil.bytesToShort(value);
			System.err.println(transducer);
			//deviceExternalService.receiveSWMVelanReplyMsg(null,NumberUtil.valueof(transducer),identityId);
			
		}
	}
	
	
	public BaseData parseReplaySWZ(byte[] dataA,String identityId){
		int nowIndex = 3;
		byte commond = dataA[nowIndex];
		nowIndex += 1;
		byte queryIndex = dataA[nowIndex];
		nowIndex += 1;
		if(commond == Const.downloadQuerySwzCommon && queryIndex == Const.QuerySwzIndex){
			byte[] waterDepthB = Arrays.copyOfRange(dataA, nowIndex, nowIndex + 2);
			Short waterDepth = ByteUtil.bytesToShort(waterDepthB);
			nowIndex = +2;
			UnderwaterUpload underwaterUpload = new UnderwaterUpload((byte)0x01, identityId, waterDepth);
			return underwaterUpload;
		}
		return null;
	}
	
}
