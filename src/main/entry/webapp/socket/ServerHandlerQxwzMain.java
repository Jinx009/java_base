package main.entry.webapp.socket;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import utils.HttpUtils;

public class ServerHandlerQxwzMain  extends ChannelInboundHandlerAdapter {

private static final Logger log = LoggerFactory.getLogger(ServerHandlerQxwzMain.class);
	

	/**
	 * 客户端与服务端创建连接的时候调用
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		log.warn("客户端与服务端连接开始...{}",ctx.channel().id());
		HttpUtils.get("http://127.0.0.1:8080/d/socketSave?status=conn&ip="+
		ctx.channel().remoteAddress().toString().split(":")[0].replaceAll("/", "")+"&clientPort="+
		ctx.channel().remoteAddress().toString().split(":")[1]+"&connPort=7777");
		NettyConfigQxwz.group.add(ctx.channel());
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
							Thread.sleep(1000);
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
		NettyConfigQxwz.group.remove(ctx.channel());
	}

	/**
	 * 服务端接收客户端发送过来的数据结束之后调用
	 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		HttpUtils.get("http://127.0.0.1:8080/d/socketSave?status=close&ip="+
		ctx.channel().remoteAddress().toString().split(":")[0].replaceAll("/", "")+"&clientPort="+
	    ctx.channel().remoteAddress().toString().split(":")[1]+"&connPort=7777");
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
		ByteBuf buf = (ByteBuf) info;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String str = "";
		for(byte b:req) {
			String s1 = Integer.toHexString(b);
			if(s1.length()==1) {
				str+=  "0"+s1;
			}else {
				str+=s1;
			}
		}
		log.warn("tcp --server-------接收到了：{}", str);
		str = str.replace(" ", "");//480000191800001200006764B56201075C00701EC51AE307040C042D0837020000000F00FDFF0383EA0F0DE0FE43A7C52512F8350000256300007F00000021010000EB000000010500007600000016050000E4777900D30000001CF2100070000000DCCA49320000000000000000C651
		try {
			HttpUtils.get("http://127.0.0.1:8080/d/rec?str="+str.replace(" ", "").replace("ffffff", "")+"&ip="+
			channelHandlerContext.channel().remoteAddress().toString().split(":")[0].replaceAll("/", "")+"&clientPort="+
			channelHandlerContext.channel().remoteAddress().toString().split(":")[1]+"&connPort=7777");
		} catch (Exception e) {
			log.error("error:{}",e);
		}
	
	}
}
	
