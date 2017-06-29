package com.test.action.httpclient;

import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.log4j.Logger;

public class HttpClientUtil {
	
	protected static final Logger logger = Logger.getLogger(HttpClientUtil.class);

	@SuppressWarnings("unused")
	private static final String HTTP_CONTENT_CHARSET = "UTF-8";
	public static final Integer MAX_TIME_OUT = Integer.valueOf(5000);
	public static final Integer MAX_IDLE_TIME_OUT = Integer.valueOf(60000);
	public static final Integer MAX_CONN = Integer.valueOf(100);

	public static HttpClient httpClient = null;

	static {
		MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
		connectionManager.closeIdleConnections(MAX_IDLE_TIME_OUT.intValue());
		connectionManager.getParams().setParameter(
				"http.connection-manager.max-total", MAX_CONN);
		httpClient = new HttpClient(connectionManager);
		httpClient.getParams()
				.setParameter("http.socket.timeout", MAX_TIME_OUT);
		httpClient.getParams().setParameter("http.connection.timeout",
				MAX_TIME_OUT);
		httpClient.getParams().setParameter("http.connection-manager.timeout",
				Long.valueOf(MAX_TIME_OUT.longValue()));
	}

	@SuppressWarnings("rawtypes")
	public static String sendPostRequest(String url,
			Map<String, String> headers, Map<String, Object> param) {
		PostMethod post = new PostMethod(url);
		post.getParams().setParameter("http.protocol.content-charset", "UTF-8");

		if (param != null)
			for (Map.Entry entry : param.entrySet())
				if (entry.getValue() != null)
					post.addParameter((String) entry.getKey(), entry.getValue()
							.toString());

		post.addRequestHeader(new Header("Connection", "close"));
		if (headers != null)
			for (Map.Entry<String, String> entry : headers.entrySet())
				post.addRequestHeader(new Header(entry.getKey(), entry
						.getValue()));

		try {
			httpClient.executeMethod(post);
			if (post.getStatusCode() == 200) {
				String str = post.getResponseBodyAsString();
				return str;
			}

			post.abort();
			System.out.println(post.getStatusCode());
			logger.error(
					"Http Comunication error!code-->" + post.getStatusCode(),
					null);
		} catch (Exception e) {
			logger.error(
					"Http post occur error!url=" + url + ",param=" + param, e);
		} finally {
			post.releaseConnection();
		}
		return null;
	}

	/**
	 * 调用post，返回StatusCode和body
	 * 
	 * @param url
	 * @param headers
	 * @param param
	 * @param result
	 */
	@SuppressWarnings("rawtypes")
	public static void sendPostRequest(String url, Map<String, String> headers,
			Map<String, Object> param, Map<String, Object> result) {
		PostMethod post = new PostMethod(url);
		post.getParams().setParameter("http.protocol.content-charset", "UTF-8");

		if (param != null)
			for (Map.Entry entry : param.entrySet())
				if (entry.getValue() != null)
					post.addParameter((String) entry.getKey(), entry.getValue()
							.toString());

		post.addRequestHeader(new Header("Connection", "close"));
		if (headers != null)
			for (Map.Entry<String, String> entry : headers.entrySet())
				post.addRequestHeader(new Header(entry.getKey(), entry
						.getValue()));

		try {
			httpClient.executeMethod(post);
			result.put("statuscode", post.getStatusCode());
			if (post.getStatusCode() == 200) {
				String str = post.getResponseBodyAsString();
				result.put("body", str);
				return;
			}

			post.abort();
			System.out.println(post.getStatusCode());
			logger.error(
					"Http Comunication error!code-->" + post.getStatusCode(),
					null);
		} catch (Exception e) {
			logger.error(
					"Http post occur error!url=" + url + ",param=" + param, e);
		} finally {
			post.releaseConnection();
		}
	}

	@SuppressWarnings("rawtypes")
	public static String sendPostRequest(String url, Map<String, Object> param) {
		PostMethod post = new PostMethod(url);
		post.getParams().setParameter("http.protocol.content-charset", "UTF-8");
		if (param != null) {
			for (Map.Entry entry : param.entrySet()) {
				if (entry.getValue() != null)
					post.addParameter((String) entry.getKey(), entry.getValue()
							.toString());
			}
		}
		try {
			post.addRequestHeader(new Header("Connection", "close"));
			//ip 代理
//			httpClient.getHostConfiguration().setProxy("", 80);
//			httpClient.getParams().setAuthenticationPreemptive(true);  
			//如果代理需要密码验证，这里设置用户名密码  
//			Credentials upcreds = new UsernamePasswordCredentials("username", "password");
//			httpClient.getState().setProxyCredentials(AuthScope.ANY, upcreds);
			
			httpClient.executeMethod(post);
			if (post.getStatusCode() == 200) {
				String str = post.getResponseBodyAsString();
				return str;
			}
			post.abort();
			System.out.println(post.getStatusCode());
			logger.error(
					"Http Comunication error!code-->" + post.getStatusCode(),
					null);
		} catch (Exception e) {
			logger.error(
					"Http post occur error!url=" + url + ",param=" + param, e);
		} finally {
			post.releaseConnection();
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public static String sendPostRequest(String url, String content) {
		PostMethod post = new PostMethod(url);
		post.getParams().setParameter("http.protocol.content-charset", "UTF-8");
		if (content != null && !"".equals(content))
			post.setRequestBody(content);
		try {
			post.addRequestHeader(new Header("Connection", "close"));

			httpClient.executeMethod(post);
			if (post.getStatusCode() == 200) {
				String str = post.getResponseBodyAsString();
				return str;
			}
			post.abort();
			logger.error(
					"Http Comunication error!code-->" + post.getStatusCode(),
					null);
		} catch (Exception e) {
			logger.error("Http post occur error!url=" + url + ",content="
					+ content, e);
		} finally {
			post.releaseConnection();
		}
		return null;
	}

	public static String getRequest(String url) {
		GetMethod get = new GetMethod(url);
		get.getParams().setParameter("http.protocol.content-charset", "UTF-8");
		try {
			get.addRequestHeader(new Header("Connection", "close"));

			httpClient.executeMethod(get);
			if (get.getStatusCode() == 200) {
				String str = get.getResponseBodyAsString();
				return str;
			}
			get.abort();
			logger.error(
					"Http Comunication error!code-->" + get.getStatusCode(),
					null);
		} catch (Exception e) {
			logger.error("Http post occur error!url=" + url, e);
		} finally {
			get.releaseConnection();
		}
		return null;
	}

	public static String getRequest(Map<String, String> headers,
			final String url) {
		GetMethod get = new GetMethod(url);
		get.getParams().setParameter("http.protocol.content-charset", "UTF-8");
		try {
			get.addRequestHeader(new Header("Connection", "close"));
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				get.addRequestHeader(new Header(entry.getKey(), entry
						.getValue()));
			}

			httpClient.executeMethod(get);
			if (get.getStatusCode() == 200) {
				String str = get.getResponseBodyAsString();
				return str;
			}
			get.abort();
			logger.error(
					"Http Comunication error!code-->" + get.getStatusCode(),
					null);
		} catch (Exception e) {
			logger.error("Http post occur error!url=" + url, e);
		} finally {
			get.releaseConnection();
		}
		return null;
	}

	public static String putRequest(String url) {
		PutMethod put = new PutMethod(url);
		put.getParams().setParameter("http.protocol.content-charset", "UTF-8");
		try {
			put.addRequestHeader(new Header("Connection", "close"));

			httpClient.executeMethod(put);
			if (put.getStatusCode() == 200) {
				String str = put.getResponseBodyAsString();
				return str;
			}
			put.abort();
			logger.error(
					"Http Comunication error!code-->" + put.getStatusCode(),
					null);
		} catch (Exception e) {
			logger.error("Http post occur error!url=" + url, e);
		} finally {
			put.releaseConnection();
		}
		return null;
	}

}
