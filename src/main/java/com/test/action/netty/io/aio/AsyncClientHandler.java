package com.test.action.netty.io.aio;

/**
 * Created by pangming on 2017/6/29.
 */
public class AsyncClientHandler implements Runnable {

    private String host;
    private int port;

    public AsyncClientHandler(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {

    }
}
