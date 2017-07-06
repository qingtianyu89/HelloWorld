package com.test.action.netty.io.aio;

/**
 * Created by pangming on 2017/6/29.
 */
public class Server {

    public static void main(String[] args) {
        Thread thread = new Thread(new AsyncServerHandler(8090), "aio-asyncServerHandler");
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
