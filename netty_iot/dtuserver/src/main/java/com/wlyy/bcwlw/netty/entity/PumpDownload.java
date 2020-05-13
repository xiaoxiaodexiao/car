package com.wlyy.bcwlw.netty.entity;

import org.apache.commons.lang.ArrayUtils;

import com.wlyy.bcwlw.netty.consts.Const;
import com.wlyy.bcwlw.netty.utils.ByteUtil;

/**
 * 
* @ClassName: PumpDownUpload 
* @Description: 泵站下传实体类 
* @author 287598457@qq.com 
* @date 2016年3月1日 下午11:50:09 
*
 */
public class PumpDownload {
	
	private byte type;
	
	private byte velan;

//	private byte velan;
//	
//	private byte[] transducer;
	
	/**
	 * 默认构造为查询
	 * 
	 */
	public PumpDownload(){
		//0xF0代表查询
		this.type = (byte)0xF0;
		//查询时默认为0x00
		this.velan = 0x00;
	}
	
	//有参数为控制变频器
	public PumpDownload(int velan){
		//0x01控制变频器
		this.type = 0x01;
		if(1== velan){
			this.velan = 0x01;
		}else{
			this.velan = 0x00;
		}
	}
	
	public byte[] getPumpDownloadMsgByte(){
		byte[] res = new byte[]{0x53,0x57,0x4D};
		res = ArrayUtils.add(res, this.type);
		res = ArrayUtils.add(res, this.velan);
		return res;
	}
	
}
