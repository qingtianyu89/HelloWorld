package com.test.action.proxy.jdk;

/**
 * Created by yezhufeng on 2017/3/20.
 */
public class Test {

    public static void main(String[] args) {
        Condition proxy = (Condition) new ProxyFactory(new ConditionImpl()).getProxy();
        proxy.getConditions();
    }
}
