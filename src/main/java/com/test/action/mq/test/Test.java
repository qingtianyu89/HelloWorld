package com.test.action.mq.test;

import com.test.action.mq.local.LocalController;
import com.test.action.mq.local.LocalProducter;
import com.test.action.mq.local.MqMessage;

/**
 * Created by pangming on 2017/7/5.
 */
public class Test {

    public static void main(String[] args) {
        try {
            LocalController.bind("51031", Consumer.class);
            LocalController.bind("51032", Consumer2.class);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        LocalController.bind("51032", Consumer3.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            for (int i = 0; i < 10; i++) {
                int finalI = i;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MqMessage mqMessage = new MqMessage();
                        mqMessage.setTopic("51031");
                        mqMessage.setT("hello" + finalI);
                        LocalProducter.send(mqMessage);
                    }
                }).start();
            }
            for (int i = 0; i < 10; i++) {
                int finalI = i;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MqMessage mqMessage = new MqMessage();
                        mqMessage.setTopic("51032");
                        mqMessage.setT("hello" + finalI);
                        LocalProducter.send(mqMessage);
                    }
                }).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        while (true) {

        }
    }

}
