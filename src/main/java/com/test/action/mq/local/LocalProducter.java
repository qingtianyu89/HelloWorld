package com.test.action.mq.local;

/**
 * Created by pangming on 2017/7/5.
 * //生产者
 */
public class LocalProducter {

    public static void send(MqMessage mqMessage) {
        String topic = mqMessage.getTopic();
        Subject subject = LocalController.subjectMap.get(topic);
        if (subject == null) {
            throw new RuntimeException("subject is not exsit");
        }
        subject.getMqContext().put(mqMessage);
    }
}
