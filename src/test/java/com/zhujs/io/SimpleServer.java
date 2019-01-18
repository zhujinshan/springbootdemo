package com.zhujs.io;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Description: TODO
 * @Author: jinshan.zhu
 * @Date: 2019/1/10 18:22
 */
public class SimpleServer implements Runnable {

    public static void main(String[] args) throws IOException {
        int port = 18080;
        SimpleServer server = new SimpleServer(port);
        Thread thread = new Thread(server);
        thread.start();
    }

    private int port;

    public SimpleServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("Server started");
            Socket socket;
            while (true) {
                System.out.println(1);
                socket = server.accept();
                new Thread(new SimpleServerHandler(socket)).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
