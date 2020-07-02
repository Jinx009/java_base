package main.entry.webapp.netty.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NettyServer {

	private static final Logger log = LoggerFactory.getLogger(NettyServer.class);

	private int port;
	private int dataFrom;
	private boolean autoSend = false;
	public static Channel serverChannel;
	private volatile String dataStr;
	private volatile String timeStr;
	private volatile String dataFromStr;
	private volatile String timeFromStr;

	public NettyServer(int port,  int dataFrom, boolean autoSend) {
		this.autoSend = autoSend;
		this.dataFrom = dataFrom;
		this.port = port;
		try {
			bind(this.port);
		} catch (Exception e) {
			log.error("e:{}", e);
		}
	}

	public NettyServer getThis() {
		return this;
	}

	public void closeServer() {
		if (serverChannel != null) {
			log.warn("close server port:{}", port);
			serverChannel.close();
			serverChannel = null;
		}
	}

	public void bind(int port) throws Exception {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				EventLoopGroup bossGroup = new NioEventLoopGroup(); // bossGroup就是parentGroup，是负责处理TCP/IP连接的
				EventLoopGroup workerGroup = new NioEventLoopGroup(); // workerGroup就是childGroup,是负责处理Channel(通道)的I/O事件

				ServerBootstrap sb = new ServerBootstrap();
				sb.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
						.option(ChannelOption.SO_BACKLOG, 128) // 初始化服务端可连接队列,指定了队列的大小128
						.option(ChannelOption.TCP_NODELAY, true)
						.childOption(ChannelOption.SO_KEEPALIVE, true) // 保持长连接
						.childOption(ChannelOption.AUTO_READ, true)
						.childHandler(new ChannelInitializer<SocketChannel>() { // 绑定客户端连接时候触发操作
							@Override
							protected void initChannel(SocketChannel sh) throws Exception {
								sh.pipeline().addLast(new ServerHandler(getThis())); // 使用ServerHandler类来处理接收到的消息
							}
						});
				// 绑定监听端口，调用sync同步阻塞方法等待绑定操作完
				ChannelFuture future;
				try {
					future = sb.bind(port).sync();

					if (future.isSuccess()) {
						log.warn("netty server port :{} start", port);
						serverChannel = future.channel();
					} else {
						log.error("netty server close fail port:{}", port);
						future.cause().printStackTrace();
						bossGroup.shutdownGracefully(); // 关闭线程组
						workerGroup.shutdownGracefully();
					}
					// 成功绑定到端口之后,给channel增加一个
					// 管道关闭的监听器并同步阻塞,直到channel关闭,线程才会往下执行,结束进程。
					future.channel().closeFuture().sync();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();
	}
	
	
	public static void main(String[] args) {
//		NettyServer nettyServer = new NettyServer(3333,0,true);
//		NettyServer nettyServer2 = new NettyServer(4444,3333,false);
	}
}
