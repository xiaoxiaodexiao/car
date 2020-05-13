package com.wlyy.bcwlw.netty.handler.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.wlyy.bcwlw.netty.consts.Const;
import com.wlyy.bcwlw.netty.entity.Connection;
import com.wlyy.bcwlw.netty.handler.IHeartBeatHandler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;

public class DefaultHeartBeatHandler extends ChannelHandlerAdapter implements IHeartBeatHandler{
	
	private static final Logger logger = LogManager.getLogger("dtu_server");
	
	@Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		Connection conn = ctx.channel().attr(Const.connection).get();
		if(conn == null){
			return ;
		}
		try {
			if (evt instanceof IdleStateEvent) {
				IdleStateEvent e = (IdleStateEvent) evt;
				switch (e.state()) {
				case WRITER_IDLE:
					break;
				case READER_IDLE:
					//dto注销操作 TODO 调接口，传入设备编号
					ctx.channel().close();
					logger.debug("编号为"+conn.getIdentityId()+"的设备,心跳超时。");
					break;
				default:
					break;
				}
				super.userEventTriggered(ctx, evt);
			}
		} catch (Exception e) {
			throw e;
		}
    }

}
