package com.test.action.concurrent.code;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by pangming on 2017/6/1.
 */
public class LockSupportTest {

    public static void main(String[] args) {
        Thread thread = Thread.currentThread();
        LockSupport.unpark(thread);
        LockSupport.unpark(thread);
        LockSupport.park();
        LockSupport.park();
    }
}
