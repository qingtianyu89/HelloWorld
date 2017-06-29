package com.test.action.concurrent.code;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * Created by pangming on 2017/6/1.
 */
public class ConditionQueueTest {

    public static void main(String[] args) {
        final ConditionQueue conditionQueue = new ConditionQueue();
        conditionQueue.add(1);
        conditionQueue.add(2);
        conditionQueue.add(3);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                conditionQueue.add(4);
            }
        });
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                conditionQueue.remove();
            }
        });
        thread.start();
        thread1.start();
        try {
            thread.join();
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
