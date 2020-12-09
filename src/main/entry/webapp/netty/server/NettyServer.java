package main.entry.webapp.netty.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {

	private static final Logger log = LoggerFactory.getLogger(NettyServer.class);

	
    private int port;
    private ServerSocketChannel serverSocketChannel;
 
    public NettyServer(int port){
        this.port = port;
        bind();
    }
    
    private void bind() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //服务端要建立两个group，一个负责接收客户端的连接，一个负责处理数据传输
                //连接处理group
                EventLoopGroup boss = new NioEventLoopGroup();
                //事件处理group
                EventLoopGroup worker = new NioEventLoopGroup();
                ServerBootstrap bootstrap = new ServerBootstrap();
                // 绑定处理group
                bootstrap.group(boss, worker).channel(NioServerSocketChannel.class)
                        //保持连接数
                        .option(ChannelOption.SO_BACKLOG, 300)
                        //有数据立即发送
                        .option(ChannelOption.TCP_NODELAY, true)
                        //保持连接
                        .childOption(ChannelOption.SO_KEEPALIVE, true)
                        //处理新连接
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel sc) throws Exception {
                                // 增加任务处理
                                ChannelPipeline p = sc.pipeline();
                                p.addLast(
                                        //自定义的处理器
                                        new ServerHandler());
                            }
                        });
 
                //绑定端口，同步等待成功
                ChannelFuture future;
                try {
                    future = bootstrap.bind(port).sync();
                    if (future.isSuccess()) {
                        serverSocketChannel = (ServerSocketChannel) future.channel();
                        log.warn("服务端启动成功，端口：{}",port);
                    } else {
                    	log.warn("服务端启动失败！");
                    }
                    
                    //等待服务监听端口关闭,就是由于这里会将线程阻塞，导致无法发送信息，所以我这里开了线程
                    future.channel().closeFuture().sync();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    //优雅地退出，释放线程池资源
                    boss.shutdownGracefully();
                    worker.shutdownGracefully();
                }
            }
        });
        thread.start();
    }
    
    public void sendMessage(Object msg){
        if(serverSocketChannel != null){
            serverSocketChannel.writeAndFlush(msg);
        }
    }
    
	public static void main(String[] args) {
		new NettyServer(1123);
	}
	
}
