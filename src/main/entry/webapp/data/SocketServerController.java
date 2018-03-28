package main.entry.webapp.data;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/socket/server")
public class SocketServerController {

	private static final Logger log = LoggerFactory.getLogger(SocketServerController.class);

	private static ServerSocket serverSocket;

	public static ServerSocket get() {
		try {
			if (serverSocket == null) {
				serverSocket = new ServerSocket(8888);
				return serverSocket;
			}
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return serverSocket;
	}

	@RequestMapping(path = "/start")
	@ResponseBody
	public String start() {
		realStart();
		return "success";
	}

	public void realStart() {
		try {
			Thread t = new Thread(new Runnable() {
				public void run() {
					try {
						ServerSocket serverSocket = get();
						while (true) {
							Socket socket = null;
							try {
								socket = serverSocket.accept(); // 从连接请求队列中取出一个连接
								System.out.println(
										"New connection accepted " + socket.getInetAddress() + ":" + socket.getPort());
								
								// 接收和发送数据
							} catch (IOException e) {
								// 这只是与单个客户通信时遇到的异常，可能是由于客户端过早断开连接引起的
								// 这种异常不应该中断整个while循环
								e.printStackTrace();
							} finally {
								try {
									if (socket != null)
										socket.close(); // 与一个客户通信结束后，要关闭Socket
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					} catch (Exception e) {
						log.error("error:{}", e);
					}
				}
			});
			t.start();
		} catch (Exception e) {
			log.error("error:{}", e);
		}
	}

}
