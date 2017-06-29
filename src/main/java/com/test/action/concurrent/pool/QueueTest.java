package com.test.action.concurrent.pool;


import java.util.ArrayList;

/**
 * Created by pangming on 2017/5/23.
 */
public class QueueTest {

    public static void main(String[] args) {
        final BlockQueue blockQueue = new BlockQueue(new ArrayList<Long>());

        for (int i = 0; i < 10; i++) {
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    blockQueue.push(finalI);
                }
            }).start();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    blockQueue.take();
                }
            }).start();
        }

        while (true) {

        }
    }
}
