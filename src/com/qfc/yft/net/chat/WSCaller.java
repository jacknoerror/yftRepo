package com.qfc.yft.net.chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import com.qfc.yft.YftValues;
import com.qfc.yft.vo.CimWSReturn;


/**
 * 接口 调用
 * 
 * @author zs.Bai
 * 
 */
public class WSCaller extends Thread {
	private Map<String, Object> wsParam;

	private CimWSReturn wsReturn;

	private IWSCallback callback;

	public void run() {
		httpClient();
	}

	private void httpClient() {
		wsReturn = null;
		BufferedReader reader = null;
		try {
			HttpClient client = new DefaultHttpClient();
//			client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,new HttpHost(YftValues.PROXY_HOST, YftValues.PROXY_PORT));//0226 taotao
			HttpUriRequest postMethod = postRequest(Config.API_URL
					+ "service/HttpService", wsParam);
			HttpResponse response = client.execute(postMethod);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				reader = new BufferedReader(new InputStreamReader(response
						.getEntity().getContent()));
				StringBuilder sb = new StringBuilder();
				for (String s = reader.readLine(); s != null; s = reader
						.readLine()) {
					sb.append(s);
				}
				String result = sb.toString();
				wsReturn = new CimWSReturn(result);
			}
			if (reader != null) {
				reader.close();
				reader = null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			wsReturn = null;
		} finally {
			callback.callback(wsReturn);

		}
	}

	private HttpUriRequest postRequest(String url, Map<String, Object> params) {
		HttpPost httppost = null;
		List<NameValuePair> listParams = new ArrayList<NameValuePair>();
		if (params != null) {
			for (String name : params.keySet()) {
				listParams.add(new BasicNameValuePair(name, params.get(name)
						.toString()));
			}
		}
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(listParams,
					HTTP.UTF_8);
			httppost = new HttpPost(url);
			httppost.addHeader("charset", HTTP.UTF_8);
			httppost.setEntity(entity);
		} catch (UnsupportedEncodingException e) {
			e.getMessage();
		}
		return httppost;

	}

	// private HttpUriRequest getRequest(String url) {
	// HttpGet request = new HttpGet(url);
	// return request;
	//
	// }

	private WSCaller(Map<String, Object> paramsMap, IWSCallback callback) {
		this.wsParam = paramsMap;
		this.callback = callback;
	}

	public static void call(String funcName, Map<String, Object> paramsMap,
			IWSCallback callback) {
		paramsMap.put("function", funcName);
		paramsMap.put("noPrefix", true);
		WSCaller wsCaller = new WSCaller(paramsMap, callback);
		wsCaller.start();

	}
}
