package com.test.action.sort;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by pangming on 2017/3/30.
 */
public class Test {


    public static void main(String[] args) {
        int[] arr = {2,5,3,4,1,6};
        for (int i = 0; i < arr.length; i++) {
            for (int j = i+1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {//交换
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] =temp;
                }
            }
            System.out.println(JSONObject.toJSONString(arr));
        }
    }
}
