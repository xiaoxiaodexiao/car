package com.wlyy.bcwlw.netty.server;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.wlyy.bcwlw.netty.consts.Const;
import com.wlyy.bcwlw.netty.handler.impl.DTUDecodeHandler;
import com.wlyy.bcwlw.netty.handler.impl.DTUEncodeHandler;
import com.wlyy.bcwlw.netty.handler.impl.DTUServiceHandler;
import com.wlyy.bcwlw.netty.handler.impl.DefaultHeartBeatHandler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.GlobalEventExecutor;
/**
 * 车联网，物联网，医院HIS，OA，商城等高端代码获取或者代码有疑问，加wx：17725354261，技术群：256860212，今日头条：大数据java架构师，公众号：前劲科技
 */
public class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

	private static final Logger logger = LogManager.getLogger("dtu_server");
	
	@Override
	protected void initChannel(SocketChannel sc) throws Exception {
		//先硬编码，待得到确认数据后改为配置的
		sc.pipeline().addLast(new IdleStateHandler(Const.maxIdleTime,
				Const.maxIdleTime, Const.maxIdleTime, TimeUnit.SECONDS));
		sc.pipeline().addLast(new DefaultHeartBeatHandler());
		sc.pipeline().addLast(new DTUDecodeHandler());
		sc.pipeline().addLast(new DTUEncodeHandler());
		sc.pipeline().addLast(new DTUServiceHandler());
	}

}
