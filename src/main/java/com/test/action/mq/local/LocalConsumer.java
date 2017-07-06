package com.test.action.mq.local;

/**
 * Created by pangming on 2017/7/5.
 * 消费者
 */
public abstract class LocalConsumer{

    public abstract void execute(MqMessage mqMessage);
}
