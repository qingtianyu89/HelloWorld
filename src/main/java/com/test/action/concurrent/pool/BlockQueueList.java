package com.test.action.concurrent.pool;

/**
 * Created by pangming on 2017/5/23.
 */
public class BlockQueueList<T> {

    private int max_size = Integer.MAX_VALUE;
    private int size;
    private boolean isEmpty;
    private Node head;
    private Node tail;

    public BlockQueueList() {

    }

    public void add(T t) {
        synchronized (this) {
            while (this.size == max_size) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (head == null) {
                Node<T> node = new Node<T>(t);
                this.head = node;
                this.tail = this.head;
                size++;
                this.notifyAll();
                return;
            }
            Node temp = this.tail;
            Node<T> node = new Node<T>(t);
            temp.setNext(node);
            this.tail = node;
            size++;
            this.notifyAll();
        }
    }


    public T take() {
        synchronized (this) {
            while (size == 0) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Node target = this.head;
            Node prev = null;
            while(target != null){
                Node next = target.getNext();
                if(next == null){
                    prev = this.head;
                    break;
                }
                if(next.getNext() == null){
                    prev = target;
                    break;
                }
                target = next;
            }
            Node result = prev.getNext();
            if (result == null) {
                result = prev;
                this.tail = null;
                this.head = null;
            } else {
                prev.setNext(null);
                this.tail = prev;
            }
            size--;
            return (T) result.getT();
        }
    }

    static class Node<T> {
        private T t;
        private Node next;

        public Node(T t) {
            this.t = t;
        }

        public T getT() {
            return t;
        }

        public void setT(T t) {
            this.t = t;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }
}
