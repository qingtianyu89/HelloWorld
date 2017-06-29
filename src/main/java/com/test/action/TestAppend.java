package com.test.action;

import com.alibaba.fastjson.serializer.ListSerializer;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ResultTreeType;
import com.sun.org.apache.xerces.internal.util.EntityResolver2Wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by yezhufeng on 2017/2/14.
 */
public class TestAppend {

    public static void main(String[] args) {
        List<String> alist1 = new ArrayList<String>();
        alist1.add("a");
        alist1.add("b");
        alist1.add("c");
        List<String> alist2 = new ArrayList<String>();
        alist2.add("*");
        alist2.add("^");
        alist2.add("&");
        List<String> alist3 = new ArrayList<String>();
        alist3.add("1");
        alist3.add("2");
        alist3.add("3");
        Append append = new Append();
//        Append test = append.test(alist1).test(alist2).test(alist3);
//        System.out.println(test.getAlist());
        List<List<String>> alist = new ArrayList<List<String>>();
        alist.add(alist1);
        alist.add(alist2);
        alist.add(alist3);
        List<String> result = null;
        List<String> strings = new TestAppend().appendTest(alist, result, 0);
        System.out.println(strings);
    }

    private List<String> appendTest(List<List<String>> alist, List<String> result, int index){
        if(index == alist.size()){
            return result;
        }
        List<String> strings = alist.get(index);
        if(result == null){
            return appendTest(alist, strings, index + 1);
        }
        List<String> appendList = new ArrayList<String>();
        for (String string : result) {
            for (String s : strings) {
                appendList.add(string + s);
            }
        }
        return appendTest(alist, appendList, index + 1);
    }


    private static class Append{
        private List<String> alist;

        public Append test(List<String> sourceList){
            if(alist == null){
                Append append = new Append();
                append.setAlist(sourceList);
                return append;
            }
            List<String> result = new ArrayList<String>();
            for (String s : alist) {
                for (String s1 : sourceList) {
                    result.add(s + s1);
                }
            }
            Append append = new Append();
            append.setAlist(result);
            return append;
        }

        public List<String> getAlist() {
            return alist;
        }

        public void setAlist(List<String> alist) {
            this.alist = alist;
        }
    }
}

