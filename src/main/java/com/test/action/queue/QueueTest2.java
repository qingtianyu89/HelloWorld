package com.test.action.queue;


import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by pangming on 2017/5/8.
 */
public class QueueTest2 {

    private ArrayList<Long> arrayList;
    private final int maxSize;
    private int size;
    private java.util.concurrent.locks.Lock lock;
    private Condition addCondition;
    private Condition removeCondition;


    public QueueTest2(ArrayList<Long> arrayList, int maxSize) {
        this.arrayList = arrayList;
        this.maxSize = maxSize;
        this.lock = new ReentrantLock();
        this.addCondition = lock.newCondition();
        this.removeCondition = lock.newCondition();
    }

    public void add(Long item) {
        lock.lock();
        try {
            while (true) {
                if (size == maxSize) {
                    try {
                        System.out.println("达到最大元素了");
                        addCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (size < maxSize) {
                    arrayList.add(item);
                    System.out.println("添加元素 item=" + item);
                    size++;
                    removeCondition.signal();
                    break;
                }
            }
        } finally {
            lock.unlock();
        }

    }

    public Long remove() {
        lock.lock();
        try {
            while (true) {
                if (size == 0) {
                    try {
                        System.out.println("没有元素了");
                        removeCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Long remove = 0L;
                if (size > 0) {
                    remove = arrayList.remove(size - 1);
                    System.out.println("删除元素 item=" + remove);
                    size--;
                    addCondition.signal();
                    return remove;
                }
            }
        } finally {
            lock.unlock();
        }

    }
}
