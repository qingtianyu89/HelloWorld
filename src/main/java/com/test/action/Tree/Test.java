package com.test.action.Tree;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.ListSerializer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pangming on 2017/4/14.
 */
public class Test {

    public static void main(String[] args) {
        int i = 1;
        Node node = new Node();
        node.setValue(1);
        Node current = node;
        while (++i < 5) {
            Node temp = new Node();
            temp.setValue(i);
            current.setNext(temp);
            current = temp;
        }
//        transfer(current);
        System.out.println(JSONObject.toJSONString(node));
        System.out.println(JSONObject.toJSONString( transfer(node)));
    }

    private static Node transfer(Node node){
        Node current = null;
        while (node != null) {
            Node temp = node.getNext();
            node.setNext(current);
            current = node;
            node = temp;
        }
        return current;
    }

    static class Node{
        private int value;
        private Node next;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }
}
