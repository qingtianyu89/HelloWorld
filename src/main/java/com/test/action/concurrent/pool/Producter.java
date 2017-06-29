package com.test.action.concurrent.pool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by pangming on 2017/5/23.
 */
public class Producter implements Runnable {

    private List<Long> productList = Collections.synchronizedList(new ArrayList<Long>());
    private long add;
    public Producter(List<Long> productList, long add){
        this.productList = productList;
        this.add = add;
    }
    @Override
    public void run() {
        synchronized (productList) {
            while (productList.size() == 10) {
                try {
                    productList.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            productList.add(add);
            System.out.println(Thread.currentThread().getName()+"生产者生产 "+add);
            productList.notifyAll();
        }
    }
}
