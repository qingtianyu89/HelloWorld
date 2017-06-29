package com.test.action.concurrent.code;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by pangming on 2017/6/1.
 */
public class ConditionQueue {

    private ReentrantLock lock = new ReentrantLock();
    private Condition notEmpty = lock.newCondition();
    private Condition notFull = lock.newCondition();
    private List<Integer> alist = new ArrayList<Integer>();

    public void add(int item) {
        ReentrantLock lock = this.lock;
        lock.lock();
        try {
            System.out.println("代码执行  22");
            while (alist.size() == 3) {
                try {
                    System.out.println("代码等待  25");
                    notFull.await();
                    System.out.println("代码执行  27");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            alist.add(item);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public Integer remove() {
        ReentrantLock lock = this.lock;
        lock.lock();
        try {
            System.out.println("代码执行  42");
            while (alist.size() == 0) {
                try {
                    notEmpty.await();
                    System.out.println("代码执行  46");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Integer remove = alist.remove(0);
            notFull.signal();
            System.out.println("代码唤醒  54");
            return remove;
        } finally {
            lock.unlock();
        }
    }
}
