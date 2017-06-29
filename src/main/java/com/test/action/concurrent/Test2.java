package com.test.action.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by pangming on 2017/3/27.
 */
public class Test2 {

    public static void main(String[] args) throws InterruptedException {
        List<Integer> productList = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            new Thread(new Producter(productList)).start();
            new Thread(new Consumer(productList)).start();
        }
        TimeUnit.SECONDS.sleep(3);
    }
}
