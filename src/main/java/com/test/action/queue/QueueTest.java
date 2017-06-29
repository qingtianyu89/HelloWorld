package com.test.action.queue;

import java.util.ArrayList;

/**
 * Created by pangming on 2017/5/8.
 */
public class QueueTest {

    private ArrayList<Long> arrayList;
    private final int maxSize;
    private int size;

    public QueueTest(ArrayList<Long> arrayList, int maxSize) {
        this.arrayList = arrayList;
        this.maxSize = maxSize;
    }

    public void add(Long item){
        synchronized (this) {
            while (true) {
                if (size == maxSize) {
                    try {
                        System.out.println("达到最大元素了");
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    arrayList.add(item);
                    System.out.println("添加元素 item="+item);
                    size++;
                    this.notifyAll();
                    break;
                }
            }
        }
    }

    public Long remove(){
        synchronized (this) {
            while (true) {
                if (size == 0) {
                    try {
                        System.out.println("没有元素了");
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    Long remove = 0L;
                    remove = arrayList.remove(size - 1);
                    System.out.println("删除元素 item=" + remove);
                    size--;
                    this.notifyAll();
                    return remove;
                }
            }
        }
    }
}
