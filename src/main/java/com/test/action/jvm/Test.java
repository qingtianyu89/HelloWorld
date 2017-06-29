package com.test.action.jvm;

import java.util.concurrent.TimeUnit;

/**
 * Created by pangming on 2017/4/18.
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        TimeUnit.SECONDS.sleep(3);
                        Node node = new Node();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("运行");
                }
            }
        });
        thread.start();
        thread.join();
    }

    static class Node{
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
