package main.entry.webapp.qxwz;


import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter {

	private static final Logger log = LoggerFactory.getLogger(ServerHandler.class);

	/**
	 * 客户端与服务端创建连接的时候调用
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		log.warn("客户端与服务端连接开始...{}",ctx.channel().id());
		NettyConfig.group.add(ctx.channel());
		new Thread() {
			public void run() {
				byte[] data = new byte[] {};
				while (true) {
					if (!Arrays.equals(main.entry.webapp.qxwzdata.NettyConfig.data, data)) {
						data = main.entry.webapp.qxwzdata.NettyConfig.data;
						ByteBuf pingMessage = ctx.alloc().buffer(main.entry.webapp.qxwzdata.NettyConfig.data.length);
						pingMessage.writeBytes(data);
						ctx.writeAndFlush(pingMessage);
					}else {
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}.start();
		
	}

	/**
	 * 客户端与服务端断开连接时调用
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		NettyConfig.group.remove(ctx.channel());
	}

	/**
	 * 服务端接收客户端发送过来的数据结束之后调用
	 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	/**
	 * 工程出现异常的时候调用
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	/**
	 * 服务端处理客户端websocket请求的核心方法，这里接收了客户端发来的信息
	 */
	@Override
	public void channelRead(ChannelHandlerContext channelHandlerContext, Object info) throws Exception {
		log.warn("netty data --server-------接收到了：{}", info);
//		ByteBuf buf = (ByteBuf) info;
//		byte[] req = new byte[buf.readableBytes()];
//		buf.readBytes(req);
	}

}