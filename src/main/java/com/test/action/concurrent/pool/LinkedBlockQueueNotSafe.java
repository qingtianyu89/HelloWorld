package com.test.action.concurrent.pool;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by pangming on 2017/5/23.
 */
public class LinkedBlockQueueNotSafe<T> {

    static class Node<T>{
        T t;
        Node next;

        public Node(T t) {
            this.t = t;
        }
    }

    private final int max_size;
    private volatile AtomicInteger size = new AtomicInteger(0);
    private Node<T> head;
    private Node<T> last;
    private Lock addLock = new ReentrantLock();
    private Lock removeLock = new ReentrantLock();
    private Condition notFull = addLock.newCondition();
    private Condition notEmpty = removeLock.newCondition();

    public LinkedBlockQueueNotSafe(){
        this.max_size = Integer.MAX_VALUE;
    }

    public LinkedBlockQueueNotSafe(int capcity){
        this.max_size = capcity;
    }

    /**
     * 发生线程不安全的情况
     * 1. put线程  enqueue发生重排序 先执行52行 size=1 head = last = null
     * 2. take线程 dequeue 执行67发生空指针异常
     * 3. put线程 enqueue 执行51(由于2发生异常不会执行)
     * @param node
     */
    private void enqueue(Node node){
        /**
         * if语句与下边的语句可能会产生重排序
         */
        if(size.get() == 0){
            this.head = this.last = node;
            System.out.println("插入数据 " + node.t);
            size.incrementAndGet();
            return;
        }
        last.next = node;//nullpointer
        last = node;
        System.out.println("插入数据 " + node.t);
        size.incrementAndGet();
    }

    private T dequeue(){
        /**
         * if语句与下边的语句可能会产生重排序
         */
        if(size.get() == 1){
            Node head = this.head;
            Object t = head.t;// nullpointer
            this.head = this.last = null;
            size.decrementAndGet();
            System.out.println("移除数据 " + t);
            return (T) t;
        }
        Node h = this.head;
        Node next = h.next;
        this.head = next;
        Object t = this.head.t;
        System.out.println("移除数据 " + t);
        size.decrementAndGet();
        return (T) t;
    }

    public void put(T t){
        Lock lock = this.addLock;
        lock.lock();
        try {
            while (size.get() == max_size) {
                try {
                    notFull.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.enqueue(new Node(t));
            if(size.get()+1 < max_size){
                notFull.signal();
            }
        }finally {
            lock.unlock();
        }
    }

    public T take(){
        Lock lock = this.removeLock;
        lock.lock();
        try {
            while (size.get() == 0) {
                try {
                    notEmpty.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T dequeue = this.dequeue();
            if(size.get() > 0){
                notEmpty.signal();
            }
            return dequeue;
        }finally {
            lock.unlock();
        }
    }

}
