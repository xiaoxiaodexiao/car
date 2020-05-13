package com.wlyy.bcwlw.netty.entity;

/**
 * 
* @ClassName: DTUMsg 
* @Description: DTU通信帧Model 
* @author 287598457@qq.com  
* @date 2016年2月29日 下午8:11:06 
*
 */
/** 
* @ClassName: DTUMsg 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 287598457@qq.com 
* @date 2016年3月7日 下午8:45:05 
*  
*/
public class DTUMsg {

//	帧头
	private byte head;
	
//	帧类型
	private byte type;
	
//	帧长度
	private int length;
	
//	DTU身份ID
	private String identityId;
	
//	数据内容
//	private String data;
	private byte[] data;
	
//	CRC16校验和
	private byte[] crc16;
	
//	帧尾
	private byte tail;
	
	public DTUMsg(byte head, byte type, int length, String identityId, byte[] data, byte[] crc16, byte tail) {
		super();
		this.head = head;
		this.type = type;
		this.length = length;
		this.identityId = identityId;
		this.data = data;
		this.crc16 = crc16;
		this.tail = tail;
	}

	public byte getHead() {
		return head;
	}

	public void setHead(byte head) {
		this.head = head;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getIdentityId() {
		return identityId;
	}

	public void setIdentityId(String identityId) {
		this.identityId = identityId;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public byte[] getCrc16() {
		return crc16;
	}

	public void setCrc16(byte[] crc16) {
		this.crc16 = crc16;
	}

	public byte getTail() {
		return tail;
	}

	public void setTail(byte tail) {
		this.tail = tail;
	}


}
