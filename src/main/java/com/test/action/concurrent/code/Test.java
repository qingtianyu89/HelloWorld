package com.test.action.concurrent.code;

import com.alibaba.fastjson.JSONObject;

import java.util.LinkedHashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by pangming on 2017/3/30.
 */
public class Test {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        FutureTask<String> futureTask = new FutureTask<String>(new Callable<String>() {
//            @Override
//            public String call() throws Exception {
//                TimeUnit.SECONDS.sleep(3);
//                return "hello";
//            }
//        });
//        Thread thread = new Thread(futureTask);
//        thread.start();
//        String s = futureTask.get();
//        System.out.println(s);
//        thread.join();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (int i = 10; i < 20; i++) {
            linkedHashMap.put("" + i, i);
        }
        for (int i = 0; i < 10; i++) {
            linkedHashMap.put("" + i, i);
        }
        System.out.println(JSONObject.toJSONString(linkedHashMap));
    }

}
