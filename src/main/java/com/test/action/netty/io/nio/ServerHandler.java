package com.test.action.netty.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * Created by pangming on 2017/6/26.
 */
public class ServerHandler {

    public static void main(String[] args) {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress("127.0.0.1", 8090));
            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            Thread thread = new Thread(new ServerReactorMutiTask(serverSocketChannel, selector), "reactor-thread");
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
