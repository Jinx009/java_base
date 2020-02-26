package main.entry.webapp.data;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerRtcmThread  extends Thread {

	private static final Logger log = LoggerFactory.getLogger(ServerRtcmThread.class);
    private Socket socket;

    public ServerRtcmThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {

            // server接收消息
            inputStream = socket.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            String str;
            if ((str = bufferedReader.readLine()) != null) {
                log.warn("rtcm message from Client:{} " , str);
            }
            socket.shutdownInput();
            log.warn("message from Client:{} " , str);

            // server发送消息
            bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
			bufferedOutputStream.write(SocketServer.b);
			bufferedOutputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (bufferedOutputStream != null) {
                	bufferedOutputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}