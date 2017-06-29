package com.test.action.rpc.sync;

/**
 * Created by pangming on 2017/3/27.
 * 方法调用过程：
 * 1.创建本地接口对象(通过javassist生成？)，为了测试方便新增本地方法
 * 2.创建代理对象，代理对象也是一个线程。线程启动后一直阻塞，等待其他线程去唤醒。
 * 3.创建rpc线程，发起远程调用。rpc线程调用结束后唤醒代理对象线程。
 * 4.代理对象线程被唤醒后获取rpc线程调用的结果，返回。
 */
public class Test {

    public static void main(String[] args) {
        try {
            Class<?> aClass = Class.forName("com.test.action.rpc.sync.ProductServiceImpl");
            Object o = aClass.newInstance();
            ProductService productService = (ProductService) o;
            Response response = new Response();
            ProxyFactory proxyFactory = new ProxyFactory(productService, response);
            ProductService proxy = proxyFactory.getProxy();
            Thread threadProxy = new Thread(proxyFactory);
            threadProxy.start();//代理对象线程
            Thread threadRpc = new Thread(new Rpc(productService, response));
            threadRpc.start();//rpc线程
            proxy.searchProductInfo(0L);//方法调用
            threadProxy.join();
            threadRpc.join();
            //TODO  代理线程 rpc线程启动问题
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class Rpc implements Runnable{

        private ProductService productService;
        private Response response;

        public Rpc(ProductService productService, Response response){
            this.productService = productService;
            this.response = response;
        }

        @Override
        public void run() {
            Thread.yield();//优先让代理线程先运行
            synchronized (productService) {
                try {
                    System.out.println("调用rpc开始。。。。");
                    Thread.sleep(1000);
                    response.setCode(200);
                    String msg = "商品详情：lufei and namei";
                    response.setResult(msg);
                    System.out.println("调用rpc结束。。。。");
                    productService.notify();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
