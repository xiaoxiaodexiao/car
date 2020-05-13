package com.wlyy.bcwlw.netty.entity;

import org.apache.commons.lang.ArrayUtils;

import com.wlyy.bcwlw.netty.consts.Const;
import com.wlyy.bcwlw.netty.utils.ByteUtil;

public class FieldValveDownload {
	/**
	 * 类型
	 */
	private byte type;
	
	 /**
     * 网络号
     */
    private byte netNum;

    /**
     * 序列号
     */
    private byte[] orderNum;
    
    private byte velan;
    
    public FieldValveDownload(){
    	this.type = (byte)0xF0;
    	this.netNum = (byte)0xFF;
    	this.orderNum = new byte[]{(byte)0xFF,(byte)0xFF};
    	this.velan = 0x00;
    }
    
    public FieldValveDownload(int netNum,short orderNum,int switch_one_status,int switch_two_status){
    	this.type = 0x03;
    	this.netNum = ByteUtil.intToByte(netNum);
    	this.orderNum = ByteUtil.shortToBytes(orderNum);
    	this.velan = ByteUtil.BitToByte("000000"+String.valueOf(switch_two_status)+String.valueOf(switch_one_status));
    }
    
    public byte[] FieldValveDownloadMsgByte(){
    	byte[] res = new byte[]{0x54,0x4A,0x47};
    	res = ArrayUtils.add(res, this.netNum);
    	res = ArrayUtils.addAll(res, this.orderNum);
    	res = ArrayUtils.add(res, this.type);
    	res = ArrayUtils.add(res, this.velan);
    	return res;
    }

}
