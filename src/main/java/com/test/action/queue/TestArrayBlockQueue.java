package com.test.action.queue;

import com.alibaba.fastjson.JSONObject;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by pangming on 2017/5/9.
 */
public class TestArrayBlockQueue {

    public static void main(String[] args) {
        ArrayBlockingQueue queue = new ArrayBlockingQueue(5);

        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.add(4);
        Object poll = queue.poll();queue.poll();queue.poll();
        queue.add(5);
        queue.add(6);
        queue.add(7);
        queue.add(8);
        queue.poll();
        queue.poll();
        System.out.println(poll);
        System.out.println(JSONObject.toJSONString(queue));
    }
}
