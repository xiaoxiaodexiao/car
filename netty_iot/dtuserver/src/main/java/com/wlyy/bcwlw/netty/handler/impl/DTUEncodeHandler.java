package com.wlyy.bcwlw.netty.handler.impl;

import org.apache.commons.lang.ArrayUtils;

import com.wlyy.bcwlw.netty.consts.Const;
import com.wlyy.bcwlw.netty.entity.Connection;
import com.wlyy.bcwlw.netty.entity.DTUMsg;
import com.wlyy.bcwlw.netty.utils.ByteUtil;
import com.wlyy.bcwlw.netty.utils.CRC16Util;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class DTUEncodeHandler extends MessageToByteEncoder<Object>{

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
		byte[]resMsg = (byte[]) msg;
		//服务器端非内容数据下传
		if(resMsg[0] != Const.resp80){
			//包含crc校验的长度了吗
			int dtuMsgLength = resMsg.length + Const.sendMinMsgLength;
			//已建立链路限制
			Connection conn = ctx.channel().attr(Const.connection).get();
			String identityId = conn.getIdentityId();
			
			byte[] crc16 = CRC16Util.crc16Bytes(resMsg);
			DTUMsg dtuMsg = new DTUMsg(Const.headDefault, Const.resp85, dtuMsgLength, identityId, resMsg, crc16, Const.tailDefault);
			resMsg = buildMsg(dtuMsg);
		}else{
			resMsg =  ArrayUtils.remove(resMsg, 0);
		}
		out.writeBytes(resMsg);
	}

	private byte[] buildMsg(DTUMsg dtuMsg) {
		byte[] res = {};
		res = ArrayUtils.add(res, dtuMsg.getHead());
		res = ArrayUtils.add(res, dtuMsg.getType());
		//TODO length int to bytes ?? 
		//整个帧长度是否计算了
		res = ArrayUtils.addAll(res, ByteUtil.getBytes((short)dtuMsg.getLength(), true));
		res = ArrayUtils.addAll(res, dtuMsg.getIdentityId().getBytes());
		res = ArrayUtils.addAll(res, dtuMsg.getData());
		
		//crc16校验错误
		res = ArrayUtils.addAll(res, dtuMsg.getCrc16());
		res = ArrayUtils.add(res, dtuMsg.getTail());
		return res;
	}

}
