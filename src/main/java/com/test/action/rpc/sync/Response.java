package com.test.action.rpc.sync;

/**
 * Created by pangming on 2017/3/27.
 * 返回结果响应
 */
public class Response {

    private Object result;
    private int code;

    public Response(){}
    public Response(Object result, int code) {
        this.result = result;
        this.code = code;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
