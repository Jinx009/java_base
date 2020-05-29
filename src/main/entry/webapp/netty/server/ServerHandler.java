package main.entry.webapp.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class ServerHandler extends ChannelInboundHandlerAdapter {

	private static final Logger log = LoggerFactory.getLogger(ServerHandler.class);

	private NettyServer nettyServer;
	private long time = 0;

	public ServerHandler(NettyServer nettyServer) {
		this.nettyServer = nettyServer;
	}

	// 接受client发送的消息
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		log.warn("--netty--server---{}接收到了：{}", nettyServer.getPort(), msg);
		System.out.println(JSONObject.toJSONString(nettyServer));
		int port = nettyServer.getPort();
		ByteBuf buf = (ByteBuf) msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		NettyTcpConstant.map.put("server" + port + "data", req);
		NettyTcpConstant.map.put("server" + port + "time", new Date().getTime());
	}

	// 通知处理器最后的channelRead()是当前批处理中的最后一条消息时调用
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//		log.warn("服务端接收数据完毕..");
		ctx.flush();
	}

	// 读操作时捕获到异常时调用
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		ctx.close();
	}

	// 客户端去和服务端连接成功时触发
	@SuppressWarnings("static-access")
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		new Thread() {
			public void run() {
				String str = "heartbeat";
				byte[] data = new byte[] {};
				while (true) {
					if (nettyServer.serverChannel == null) {
						ctx.close();
						break;
					} else {
						if (nettyServer.isAutoSend()) {
							try {
								data = str.getBytes();
								ByteBuf pingMessage = ctx.alloc().buffer(data.length);
								pingMessage.writeBytes(data);
								ctx.writeAndFlush(pingMessage);
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						if (nettyServer.getDataFrom() != 0) {
							if (NettyTcpConstant.map.get("server" + nettyServer.getDataFrom() + "time") != null) {
								long time1 = (long) NettyTcpConstant.map.get("server" + nettyServer.getDataFrom() + "time");
								if (time == 0 || time != time1) {
									data = (byte[]) NettyTcpConstant.map.get("server" + nettyServer.getDataFrom() + "data");
									time = time1;
									ByteBuf pingMessage = ctx.alloc().buffer(data.length);
									pingMessage.writeBytes(data);
									ctx.writeAndFlush(pingMessage);
								} else {
									try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
							}
						}
					}
				}
			}
		}.start();
	}
}