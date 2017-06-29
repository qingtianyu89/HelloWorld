package com.test.action.tcp.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by pangming on 2017/5/5.
 */
public class TimeServer {

    public static void main(String[] args) {
        final int port = 8090;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket accept = serverSocket.accept();
                new Thread(new TimeServerHandler(accept)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class TimeServerHandler implements Runnable{

        private Socket socket;
        public TimeServerHandler(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run() {
            BufferedReader bufferedReader = null;
            try {
                InputStream inputStream = socket.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String s = null;
                while (true) {
                    s = bufferedReader.readLine();
                    if(s == null){
                        break;
                    }
                    System.out.println(s);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(bufferedReader != null){
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(socket != null){
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
