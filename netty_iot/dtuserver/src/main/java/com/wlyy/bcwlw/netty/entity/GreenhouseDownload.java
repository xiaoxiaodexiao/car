package com.wlyy.bcwlw.netty.entity;

import org.apache.commons.lang.ArrayUtils;

import com.wlyy.bcwlw.netty.consts.Const;
import com.wlyy.bcwlw.netty.utils.ByteUtil;

/**
 * 
* @ClassName: GreenhouseDownload 
* @Description: 大棚下传信息实体类
* @author 287598457@qq.com 
* @date 2016年3月1日 下午11:49:23 
*
 */
public class GreenhouseDownload {

	private byte[] radiotubeSwitchs;

	public GreenhouseDownload(String radiotubeSwitchs){
		this.radiotubeSwitchs =ByteUtil.strToBytes(radiotubeSwitchs);
	}
	
	public byte[] getClassToBytes(){
		//设备类型 固定为SPM，代表智能大棚系统
		//命令类型 固定为0x01
		byte[] res = new byte[]{0x53,0x50,0x4D,Const.downloadRadiotubesCommond};
		//电磁阀开关控制
		res = ArrayUtils.addAll(res, this.radiotubeSwitchs);
		return res;
	}
	
}
