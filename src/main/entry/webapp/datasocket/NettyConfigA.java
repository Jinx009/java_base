package main.entry.webapp.datasocket;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class NettyConfigA {
	public static byte[] data = new byte[] {};
	public static long time = 0;
	public static long maxTime = 0;
	public static int num = 0;
	
	
    /**
     * 存储每一个客户端接入进来时的channel对象
     */
    public static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
}
