package com.test.action.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by pangming on 2017/6/8.
 */
public class Test1 {

    private static int a = 2;
    private static CountDownLatch countDownLatch = new CountDownLatch(100);

    public static void main(String[] args) {
        Thread1 thread1 = new Thread1();
        thread1.start();
        try {
            TimeUnit.NANOSECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        Thread2 thread2 = new Thread2();
//        thread2.start();
//        try {
//            thread1.join();
//            thread2.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    a+=1;
                    countDownLatch.countDown();
                }
            }).start();
        }
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    static class Thread1 extends Thread {
        @Override
        public void run() {
            System.out.println(a);
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(a);
            synchronized (Thread1.class) {//读取主内存的值(可能存在线程没有从cpu cache写回主内存)
                System.out.println(a);
            }
        }
    }

    static class Thread2 extends Thread {
        @Override
        public void run() {
            a = a + 1;
            System.out.println("线程2执行完毕");
            countDownLatch.countDown();
        }
    }

}
