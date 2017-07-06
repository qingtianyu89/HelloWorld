package com.test.action.mq.local;

import com.test.action.mq.Exception.NotFoundTopicException;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by pangming on 2017/7/5.
 */
public class LocalController {

    protected static ConcurrentHashMap<String, Subject> subjectMap = new ConcurrentHashMap<>();

    public static boolean bind(String topic, Class<? extends LocalConsumer> consumerClazz) throws Exception {
        if (!TopicEnum.contain(topic)) {
            throw new NotFoundTopicException(String.format("该topic不存在, %s", topic));
        }
        LocalConsumer localConsumer = consumerClazz.newInstance();
        synchronized (topic) {
            if (subjectMap.containsKey(topic)) {
                Subject subject = subjectMap.get(topic);
                subject.getConsumerList().add(localConsumer);
            } else {
                Subject subject = new Subject(topic, localConsumer);
                subjectMap.put(topic, subject);
            }
        }
        return true;
    }
}
