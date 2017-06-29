package com.test.action.tcp.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by pangming on 2017/5/4.
 */
public class ClientTest {

    public static void main(String[] args) {

    }

    static class ClientTask implements Runnable{
        private String host;
        private int port;
        private Selector selector;
        private SocketChannel socketChannel;
        private volatile boolean stop;

        public ClientTask(String host, int port){
            try {
                selector = Selector.open();
                socketChannel = SocketChannel.open();
                socketChannel.configureBlocking(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                if (socketChannel.connect(new InetSocketAddress(host, port))) {
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else {
                    socketChannel.register(selector, SelectionKey.OP_CONNECT);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey next = iterator.next();
                    SocketChannel channel = (SocketChannel) next.channel();
                    if (next.isConnectable() && channel.finishConnect()) {
                        channel.register(selector, SelectionKey.OP_READ);
                        iterator.remove();
                    } else if(next.isReadable()){

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
