package com.wlyy.bcwlw.netty.server;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.wlyy.bcwlw.netty.entity.Connection;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
/**
 * 车联网，物联网，医院HIS，OA，商城等高端代码获取或者代码有疑问，加wx：17725354261，技术群：256860212，今日头条：大数据java架构师，公众号：前劲科技
 */
public class NettyServer {
	
	private static final Logger logger = LogManager.getLogger("dtu_server");
	
	public static ConcurrentHashMap<String, Connection> connections = new ConcurrentHashMap<String, Connection>();
	
	public static void addConnection(Connection conn) {

		connections.put(conn.getIdentityId(), conn);
	}
	
	public static void removeConnection(Connection conn) {
		//判断channel
		if(conn.equals(getConnection(conn))){
			connections.remove(conn.getIdentityId());
		}
	}
	
	public static Connection getConnection(Connection conn){
		return connections.get(conn.getIdentityId());
	}
	
	public static void sendMsg(String identity, Object o) {
		//TODO
	}
	
	public void bing(int port) throws Exception{
		logger.debug("服务器已启动，端口为:"+port);
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try {
			
			ServerBootstrap b = new ServerBootstrap();

			b.group(bossGroup, workerGroup);
			b.channel(NioServerSocketChannel.class);
			b.option(ChannelOption.SO_BACKLOG, 1024);
			b.childHandler(new ChildChannelHandler());
			
			ChannelFuture f = b.bind(port).sync();
			
			f.channel().closeFuture().sync();
			
		} finally{

			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();

		}
		
	}
	
}
