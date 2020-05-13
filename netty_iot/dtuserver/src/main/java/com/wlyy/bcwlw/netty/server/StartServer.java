package com.wlyy.bcwlw.netty.server;

public class StartServer {

	/**
	 * 车联网，物联网，医院HIS，OA，商城等高端代码获取或者代码有疑问，加wx：17725354261，技术群：256860212，今日头条：大数据java架构师，公众号：前劲科技
	 */
	
	public static void main(String[] args) {
		
		int port = 9978;
		
		NettyServer ns = new NettyServer();
		
		try {
			
			ns.bing(port);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
