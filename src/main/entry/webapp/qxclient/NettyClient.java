package main.entry.webapp.qxclient;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;  
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;  
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;  
import io.netty.channel.nio.NioEventLoopGroup;  
import io.netty.channel.socket.nio.NioSocketChannel;  



public class NettyClient implements Runnable{
	
	private static final Logger log = LoggerFactory.getLogger(NettyClient.class);
	
	public static byte[] b = new byte[] {};
	
	public static long time = 0;
	public static long maxTime = 0;
	public static int num = 0;

    private static volatile EventLoopGroup workerGroup;

    private String ip;

    private int port;

    public NettyClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @SuppressWarnings("rawtypes")
	@Override
    public void run() {
        // 客户端的配置
        workerGroup= new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            ChannelPipeline p = channel.pipeline();
                            //编码格式
//                            p.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8))
//                                    .addLast("encoder", new StringEncoder(CharsetUtil.UTF_8))
                                    //具体的客户端实现操作类
                                    p.addLast(new SimpleClientHandler(bootstrap, ip, port));
                        }
                    });

            ChannelFuture future = null;
            /**
             *防止初次链接不上 每隔十秒重连一次
             */
            while (future == null || !future.isSuccess()) {
                try {
                    future = bootstrap.connect(ip, port).sync();
                } catch (Exception ex) {
                    log.warn("重连中。。。。");
                    Thread.sleep(10000);
                }
            }


            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //workerGroup.shutdownGracefully();
        }
    }



    public static void main(String[] args) {
    	new NettyClient("139.196.205.157", 7777).run();
    }
}