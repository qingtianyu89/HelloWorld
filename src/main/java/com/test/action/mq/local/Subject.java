package com.test.action.mq.local;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by pangming on 2017/7/5.
 */
public class Subject {

    private String topic;
    private List<LocalConsumer> consumerList = new ArrayList<>();//线程的可见性 由synchronized来保护
    private ExecutorService consumerEs = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private ExecutorService dispatcherEs;
    private MqContext mqContext;

    public Subject(String topic, LocalConsumer consumer) {
        this(topic, consumer, Runtime.getRuntime().availableProcessors() * 2);
    }

    public Subject(String topic, LocalConsumer consumer, int concurrentThreads) {
        dispatcherEs = Executors.newFixedThreadPool(concurrentThreads);
        this.mqContext = new MqContext();
        this.topic = topic;
        consumerList.add(consumer);
        start();
    }

    private void start() {
        consumerEs.submit(new Runnable() {
            @Override
            public void run() {
                for (; ; ) {
                    if(!mqContext.queue.isEmpty()){
                        dispatcherEs.submit(new Runner(getRandomConsumer(), mqContext.queue.poll()));
                    }
                }
            }
        });
    }

    private LocalConsumer getRandomConsumer() {
        int i = new Random().nextInt(consumerList.size());
        return consumerList.get(i);
    }

    static class Runner implements Runnable {
        private MqMessage mqMessage;
        private LocalConsumer consumer;

        public Runner(LocalConsumer consumer, MqMessage mqMessage) {
            this.mqMessage = mqMessage;
            this.consumer = consumer;
        }

        @Override
        public void run() {
            if(mqMessage != null){
                consumer.execute(mqMessage);
            }
        }
    }


    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public List<LocalConsumer> getConsumerList() {
        return consumerList;
    }

    public void setConsumerList(List<LocalConsumer> consumerList) {
        this.consumerList = consumerList;
    }

    public MqContext getMqContext() {
        return mqContext;
    }

    public void setMqContext(MqContext mqContext) {
        this.mqContext = mqContext;
    }
}
