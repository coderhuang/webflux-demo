package com.example.demo.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {

	public static void main(String[] args) {

		EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
		Bootstrap bs = new Bootstrap();
		bs.group(eventLoopGroup)
		.channel(NioSocketChannel.class)
		.option(ChannelOption.SO_KEEPALIVE, true)
		.handler(new ChannelInitializer<Channel>() {

			@Override
			protected void initChannel(Channel ch) throws Exception {

				System.err.println("测试启动");
			}

		});

		ChannelFuture f;
		try {
			// Start the client.
			f = bs.connect("localhost", 9001).sync();
			// Wait until the connection is closed.
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {

			e.printStackTrace();
		} finally {

			eventLoopGroup.shutdownGracefully();
		}

	}

}
