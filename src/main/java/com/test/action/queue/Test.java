package com.test.action.queue;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by pangming on 2017/5/8.
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {
        ArrayList<Long> longs = new ArrayList<Long>();
        //synchronized
//        final QueueTest queueTest = new QueueTest(longs, 10);

        //reentrantLock
        final QueueTest2 queueTest = new QueueTest2(longs, 10);
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(1000 + 1012);
        for (long i = 0; i < 1012; i++) {
            final long finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        cyclicBarrier.await();
                        queueTest.add(finalI);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        for (long i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        cyclicBarrier.await();
                        queueTest.remove();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        Thread.sleep(5000);
        System.out.println("main  " + JSONObject.toJSONString(longs));
    }
}
