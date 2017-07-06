package com.test.action.mq.local;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by pangming on 2017/7/5.
 */
public class MqContext {

    protected LinkedBlockingQueue<MqMessage> queue = new LinkedBlockingQueue();
    private ExecutorService executorService;

    public MqContext(){
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
    }

    //阻塞
    public void put(MqMessage mqMessage){
        try {
            queue.put(mqMessage);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
