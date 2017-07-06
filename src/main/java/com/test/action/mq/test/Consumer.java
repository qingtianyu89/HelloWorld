package com.test.action.mq.test;

import com.test.action.mq.local.LocalConsumer;
import com.test.action.mq.local.MqMessage;

/**
 * Created by pangming on 2017/7/5.
 */
public class Consumer extends LocalConsumer {

    @Override
    public void execute(MqMessage mqMessage) {
        System.out.println("order is execute");
    }
}
