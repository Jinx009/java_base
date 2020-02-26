package main.entry.webapp.data;

import java.io.*;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 */
public class ServerThread extends Thread {

	private static final Logger log = LoggerFactory.getLogger(ServerThread.class);
    private Socket socket;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        OutputStream outputStream = null;
        PrintWriter printWriter = null;

        try {

            // server接收消息
            inputStream = socket.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);
            byte[] receive = new byte[256];
			int read = bufferedInputStream.read(receive);
			SocketServer.b = receive;
            log.warn("message from Client:{} " , read);
            socket.shutdownInput();

            // server发送消息
            outputStream = socket.getOutputStream();
            printWriter = new PrintWriter(outputStream);
            printWriter.write("[welcome conn!]\n");
            printWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (printWriter != null) {
                    printWriter.close();
                }
                if (outputStream != null) {
                    outputStream.close();

                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
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