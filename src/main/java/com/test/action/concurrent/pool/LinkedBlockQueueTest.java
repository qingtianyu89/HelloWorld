package com.test.action.concurrent.pool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by pangming on 2017/5/23.
 */
public class LinkedBlockQueueTest {

    public static void main(String[] args) {
        final LinkedBlockQueueNotSafe<Integer> queue = new LinkedBlockQueueNotSafe<Integer>(10);
//        final LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>(10);
//        queue.push(1);
//        System.out.println(queue.take());
//        System.out.println(queue.take());

        for (int i = 0; i < 100; i++) {
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        queue.put(finalI);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Integer take = queue.take();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        while (true) {

        }
    }
}
