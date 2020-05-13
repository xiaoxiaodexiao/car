package com.wlyy.bcwlw.netty.consts;

import com.wlyy.bcwlw.netty.entity.Connection;

import io.netty.util.AttributeKey;

/**
 * 
* @ClassName: Const 
* @Description: DTU常量
* @author 287598457@qq.com 
* @date 2016年2月29日 下午9:52:54 
*
 */
public class Const {
	
	/**
	 * 接收报文最短长度（无数据内容无校验）
	 */
	public static final int acceptMinMsgLength=17;
	
	/**
	 * 接收报文最大长度
	 */
	public static final int acceptMaxMsgLength=1043;
	/**
	 * 报文最短长度
	 */
	public static final int sendMinMsgLength=19;
	
	public static final int nextFieldLength = 1;
	
	/**
	 * 帧头字段长度
	 */
	public static final int headFieldLength = 1;
	
	/**
	 * 类型字段长度
	 */
	public static final int typeFieldLength = 1;
		
	/**
	 * 消息长度字段的长度
	 * 整个帧的长度
	 */
	public static final int msgFieldLength = 2;
	
	/**
	 * 身份字段长度
	 */
	public static final int identityFieldLength = 12;
		
	/**
	 * crc字段长度
	 */
	public static final int crcFieldLength = 2;
	
	/**
	 * 帧尾字段长度
	 */
	public static final int tailFieldLength = 1;
	
	//最大能忍受多长时间未收到心跳
	public static final int maxIdleTime = 420;
	
	//channel对应的连接
	public static final AttributeKey<Connection> connection = AttributeKey
            .valueOf("connection");
	
	//终端DTU请求注册
	public static final byte req01 = 0x01;
	
	//终端DTU请求注销
	public static final byte req02 = 0x02;
	
	//终端DTU自动发送的心跳包
	public static final byte req03 = 0x03;
	
	//接收到DSC数据中心的用户数据的应答包
	public static final byte req05 = 0x05;
	
	//发送给DSC数据中心的终端DTU数据包
	public static final byte req06 = 0x06;
	
	//保留字节
	public static final byte reserveCode = 0x00;
	
	
	//DTU请求类型
	public static final byte[] reqs = {req01,req02,req03,req05,req06};
	
	
	
	//服务器端非内容数据下传
	public static final byte resp80 = (byte)0x80;
	
	//注册成功
	public static final byte resp81 = (byte) 0x81;
	
	//注销成功
	public static final byte resp82 = (byte) 0x82;
	
	//接收到心跳包
	public static final byte resp83 = (byte) 0x83;
	
	//发送给DTU的用户数据包
	public static final byte resp85 = (byte) 0x85;
	
	//接收到DTU数据
	public static final byte resp86 = (byte) 0x86;
	
	//帧头类型
	public static final byte headDefault = 0x7B;
	
	//固定帧尾
	public static final byte tailDefault = 0x7B;
	
	//大棚控制类型
	
	//泵站阀门控制类型
	public static final byte downloadPumpVelanCommand = 0x01;
	
	//泵站电磁阀控制类型
	public static final byte downloadPumpValueCommand = 0x02;
	
	//田间阀门控制类型
	public static final byte downloadRadiotubesCommond = 0x01;
	
	public static final byte downloadQuerySwzCommon = 0x01;
	
	public static final byte QuerySwzIndex = 0x01;

	
}
