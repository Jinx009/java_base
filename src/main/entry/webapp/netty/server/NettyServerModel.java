package main.entry.webapp.netty.server;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NettyServerModel {
	private int port;
	private int dataFrom;
	private boolean autoSend;
}
