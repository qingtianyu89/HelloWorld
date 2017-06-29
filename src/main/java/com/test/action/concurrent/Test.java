package com.test.action.concurrent;

import java.util.concurrent.TimeUnit;

/**
 * Created by yezhufeng on 2017/3/27.
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {
        int count = 5;
        int[] a = {1, 2};
        new Thread(new ThreadA(a, count)).start();
        new Thread(new ThreadA(a, count)).start();
        TimeUnit.SECONDS.sleep(10);
    }

    static class ThreadA implements Runnable {
        private int[] a;
        private int count;

        public ThreadA(int[] a, int count) {
            this.a = a;
            this.count = count;
        }

        @Override
        public void run() {
            synchronized (a) {
                while (count-- > 0) {
                    a.notify();
                    System.out.println("1");
                    try {
                        a.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("2");
                }
                return;
            }
        }
        //1.线程1执行a.notify()，释放锁，但不释放cpu，往后执行，执行直到a.wait()
        //2.线程2从等待队列中获取a的对象锁。执行a.notify(),然后释放锁，但不是放cpu，执行直到a.wait();
        //3.线程从等待队列中获取到对象锁，然后根据执行记录从a.wait()方法开始往后执行。重复1
    }
}
