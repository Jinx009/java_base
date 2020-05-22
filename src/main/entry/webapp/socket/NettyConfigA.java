package main.entry.webapp.socket;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class NettyConfigA {
	 public static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
}
