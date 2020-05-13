package com.wlyy.bcwlw.netty.entity;

import com.wlyy.bcwlw.netty.consts.Const;
import com.wlyy.bcwlw.netty.server.NettyServer;

import io.netty.channel.Channel;

/**
 * 
* @ClassName: Connection 
* @Description: 连接model
* @author 287598457@qq.com 
* @date 2016年3月1日 下午11:00:35 
*
 */
public class Connection {
	
	public static Connection getConnection(String idenitity) {
		return NettyServer.connections.get(idenitity);
	}
	
	private Channel channel;
	
	private String identityId;

	public Connection(Channel channel, String identityId) {
		super();
		this.channel = channel;
		this.identityId = identityId;
	}
	
	public void register(Connection conn) {
		conn.getChannel().attr(Const.connection).set(conn);
		NettyServer.addConnection(conn);
	}
	
	public void logOut() {
		NettyServer.removeConnection(this);
		this.getChannel().attr(Const.connection).remove();
		this.channel.close();
	}
	
	public void write(byte[] rec) {
		this.channel.writeAndFlush(rec);
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public String getIdentityId() {
		return identityId;
	}

	public void setIdentityId(String identityId) {
		this.identityId = identityId;
	}
	
	
}
