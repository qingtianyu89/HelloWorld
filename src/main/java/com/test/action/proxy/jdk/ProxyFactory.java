package com.test.action.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by yezhufeng on 2017/3/20.
 */
public class ProxyFactory implements InvocationHandler {

    private Object target;

    public ProxyFactory(Object obj){
        this.target = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("前置");
        Object invoke = method.invoke(target, args);
        System.out.println("后置");
        return invoke;
    }

    public Object getProxy(){
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                target.getClass().getInterfaces(), this);
    }

}
