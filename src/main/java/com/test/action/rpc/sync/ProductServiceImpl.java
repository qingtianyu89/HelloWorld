package com.test.action.rpc.sync;

/**
 * Created by pangming on 2017/3/27.
 */
public class ProductServiceImpl implements ProductService {

    @Override
    public void searchProductInfo(long productId) {
        System.out.println("查询订单详情");
    }
}
