package com.test.action.mq.Exception;

/**
 * Created by pangming on 2017/7/5.
 */
public class NotFoundTopicException extends RuntimeException {

    public NotFoundTopicException() {
        super();
    }

    public NotFoundTopicException(String message) {
        super(message);
    }
}
