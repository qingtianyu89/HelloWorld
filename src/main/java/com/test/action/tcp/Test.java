package com.test.action.tcp;

import javax.net.ServerSocketFactory;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by pangming on 2017/5/3.
 */
public class Test {

    public static void main(String[] args) {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            System.out.println(inetAddress.getHostAddress());
            System.out.println(inetAddress.getHostName());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*2);

    private void test() throws Exception {
        ServerSocket serverSocket = new ServerSocket();
        while (true) {
            Socket accept = serverSocket.accept();
            executorService.submit(new SocketTask(accept));
        }
    }

    static class SocketTask implements Runnable {
        private Socket socket;
        public SocketTask(Socket socket){
            this.socket = socket;
        }
        @Override
        public void run() {
            System.out.println("socket is running");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
