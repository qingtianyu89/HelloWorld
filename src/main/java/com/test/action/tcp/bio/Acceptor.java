package com.test.action.tcp.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by pangming on 2017/5/4.
 */
public class Acceptor {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        while (true) {
            Socket accept = serverSocket.accept();
            executorService.submit(new SocketTask(accept));
        }
    }

    private static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    static class SocketTask implements Runnable{
        private Socket socket;
        public SocketTask(Socket socket){
            this.socket = socket;
        }
        @Override
        public void run() {
            System.out.println(socket+" is running");
        }
    }
}
