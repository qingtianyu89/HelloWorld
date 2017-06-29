package com.test.action.netty.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by pangming on 2017/6/26.
 */
public class Client {

    public static void main(String[] args) {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            boolean connect = socketChannel.connect(new InetSocketAddress("127.0.0.1", 8090));
            socketChannel.configureBlocking(false);
            Selector selector = Selector.open();
            if (!connect) {
                socketChannel.register(selector, SelectionKey.OP_CONNECT);
            } else {
                new Client().handleWrite(socketChannel);
            }
            Thread thread = new Thread(new ClientMutilHandler(socketChannel, selector));
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleWrite(SocketChannel socketChannel){
        String content = "hello, world";
        byte[] arr = content.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.allocate(arr.length);
        byteBuffer.put(arr);
        byteBuffer.flip();
        try {
            socketChannel.write(byteBuffer);
            if(!byteBuffer.hasRemaining()){
                System.out.println("send succeed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
