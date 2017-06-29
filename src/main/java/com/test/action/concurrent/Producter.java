package com.test.action.concurrent;

import java.util.List;
import java.util.Random;

/**
 * Created by yezhufeng on 2017/3/27.
 */
public class Producter implements Runnable {

    private List<Integer> productList;

    public Producter(List<Integer> productList) {
        this.productList = productList;
    }

    @Override
    public void run() {
        synchronized (productList){
            while (true){
                if(this.productList.size() == 10){
                    try {
                        productList.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Random random = new Random();
                int i = random.nextInt();
                productList.add(i);
                System.out.println("生产值生成   " + i);
                productList.notifyAll();
            }
        }
    }


}
