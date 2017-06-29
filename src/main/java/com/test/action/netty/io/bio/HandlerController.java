package com.test.action.netty.io.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by pangming on 2017/6/26.
 */
public class HandlerController {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8090);
            while (true) {
                Socket accept = serverSocket.accept();
                if(accept != null){//每来一个请求就会创建 一个线程
                    Thread thread = new Thread(new ServerHandler(accept));
                    thread.start();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 伪异步io 虽然不同每个client请求创建一个线程 但是无法解决阻塞问题
     */
    private static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

    public static void aSync(){
        try {
            ServerSocket serverSocket = new ServerSocket(8090);
            while (true) {
                Socket accept = serverSocket.accept();
                if(accept != null){
                    executorService.submit(new ServerHandler(accept));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
