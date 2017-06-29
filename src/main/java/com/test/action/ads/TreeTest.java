package com.test.action.ads;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by pangming on 2017/3/31.
 */
public class TreeTest {

    public static void main(String[] args) {
        int[] a = {1, 2, 4, 6, 3, 5};
        Node parent = null;
        Node prev = null;
        for (int i = 0; i < a.length; i++) {
            int item = a[i];
            if(i == 0){
                parent = new Node(null, null, item);
                prev = parent;
                continue;
            }
            Node node = new Node(null, null, item);
            if (i < node.getValue()) {
                prev.setLeft(node);
            } else {
                prev.setRight(node);
            }
            prev = node;
        }
        System.out.println(JSONObject.toJSONString(parent));
    }

    static class Node{
        private Node left;
        private Node right;
        private int value;

        public Node(Node left, Node right, int value) {
            this.left = left;
            this.right = right;
            this.value = value;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}
