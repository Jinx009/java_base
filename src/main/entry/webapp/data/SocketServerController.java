package main.entry.webapp.data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
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

	@SuppressWarnings("resource")
	@RequestMapping(path = "/start")
	@ResponseBody
	public String start() {
		  try {    
	            ServerSocket serverSocket = new ServerSocket(8888);   
	            log.warn("server is comming ...");
	            while (true) {    
	            	log.warn("new client is comming ...");
	                Socket client = serverSocket.accept();    
	                new HandlerThread(client);    
	            }    
	        } catch (Exception e) {    
	            log.error("Server exception: " + e.getMessage());    
	        }   
		return "success";
	}

	private class HandlerThread implements Runnable {    
        private Socket socket;    
        public HandlerThread(Socket client) {    
            socket = client;    
            new Thread(this).start();    
        }    
        public void run() {    
            try {    
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));    
                String clientInputStr = input.readLine(); 
                log.warn("Client msg is:" + clientInputStr);    
                PrintStream out = new PrintStream(socket.getOutputStream());    
                if(clientInputStr.equals("123")){
                	out.println("321");    
                }else{
                	out.println("456");   
                }
                out.close();    
                input.close();    
            } catch (Exception e) {    
                log.error("Server run exception: " + e.getMessage());    
            } finally {    
                if (socket != null) {    
                    try {    
                        socket.close();    
                    } catch (Exception e) {    
                        socket = null;    
                        System.out.println("Server finally exception:" + e.getMessage());    
                    }    
                }    
            }   
        }    
    }    

}
