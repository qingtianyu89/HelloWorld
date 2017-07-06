package com.test.action.mq.test;

import com.test.action.mq.local.LocalConsumer;
import com.test.action.mq.local.MqMessage;

/**
 * Created by pangming on 2017/7/5.
 */
public class Consumer3 extends LocalConsumer {

    @Override
    public void execute(MqMessage mqMessage) {
        System.out.println("product2 is execute, content="+mqMessage.getT());
    }
}
