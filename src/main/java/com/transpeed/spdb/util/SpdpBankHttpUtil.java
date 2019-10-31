package com.transpeed.spdb.util;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SpdpBankHttpUtil {

    /**
     * 执行POST请求
     *
     * @param url
     * @param content
     * @param contentType
     * @return
     * @throws IOException
     */
    public HttpResult doPostWithoutKey(String url, String content, String contentType) throws IOException {

        return doPostWithEncode( url, content, contentType, null );
    }

    /**
     * 执行POST请求
     *
     * @param url
     * @param content
     * @param contentType
     * @return
     * @throws IOException
     */
    public HttpResult doPostWithEncode(String url, String content, String contentType, String encode) throws IOException {
        // 创建http POST请求
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost( url );
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout( 20000 )
                .setConnectionRequestTimeout( 50000 )
                .build();
        httpPost.setConfig( requestConfig );

        if (encode == null) {
            encode = "GB18030";
        }

        httpPost.setEntity( new StringEntity( content, encode ) );

        httpPost.setHeader( "Content-Type", contentType );

        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = httpClient.execute( httpPost );
            return new HttpResult( response.getStatusLine().getStatusCode(),
                    EntityUtils.toString( response.getEntity(), "GB18030" ) );
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    /**
     * 执行POST请求到银行
     *
     * @param url
     * @param content
     * @return
     * @throws IOException
     */
    public HttpResult doPostToBank(String url, String content) throws IOException {
        // 创建http POST请求
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost( url );
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout( 20000 )
                .setConnectionRequestTimeout( 50000 )
                .build();
        httpPost.setConfig( requestConfig );

        httpPost.setEntity( new StringEntity( content ) );

        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = httpClient.execute( httpPost );
            return new HttpResult( response.getStatusLine().getStatusCode(), EntityUtils.toString( response.getEntity(), "GB18030" ) );
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }
}
