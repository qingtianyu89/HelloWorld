package com.test.action.mq.local;

/**
 * Created by pangming on 2017/7/5.
 */
public class MqMessage<T> {

    private String topic;
    private T t;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
