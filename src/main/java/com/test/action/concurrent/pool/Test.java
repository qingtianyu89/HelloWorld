package com.test.action.concurrent.pool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pangming on 2017/5/23.
 */
public class Test {

    public static void main(String[] args) {
        List<Long> productList = new ArrayList<Long>();

        for (int i = 0; i < 102; i++) {
            Consumer consumer = new Consumer(productList);
            new Thread(consumer, "thread-consumer-" + i).start();
        }

        for (int i = 0; i < 100; i++) {
            Producter producter = new Producter(productList, i);
            new Thread(producter, "thread-producter-" + i).start();
        }

        while (true) {

        }
    }
}
