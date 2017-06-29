package com.test.action.concurrent.pool;

import com.alibaba.fastjson.JSONObject;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by pangming on 2017/5/23.
 */
public class LinkedBlockQueue<T> {

    static class Node<T>{
        T t;
        Node next;

        public Node(T t) {
            this.t = t;
        }
    }

    private final int max_size;
    private AtomicInteger size = new AtomicInteger(0);
    private Node<T> head;
    private Node<T> last;
    private Lock addLock = new ReentrantLock();
    private Lock removeLock = new ReentrantLock();
    private Condition notFull = addLock.newCondition();
    private Condition notEmpty = removeLock.newCondition();

    public LinkedBlockQueue(){
        this.max_size = Integer.MAX_VALUE;
    }

    public LinkedBlockQueue(int capcity){
        this.max_size = capcity;
        this.head = this.last = new Node<T>(null);
    }

    /**
     * 增加
     */
    private void enqueue(Node node){
        last = last.next = node;
        System.out.println("插入数据 " + node.t);
        size.incrementAndGet();
    }

    private T dequeue(){
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
