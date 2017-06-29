package com.test.action.walker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 
 * @author zhy
 * 
 */
public class ExtractService {
	/**
	 * @param rule
	 * @return
	 */
	public static List<LinkTypeData> extract(Rule rule) {
		//代理ip
		ExtractService.setProxy();
		// 进行对rule的必要校验
		ExtractService.validateRule(rule);
		
		LinkTypeData data = null;
		try {
			/**
			 * 解析rule
			 */
			String url = rule.getUrl();
			String[] params = rule.getParams();
			String[] values = rule.getValues();
			String resultTagName = rule.getResultTagName();
			int type = rule.getType();
			int requestType = rule.getRequestMoethod();

			Connection conn = Jsoup.connect(url);
			// 设置查询参数

			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					conn.data(params[i], values[i]);
				}
			}

			// 设置请求类型
			Document doc = null;
			switch (requestType) {
			case Rule.GET:
				doc = conn.timeout(100000).get();
				break;
			case Rule.POST:
				doc = conn.timeout(100000).post();
				break;
			}

			// 处理返回数据
			Elements results = new Elements();
			switch (type) {
			case Rule.CLASS:
				results = doc.getElementsByClass(resultTagName);
				break;
			case Rule.ID:
				Element result = doc.getElementById(resultTagName);
				results.add(result);
				break;
			case Rule.SELECTION:
				results = doc.select(resultTagName);
				break;
			default:
				// 当resultTagName为空时默认取body标签
				if (resultTagName == null || "".equals(resultTagName)) {
					results = doc.getElementsByTag("body");
				}
			}

			for (Element result : results) {
				List<LinkTypeData> datas = new ArrayList<LinkTypeData>();
				// Elements links = result.getElementsByTag("a");
				Elements links = result.getElementsByClass("name"); // href text
				links.addAll(result.getElementsByClass("num"));
				links.addAll(result.getElementsByClass("text-indent-seo"));
				links.addAll(result.getElementsByClass("school-age"));

				for (Element link : links) {
					// 必要的筛选
					String linkHref = link.attr("href");
					String linkText = link.text();
					data = new LinkTypeData();
					data.setLinkHref(linkHref);

					if (!linkText.contains("<")) {
						if(linkText.indexOf("-")!=-1 && linkText.length()>12){
							data.setLinkText(linkText.substring(0, linkText.indexOf("-")));
						}else{
							data.setLinkText(linkText);
						}
					}
					datas.add(data);
				}
				StringBuffer sf = new StringBuffer("");
				for (LinkTypeData d : datas) {
					if(!"".equals(d.getLinkText())){
						sf.append(d.getLinkText()).append("|");
					}
					if(!"".equals(d.getLinkHref())){
						sf.append(d.getLinkHref()).append("|");
					}
				}
				System.out.println(sf.toString());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 对传入的参数进行必要的校验
	 */
	private static void validateRule(Rule rule) {
		String url = rule.getUrl();
		if (url == null || "".equals(url)) {
			throw new RuleException("url不能为空！");
		}
		if (!url.startsWith("http://")) {
			throw new RuleException("url的格式不正确！");
		}

		if (rule.getParams() != null && rule.getValues() != null) {
			if (rule.getParams().length != rule.getValues().length) {
				throw new RuleException("参数的键值对个数不匹配！");
			}
		}

	}
	
	/**
	 * 设置代理ip
	 */
	public static void setProxy(){
		List<ProxyPo> alist = new ArrayList<ProxyPo>();
		alist.add(new ProxyPo("106.3.40.249", 8081));
		alist.add(new ProxyPo("58.56.124.192", 80));
		alist.add(new ProxyPo("223.202.3.49", 8080));
		alist.add(new ProxyPo("218.4.236.117", 80));
		alist.add(new ProxyPo("121.10.252.139", 3128));
		alist.add(new ProxyPo("60.250.81.118", 8080));
		alist.add(new ProxyPo("113.57.252.107", 80));
		alist.add(new ProxyPo("113.214.13.1", 8000));
		int i = (int)(Math.random()*alist.size());
		System.getProperties().setProperty("http.proxyHost", alist.get(i).getIp());
		System.getProperties().setProperty("http.proxyPort", String.valueOf(alist.get(i).getPort()));
	}

}



class ProxyPo{
	private String ip;
	private int port;
	
	public ProxyPo() {
		super();
	}
	public ProxyPo(String ip, int port) {
		super();
		this.ip = ip;
		this.port = port;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
}
