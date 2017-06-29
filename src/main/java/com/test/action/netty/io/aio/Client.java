package com.test.action.netty.io.aio;

/**
 * Created by pangming on 2017/6/29.
 */
public class Client {

    public static void main(String[] args) {
        Thread thread = new Thread(new AsyncClientHandler("127.0.0.1", 8090), "aio-asyncClient-001");
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
