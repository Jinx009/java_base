package main.entry.webapp.qxwz;


import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 处理服务端返回的数据
 * 
 * @author Administrator
 *
 */
public class SimpleClientHandler extends ChannelInboundHandlerAdapter{
	
	private static Logger log = LoggerFactory.getLogger(SimpleClientHandler.class);

    /**
     *
     */
    private final Bootstrap bootstrap;

    /**
     * 交互channel
     */
    @SuppressWarnings("unused")
	private static Channel channel;

    /**
     * 服务端ip  重连时使用
     */
    private String ip;

    /**
     * 服务端端口
     */
    private int port;


    /**
     * 断线重连等待时间 /S
     */
    private final static int reconnectDelay = 1;

    public SimpleClientHandler(Bootstrap bootstrap, String ip, int port) {
        this.bootstrap = bootstrap;
        this.ip = ip;
        this.port = port;
    }

    /**
     * 客户端连接服务器后被调用
     * @param ctx
     * @throws InterruptedException 
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws InterruptedException {
        channel = ctx.channel();
        ctx.fireChannelActive();
        channelWrite();
    }

    /**
     * @param ctx
     * @DESCRIPTION: 服务端端终止连接 后触发此函数
     * @return: void
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        try {
        	ctx.fireChannelInactive();
        	log.warn("服务端终止了服务");
            super.channelInactive(ctx);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //断开连接后实现重连机制
        ctx.channel().eventLoop().schedule(() -> doConnect(), reconnectDelay, TimeUnit.SECONDS);
    }

    /**
     * 每隔十秒重连一次
     */
    private void doConnect() {
        ChannelFuture future = bootstrap.connect(new InetSocketAddress(ip, port));
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture f) throws Exception {
                if (f.isSuccess()) {
                	String msg = "GET /RTCM32_GGB HTTP/1.1 \r\n";// 请求行
            		msg += "Host: rtk.ntrip.qxwz.com \r\n";// 请求首部
            		msg += "Ntrip-Version: Ntrip/2.0 \r\n";// 通用首部
            		msg += "User-Agent: NTRIP GNSSInternetRadio 2.0.10 \r\n";// 请求首部
            		 msg += "Accept: */*\r\nConnection: close\r\n";// 7802_RTD
            		msg += "Accept:*/* \r\n";// 请求首部
            		msg += "Authorization: Basic " + Base64.getEncoder().encodeToString("qxnfun00291:a79b386".getBytes())
            				+ "\r\n\r\n";// base64加密用户名和密码 // 请求首部
            		log.warn("Started Tcp Client:{} ",msg);
            		ByteBuf byteBuf = Unpooled.wrappedBuffer(msg.getBytes());
            		f.channel().writeAndFlush(byteBuf);
            		msg = "$GPGGA,013406.00,30.731083,N,103.970377,E,1,16,0.8,122.1179,M,-7.002,M,,*46\r\n";
            		Thread.sleep(2000);
            		byteBuf = Unpooled.wrappedBuffer(msg.getBytes());
            		f.channel().writeAndFlush(byteBuf);
                } else {
                	log.warn("Started Tcp Client Failed: ");
                    f.channel().eventLoop().schedule(() -> doConnect(), reconnectDelay, TimeUnit.SECONDS);
                }
            }
        });
    }

    /**
     * 从服务器接收到数据后调用
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
    	log.warn("回写数据:" + msg);
		ByteBuf buf = (ByteBuf) msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		
		try {
//			Socket s = new Socket("139.224.237.198", 6666);
			Socket s = new Socket("139.196.205.157", 6666);
			
			OutputStream outputStream = s.getOutputStream();
			outputStream.write(req);
			outputStream.flush();
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
    }


    /**
     * 发生异常时被调用
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

        //cause.printStackTrace();
    	log.warn("服务端发生异常【{}】", cause.getMessage());
        ctx.close();
    }

    /**
     * 新启一个线程  轮询发送数据
     * @DESCRIPTION: 客户端给服务端发送消息
     * @return: void
     * @throws InterruptedException 
     */
    public void channelWrite() throws InterruptedException {
    	
    }
}