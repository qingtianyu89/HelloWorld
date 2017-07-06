package com.test.action.mq.local;

/**
 * Created by pangming on 2017/7/5.
 */
public enum  TopicEnum {

    ORDER("51031", "订单"),
    PRODUCT("51032", "商品");
    private String topic;
    private String desc;

    TopicEnum(String topic, String desc) {
        this.topic = topic;
        this.desc = desc;
    }

    public static boolean contain(String topic){
        for (TopicEnum topicEnum : TopicEnum.values()) {
            if(topicEnum.topic.equals(topic)){
                return true;
            }
        }
        return false;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
