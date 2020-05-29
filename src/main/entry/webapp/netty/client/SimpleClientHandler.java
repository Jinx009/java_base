package main.entry.webapp.netty.client;

import java.net.InetSocketAddress;
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
import main.entry.webapp.netty.server.NettyTcpConstant;

/**
 * 处理服务端返回的数据
 * 
 * @author Administrator
 *
 */
public class SimpleClientHandler extends ChannelInboundHandlerAdapter {

	private static Logger log = LoggerFactory.getLogger(SimpleClientHandler.class);

	/**
	 *
	 */
	private final Bootstrap bootstrap;

	/**
	 * 交互channel
	 */
	private static Channel channel;

	/**
	 * 服务端ip 重连时使用
	 */
	private NettyClient nettyClient;

	/**
	 * 服务端端口
	 */
	private int port;
	/**
	 * 需要被转发的数据server来源
	 */
	private long time = 0;

	/**
	 * 断线重连等待时间 /S
	 */
	private final static int reconnectDelay = 1;

	public SimpleClientHandler(Bootstrap bootstrap, NettyClient nettyClient) {
		this.bootstrap = bootstrap;
		this.nettyClient = nettyClient;
	}

	/**
	 * 客户端连接服务器后被调用
	 * 
	 * @param ctx
	 * @throws InterruptedException
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws InterruptedException {
		channel = ctx.channel();
		ctx.fireChannelActive();
		channelWrite(ctx);
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
		// 断开连接后实现重连机制
		ctx.channel().eventLoop().schedule(() -> doConnect(), reconnectDelay, TimeUnit.SECONDS);
	}

	/**
	 * 每隔十秒重连一次
	 */
	private void doConnect() {
		ChannelFuture future = bootstrap.connect(new InetSocketAddress(nettyClient.getIp(), nettyClient.getPort()));
		future.addListener(new ChannelFutureListener() {
			public void operationComplete(ChannelFuture f) throws Exception {
				if (f.isSuccess()) {
					log.warn("Started Tcp Client Success: {}", port);
				} else {
					log.warn("Started Tcp Client Failed: ");
					f.channel().eventLoop().schedule(() -> doConnect(), reconnectDelay, TimeUnit.SECONDS);
				}
			}
		});
	}

	/**
	 * 从服务器接收到数据后调用
	 * 
	 * @param ctx
	 * @param msg
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		log.warn("回写数据:{}", msg);
		ByteBuf buf = (ByteBuf) msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
	}

	/**
	 * 发生异常时被调用
	 * 
	 * @param ctx
	 * @param cause
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

		// cause.printStackTrace();
		log.warn("服务端发生异常【{}】", cause.getMessage());
		ctx.close();
	}

	/**
     * 新启一个线程  轮询发送数据
     * @DESCRIPTION: 客户端给服务端发送消息
     * @return: void
     * @throws InterruptedException 
     */
    public void channelWrite(ChannelHandlerContext ctx) throws InterruptedException {
    	new Thread(){
    		public void run(){
    			while(true){
    				if (nettyClient.getDataFrom()== 0) {
    					ctx.close();
						break;
					} 
    				if(nettyClient.getDataFrom()!=0){
    					if (NettyTcpConstant.map.get("server" + nettyClient.getDataFrom()+ "time") != null) {
							if (time == 0 || time != (long) NettyTcpConstant.map
									.get("server" + nettyClient.getDataFrom() + "time")) {
								byte[] data = (byte[]) NettyTcpConstant.map
										.get("server" + nettyClient.getDataFrom() + "data");
								time = (long) NettyTcpConstant.map
										.get("server" + nettyClient.getDataFrom() + "time");
								log.warn("client send ip:{},port:{},from:{}",nettyClient.getIp(),nettyClient.getPort(),nettyClient.getDataFrom());
								ByteBuf byteBuf = Unpooled.wrappedBuffer(data);
								channel.writeAndFlush(byteBuf);
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
    	}.start();
    }
}