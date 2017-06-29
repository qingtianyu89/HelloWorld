package com.test.action.concurrent.pool;

import java.util.List;

/**
 * Created by pangming on 2017/5/23.
 */
public class BlockQueue {

    private int max_size = 100;
    private int size;
    private List<Long> alist;

    public BlockQueue(List<Long> alist){
        this.alist = alist;
    }

    public void push(long item){
        synchronized (alist) {
            while (size == max_size) {
                try {
                    alist.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            alist.add(item);
            System.out.println("增加" + item);
            size++;
            alist.notify();
        }
    }

    public void take(){
        synchronized (alist) {
            while(size == 0){
                try {
                    alist.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Long remove = alist.remove(0);
            System.out.println("消费" + remove);
            size--;
            alist.notify();
        }
    }

}
