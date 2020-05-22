package main.entry.webapp.datasocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandlerA extends ChannelInboundHandlerAdapter {

	private static final Logger log = LoggerFactory.getLogger(ServerHandlerA.class);

	/**
	 * 客户端与服务端创建连接的时候调用
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		NettyConfigA.group.add(ctx.channel());
		new Thread() {
		public void run() {
			String str = "0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
			byte[] data = new byte[] {};
			int i = 10000;
			while (true) {
				try {
					String s = "";
					if(i<13600) {
						s = i+str+i;
						
					}else {
						i = 10000;
						s = i+str+i;
					}
					data = s.getBytes();
					ByteBuf pingMessage = ctx.alloc().buffer(data.length);
					pingMessage.writeBytes(data);
					ctx.writeAndFlush(pingMessage);
					Thread.sleep(2000);
					i++;
				} catch (InterruptedException e) {
					e.printStackTrace();
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
		NettyConfigA.group.remove(ctx.channel());
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
		log.warn("--data---333-server接收到了：{}", info);
		ByteBuf buf = (ByteBuf) info;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		NettyConfigA.data = req;
	}

	

}