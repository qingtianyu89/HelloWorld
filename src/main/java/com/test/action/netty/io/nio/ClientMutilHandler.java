package com.test.action.netty.io.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by pangming on 2017/6/26.
 */
public class ClientMutilHandler implements Runnable{

    private SocketChannel socketChannel;
    private Selector selector;

    public ClientMutilHandler(SocketChannel socketChannel, Selector selector) {
        this.socketChannel = socketChannel;
        this.selector = selector;
    }

    @Override
    public void run() {
        while (true) {
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey next = iterator.next();
                    iterator.remove();
                    if(next.isValid()){
                        if (next.isConnectable()) {
                            handleConnect(next);
                        }
                        if(next.isReadable()){
                            handleWrite(next);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleConnect(SelectionKey key){
        SocketChannel socketChannel = (SocketChannel) key.channel();
        try {
            socketChannel.register(selector, SelectionKey.OP_READ);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleWrite(SelectionKey key){
        SocketChannel socketChannel = (SocketChannel) key.channel();
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
