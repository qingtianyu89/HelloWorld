package com.test.action.netty.io.nio;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by pangming on 2017/6/26.
 * 异步非阻塞存在一个问题 写半包
 */
public class ServerReactorMutiTask implements Runnable{

    private ServerSocketChannel serverSocketChannel;
    private Selector selector;

    public ServerReactorMutiTask(ServerSocketChannel serverSocketChannel, Selector selector) {
        this.serverSocketChannel = serverSocketChannel;
        this.selector = selector;
    }

    @Override
    public void run() {
        try {
            while (true) {
                selector.select(1000);
                Set<SelectionKey> acceptKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = acceptKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    if(selectionKey.isAcceptable()){
                        handleAccept(selectionKey);
                    }
                    if(selectionKey.isReadable()){
                        handleReadle(selectionKey);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleAccept(SelectionKey key){
      //accept the conn
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        try {
            SocketChannel accept = serverSocketChannel.accept();
            accept.configureBlocking(false);
            accept.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleReadle(SelectionKey key){
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);//分配一个1m大小的缓冲区
        try {
            int read = socketChannel.read(byteBuffer);//将数据复制到缓冲区 并且返回读取数据状态
            if(read > 0){//大于0 则有数据到来
                byteBuffer.flip();//position 设置为0
                byte[] bytes = new byte[byteBuffer.remaining()];//创建一定大小的数组
                byteBuffer.get(bytes);//将bytebuffer缓冲区对象copy到bytes数组
                System.out.println(new String(bytes, "utf-8"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(socketChannel != null){
                try {
                    socketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
