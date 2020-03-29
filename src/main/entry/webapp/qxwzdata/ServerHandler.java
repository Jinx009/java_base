package main.entry.webapp.qxwzdata;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

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
		NettyConfig.group.add(ctx.channel());
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
		log.warn("--netty 6666 data----server接收到了：{}", info);
		ByteBuf buf = (ByteBuf) info;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		NettyConfig.data = req;
//		save();
	}

	private void save() {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long t = System.currentTimeMillis();
			if (NettyConfig.time == 0) {
				NettyConfig.time = t;
			} else {
				if ((t - NettyConfig.time) > NettyConfig.maxTime) {
					NettyConfig.maxTime = t - NettyConfig.time;
					NettyConfig.time = t;
					File f = new File("/data/logs/gnss/qxwz.txt");
					File fileParent = f.getParentFile();
					if (!fileParent.exists()) {
						fileParent.mkdirs();
						f.createNewFile();
					}
					FileWriter fw = new FileWriter(f, true);
					PrintWriter pw = new PrintWriter(fw);
					pw.println("max time:" + NettyConfig.maxTime + " " + df.format(new Date()));
					pw.flush();
					fw.flush();
					pw.close();
					fw.close();
				}else if ((t - NettyConfig.time) > 3000) {
					NettyConfig.num++;
					NettyConfig.time = t;
					File f = new File("/data/logs/gnss/qxwz.txt");
					File fileParent = f.getParentFile();
					if (!fileParent.exists()) {
						fileParent.mkdirs();
						f.createNewFile();
					}
					FileWriter fw = new FileWriter(f, true);
					PrintWriter pw = new PrintWriter(fw);
					pw.println("num:" + NettyConfig.num + " " + df.format(new Date()));
					pw.flush();
					fw.flush();
					pw.close();
					fw.close();
				}else {
					NettyConfig.time = t;
				}
			}
		} catch (Exception e) {
			log.error("error:{}", e);
		}
	}

}