package com.test.action.concurrent.pool;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by pangming on 2017/5/23.
 */
public class Consumer implements Runnable{

    private List<Long> productList = Collections.synchronizedList(new ArrayList<Long>());

    public Consumer(List<Long> productList){
        this.productList = productList;
    }



    @Override
    public void run() {
        synchronized (productList) {
            while (productList.isEmpty()) {
                try {
                    productList.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Long remove = productList.remove(0);
            System.out.println(Thread.currentThread().getName()+"消费者 "+remove);
            productList.notifyAll();
        }
    }
}
