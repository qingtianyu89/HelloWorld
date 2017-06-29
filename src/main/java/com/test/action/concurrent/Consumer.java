package com.test.action.concurrent;

import java.util.List;

/**
 * Created by yezhufeng on 2017/3/27.
 */
public class Consumer  implements Runnable{

    private List<Integer> productList;

    public Consumer(List<Integer> productList) {
        this.productList = productList;
    }

    @Override
    public void run() {
        synchronized (productList){
            while (true) {
                System.out.println("消费者等待。。。");
                if(productList.size() == 0){
                    try {
                        productList.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Integer i = productList.remove(0);
                System.out.println("消费   "+i);
                productList.notifyAll();
            }
        }
    }
}
