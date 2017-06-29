package com.test.action.rpc.sync;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by pangming on 2017/3/27.
 */
public class ProxyFactory implements InvocationHandler, Runnable{

    private ProductService productService;
    private Response response;
    private volatile boolean flag = false;

    public ProxyFactory(ProductService productService, Response response){
        this.productService = productService;
        this.response = response;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        while (true){
            if(flag){
                System.out.println("proxy远程调用结果：");
                System.out.println(JSONObject.toJSONString(response));
                break;
            }
        }
        return null;
    }

    @Override
    public void run() {
        synchronized (productService) {
            try {
                System.out.println("proxy is waiting");
                productService.wait();
                flag = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public ProductService getProxy(){
        return (ProductService) Proxy.newProxyInstance(productService.getClass().getClassLoader(), productService.getClass().getInterfaces(), this);
    }
}
