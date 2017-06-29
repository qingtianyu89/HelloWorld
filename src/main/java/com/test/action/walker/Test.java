package com.test.action.walker;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.test.action.httpclient.HttpClientUtil;

import java.util.HashMap;
import java.util.Map;


public class Test {

    @org.junit.Test
    public void getDatasByClass() {
        Rule rule = new Rule(
                "http://www1.sxcredit.gov.cn/public/infocomquery.do?method=publicIndexQuery",
                new String[]{"query.enterprisename",
                        "query.registationnumber"}, new String[]{"兴网", ""},
                "cont_right", Rule.CLASS, Rule.POST);
        ExtractService.extract(rule);

    }

    @org.junit.Test
    public void getDatasByCssQuery() {
        Rule rule = new Rule("http://www.11315.com/search",
                new String[]{"name"}, new String[]{"兴网"},
                "div.g-mn div.con-model", Rule.SELECTION, Rule.GET);
        ExtractService.extract(rule);
    }

    @org.junit.Test
    public void test1() throws Exception {
        String ip = "11.184.192.50";
        System.getProperties().setProperty("http.proxyHost", ip);
        System.getProperties().setProperty("http.proxyPort", "80");

        Rule rule = new Rule("http://bj.genshuixue.com/st/-975.html",
                new String[]{"source"}, new String[]{"cat"},
                "search-item", Rule.CLASS, Rule.GET);
        ExtractService.extract(rule);
    }

    @org.junit.Test
    public void test2() throws Exception {
        Rule rule = new Rule("http://10.58.38.15:8888/v1/getallmessage", new String[]{},
                new String[]{}, "", 12, Rule.POST);
        ExtractService.extract(rule);
    }

    @org.junit.Test
    public void testApp() throws Exception {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("order", 1);
        paramsMap.put("count", 50);
        paramsMap.put("teacherclass", 100);
        paramsMap.put("pageno", 1);
        String str = HttpClientUtil.sendPostRequest("http://i.meishubaby.com/v2/teachersearch", paramsMap);
        JSONObject jsonObj = JSON.parseObject(str);
        JSONArray jsonArr = jsonObj.getJSONArray("teachers");
        System.out.println(str);
        for (int i = 0; i < jsonArr.size(); i++) {
            JSONObject obj = (JSONObject) jsonArr.get(i);
            System.out.println(obj.getString("nickname"));
        }
    }

}
