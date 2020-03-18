package main.entry.webapp.client;


import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
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
                	log.warn("");
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
		save();
    }
    
    private void save() {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long t = System.currentTimeMillis();
			if (NettyClient.time == 0) {
				NettyClient.time = t;
			} else {
				if ((t - NettyClient.time) > NettyClient.maxTime) {
					NettyClient.maxTime = t - NettyClient.time;
					NettyClient.time = t;
					File f = new File("D:\\beijing.txt");
					File fileParent = f.getParentFile();
					if (!fileParent.exists()) {
						fileParent.mkdirs();
						f.createNewFile();
					}
					FileWriter fw = new FileWriter(f, true);
					PrintWriter pw = new PrintWriter(fw);
					pw.println("max time:" + NettyClient.maxTime + " " + df.format(new Date()));
					pw.flush();
					fw.flush();
					pw.close();
					fw.close();
				}else if ((t - NettyClient.time) > 3000) {
					NettyClient.num++;
					NettyClient.time = t;
					File f = new File("D:\\beijing.txt");
					File fileParent = f.getParentFile();
					if (!fileParent.exists()) {
						fileParent.mkdirs();
						f.createNewFile();
					}
					FileWriter fw = new FileWriter(f, true);
					PrintWriter pw = new PrintWriter(fw);
					pw.println("num:" + NettyClient.num + " " + df.format(new Date()));
					pw.flush();
					fw.flush();
					pw.close();
					fw.close();
				}else {
					NettyClient.time = t;
				}
			}
		} catch (Exception e) {
			log.error("error:{}", e);
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