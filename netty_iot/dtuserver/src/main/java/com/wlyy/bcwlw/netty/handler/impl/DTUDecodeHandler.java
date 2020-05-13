package com.wlyy.bcwlw.netty.handler.impl;

import java.nio.ByteOrder;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.wlyy.bcwlw.netty.consts.Const;
import com.wlyy.bcwlw.netty.entity.DTUMsg;
import com.wlyy.bcwlw.netty.utils.ByteUtil;
import com.wlyy.bcwlw.netty.utils.CRC16Util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * 
* @ClassName: DTUDecodeHandler 
* @Description: 解码handler
* @author 287598457@qq.com 
* @date 2016年2月29日 下午11:18:20 
*
 */
public class DTUDecodeHandler extends ByteToMessageDecoder{

	private static final Logger logger = LogManager.getLogger("dtu_server");
	
	/** 剩余长度*/
	private int remainLength =0;
	/** 已读长度*/
	private int hasReadLength =0;
	
	/**
	 *bytebuf 是byte数组的缓冲区
	 *基本功能：
	 *7种java基础类型、byte数组，bytebuffer的读写
	 *缓冲区自身的copy和slice等
	 *设置网络字节序
	 *构造缓冲区实例
	 *操作位置指针等方法 
	*  ByteBuf维护了两个指针,一个用于读取(readerIndex),一个用于写入(writerIndex).
	 */
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		//如果小于报文最小长度，则放弃*********
       // in.clear();
		/*缓冲区中的可读字节数*/
		int readableLength = in.readableBytes();
		
		if (readableLength < Const.acceptMinMsgLength) {  
	        return;  
	    }

		remainLength = readableLength;
		/**
		 * 处理DecoderException异常 
		 * 原因在indexof 是根据 in.getByte(Index)读的，并不是以readIndex 做参考
		 * 这样如果处理上次半包时 获取的headIndex 还是上次信息的索引
		 * 导致新发送的信息未被处理 所以netty抛出异常
		 * ByteBuf维护了两个指针,一个用于读取(readerIndex)
		 *
		 * readerIndex会根据读取字节数向后移动
		 */
		/*在读取前标记readerIndex*/
		int markLength =in.readerIndex();
		readableLength = readableLength+markLength;
		hasReadLength=markLength;
		/**
		 * 导致死循环的原因是Netty的IO线程一直会处理到编解码、Pipeline，业务可以在上面定制各种ChannelHandler，如果业务的异常最终抛到最外层，Netty捕获后继续处理，如果再次进入业务异常，再次抛出捕获，在这种场景下可能会导致死循环；
			另外，如果Channel处理不当，也会导致死循环，这个跟NIO的多路复用器相关，例如如果一个损坏的Channel没有被注销和关闭，它就会不断被多路复用器选择就绪，这样就会导致死循环；
		 */
		while (checkByteBuf(in,readableLength)){
			
			byte head = getHeadField(in);
			byte type = getTypeField(in);
			int msgLength = getMsgLengthField(in);
			String identity = getIdentityField(in);
			//请求和注销及心跳是没有数据内容和校验的 无数据内容无校验
			byte[] data ={};
			byte[] crc={};
			if(msgLength != Const.acceptMinMsgLength){
				data = getDataField(in, msgLength);
				crc = getCrcField(in,data);
				if(crc == null){
					return;
				}
			}
			byte tail = getTailField(in);
			DTUMsg dtuMsg = new DTUMsg(head, type, msgLength, identity, data, crc, tail);
			out.add(dtuMsg);
		}
	}
	
	
	/**
	 * 
	* @Title. checkByteBuf
	* @Description. 处理半包和粘包
	* @param in
	* @param readableLength
	* @return boolean
	* @exception.
	 */
	private boolean checkByteBuf(ByteBuf in,int readableLength){
		if(remainLength < Const.acceptMinMsgLength){
			return false;
		}
		/*是指在可读区域(即readerIndex与writerIndex之间部分)内的指定长度范围检索特定值，
		返回为找到的第一个值的index值，否则返回-1.*/
		int headIndex = in.indexOf(hasReadLength, readableLength, Const.headDefault);
		if(headIndex <0){
			return false;
		}
		in.readerIndex(headIndex);
		byte head = getHeadField(in);
		byte type =getTypeField(in);
		while(type == Const.headDefault){
			headIndex = headIndex+1;
			type = getTypeField(in);
		}
		if(!ArrayUtils.contains(Const.reqs, type)){
			logger.error("类型不正确");
			hasReadLength =headIndex+1;
			return checkByteBuf(in,readableLength);
		}
		int msgLength = getMsgLengthField(in);
		int needLength = msgLength+headIndex;
		if(needLength>readableLength){
			return false;
		}
		byte tail = in.getByte(needLength-1);
		if(tail != Const.tailDefault){
			logger.error("帧尾不正确");
			hasReadLength =headIndex+1;
			return checkByteBuf(in,readableLength);
		}
		in.readerIndex(headIndex);
		remainLength = readableLength - needLength;
		hasReadLength =needLength ;
		
		return true;

	}
	/*真头*/
	private byte getHeadField(ByteBuf in) {
		//从readerIndex开始获取字节值，readerIndex增加1,从给定字段处读取
		ByteBuf headbuf = in.readBytes(Const.headFieldLength);
		//得到一个字节在指定绝对指数在这个缓冲区。这种方法不修改readerIndex或writerIndex的缓冲区。
		return headbuf.getByte(0);
	}
	/*帧类型长度为1*/
	private byte getTypeField(ByteBuf in) {

		ByteBuf buf = in.readBytes(Const.typeFieldLength);
		//这种方法不会移动readerIndex指针,获取数据中的第几个值
		return buf.getByte(0);
	}
	/*帧长度为1*/
	private int getMsgLengthField(ByteBuf in) {
		ByteBuf buf = in.readBytes(Const.msgFieldLength);
		//设置网络字节序
		buf.order(ByteOrder.BIG_ENDIAN);
		return buf.getShort(0);
	}
	
	/*身份长度为12*/
	private String getIdentityField(ByteBuf in) {
		ByteBuf buf = in.readBytes(Const.identityFieldLength);
		//获取数组
		//返回这个缓冲区的字节数组
		byte[] res = buf.array();
		//直接转换不行吗
		return new String(res);
	}
	
	/*数据内容*/
	private byte[] getDataField(ByteBuf in, int msgLengthTotal) {
		//消息实际长度
		int dataLength = msgLengthTotal - Const.headFieldLength - Const.typeFieldLength - Const.msgFieldLength - Const.identityFieldLength - Const.crcFieldLength - Const.tailFieldLength;
		ByteBuf buf = in.readBytes(dataLength);
		byte[] res = buf.array();
		return res;
	}
	/*CRC校验和*/
	private byte[] getCrcField(ByteBuf in,byte[] data) {
		ByteBuf buf = in.readBytes(Const.crcFieldLength);
		buf.order(ByteOrder.BIG_ENDIAN);
		//crc校验
		if(data.length !=0 && CRC16Util.crc16(data)==ByteUtil.bytesToInt(buf.array())){
			return buf.array();
		}
		return null;
	}
	/*帧尾*/
	private byte getTailField(ByteBuf in) {
		ByteBuf buf = in.readBytes(Const.tailFieldLength);
		return buf.getByte(0);
	}
	

	private void resetBytebufReadIndex(ByteBuf bytebuf,int readIndex) {
		bytebuf.readerIndex(readIndex);
		
	}
	
}
