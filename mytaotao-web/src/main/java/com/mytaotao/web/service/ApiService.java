package com.mytaotao.web.service;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liqiyu on 2016/08/15 11:00.
 */
@Service
public class ApiService implements BeanFactoryAware {
    private BeanFactory beanFactory;
    @Autowired
    private RequestConfig requestConfig;

    public String doget(String URL) throws IOException {
        HttpGet httpGet = new HttpGet(URL);
        httpGet.setConfig(requestConfig);
        CloseableHttpResponse closeableHttpResponse = null;
        closeableHttpResponse = getClient().execute(httpGet);
        try {
            if (closeableHttpResponse.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(closeableHttpResponse.getEntity(), "utf-8");
            }
        } finally {
            if (closeableHttpResponse != null) {
                closeableHttpResponse.close();
            }
        }
        return null;

    }

    public String doget(String URL, Map<String, String> params) throws URISyntaxException, IOException {
        URIBuilder uriBuilder = new URIBuilder(URL);
        for (Map.Entry<String, String> stringStringEntry : params.entrySet()) {
            uriBuilder.setParameter(stringStringEntry.getKey(), stringStringEntry.getValue());
        }
        return doget(uriBuilder.toString());
    }

    /**
     * 带有参数的POST请求
     *
     * @param url
     * @param params
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public Map<Object, Object> doPost(String url, Map<String, String> params) throws
            IOException {
        Map<Object, Object> map = new HashMap<Object, Object>();
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(this.requestConfig);
        if (null != params) {
            List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            // 构造一个form表单式的实体
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters);
            // 将请求实体设置到httpPost对象中
            httpPost.setEntity(formEntity);
        }

        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = getClient().execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (null == entity) {
                map.put(response.getStatusLine().getStatusCode(), null);
                return map;
            }
            map.put(response.getStatusLine().getStatusCode(), EntityUtils.toString(
                    response.getEntity(), "UTF-8"));
            return map;
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    /**
     * 带有json参数的POST请求
     *
     * @param url
     * @param
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public Map<Object, Object> doPostJson(String url, String json) throws
            IOException {
        Map<Object, Object> map = new HashMap();
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(this.requestConfig);
        if (null != json) {
            StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
            // 将请求实体设置到httpPost对象中
            httpPost.setEntity(stringEntity);
        }

        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = getClient().execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (null == entity) {
                map.put(response.getStatusLine().getStatusCode(), null);
                return map;
            }
            map.put("data", EntityUtils.toString(
                    response.getEntity(), "UTF-8"));
            return map;
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    /**
     * 没有参数的POST请求
     *
     * @param url
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public Map<Object,Object> doPost(String url) throws IOException {
        return this.doPost(url, null);
    }

    private CloseableHttpClient getClient() {
        return beanFactory.getBean(CloseableHttpClient.class);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
