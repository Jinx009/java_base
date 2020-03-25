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
					try {
						String str = "D300853EC008491F7B8280080DF9C008EFBFD26FB0288182ABFCE0A7A38700145EFE9B41821E03525FE98652AD5F04D1CFF45CAC1A10CC39FF984962D903FE85AFA84C60BA7FC91DFA53053237000E29FD2EAF041402973FD4224F1D1F0C4BCFE9173820020B35FEE1310C19803408FF45C4C0BB02892FF4AE0229AB00D80BFA6CEE06F023317F56233CA5D300853EC008491F7B8280080DF9C008EFBFD26FB0288182ABFCE0A7A38700145EFE9B41821E03525FE98652AD5F04D1CFF45CAC1A10CC39FF984962D903FE85AFA84C60BA7FC91DFA53053237000E29FD2EAF041402973FD4224F1D1F0C4BCFE9173820020B35FEE1310C19803408FF45C4C0BB02892FF4AE0229AB00D80BFA6CEE06F023317F56233CA5D300853EC008491F7B8280080DF9C008EFBFD26FB0288182ABFCE0A7A38700145EFE9B41821E03525FE98652AD5F04D1CFF45CAC1A10CC39FF984962D903FE85AFA84C60BA7FC91DFA53053237000E29FD2EAF041402973FD4224F1D1F0C4BCFE9173820020B35FEE1310C19803408FF45C4C0BB02892FF4AE0229AB00D80BFA6CEE06F023317F56233CA5D300853EC008491F7B8280080DF9C008EFBFD26FB0288182ABFCE0A7A38700145EFE9B41821E03525FE98652AD5F04D1CFF45CAC1A10CC39FF984962D903FE85AFA84C60BA7FC91DFA53053237000E29FD2EAF041402973FD4224F1D1F0C4BCFE9173820020B35FEE1310C19803408FF45C4C0BB02892FF4AE0229AB00D80BFA6CEE06F023317F56233CA5";
						data = str.getBytes();
						ByteBuf pingMessage = ctx.alloc().buffer(data.length);
						pingMessage.writeBytes(data);
						ctx.writeAndFlush(pingMessage);
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
//					if (!Arrays.equals(main.entry.webapp.qxwzdata.NettyConfig.data, data)) {
//						data = main.entry.webapp.qxwzdata.NettyConfig.data;
//						ByteBuf pingMessage = ctx.alloc().buffer(main.entry.webapp.qxwzdata.NettyConfig.data.length);
//						pingMessage.writeBytes(data);
//						ctx.writeAndFlush(pingMessage);
//					}else {
//						try {
//							Thread.sleep(1);
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
//					}
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