package com.github.rwsbillyang.wxpay.common;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpsRequest implements IHttpsRequest{

    public interface ResultListener {
        public void onConnectionPoolTimeoutError();
    }


    //连接超时时间，默认10秒
    private  int socketTimeout = 10000;

    //传输超时时间，默认30秒
    private  int connectTimeout = 30000;

    //请求器的配置
    private  RequestConfig requestConfig;

    //HTTP请求器
    private  CloseableHttpClient httpClient;

    /**
     * 不需要证书时的构造函数。注意：当新上传了证书时要么重启系统生效，要么重新initCofigure才能生效
     * 
     * */
    public HttpsRequest() {
		super();
		requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
		httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
	}
	/**
     * cert证书以文件的形式传递过来
     * */
    public HttpsRequest(String certFile,String certPwd) {
    	try {
			init(certFile,certPwd);
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    /**
     * cert证书以byte形式传递过来
     * */
    public HttpsRequest(byte[] certData,String certPwd) {
    	try {
    		ByteArrayInputStream is = new ByteArrayInputStream(certData);
			init(is,certPwd);
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    
    private  void init(String certFile,String certPwd) throws IOException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException {
    	FileInputStream instream = new FileInputStream(new File(certFile));//加载本地的证书进行https加密传输
    	init(instream,certPwd);
    }
    
    private  void init(InputStream instream,String certPwd) throws IOException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException {

        KeyStore keyStore = KeyStore.getInstance("PKCS12");    
        try {
            keyStore.load(instream, certPwd.toCharArray());//设置证书密码
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            instream.close();
        }

        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, certPwd.toCharArray())
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[]{"TLSv1"},
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

        httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();

        //根据默认超时限制初始化requestConfig
        requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();

        
    }
    /**
     * 通过Https往API post xml数据
     *
     * @param url    API地址
     * @param postDataXML 要提交的XML字符串
     * @return API回包的实际数据
     * @throws IOException
     * @throws KeyStoreException
     * @throws UnrecoverableKeyException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public  String sendPost(String url, String postDataXML){

        String result = null;

        HttpPost httpPost = new HttpPost(url);


        //得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
        StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);

        //设置请求器的配置
        httpPost.setConfig(requestConfig);

        log.info("executing request" + httpPost.getRequestLine());

        try {
            HttpResponse response = httpClient.execute(httpPost);

            HttpEntity entity = response.getEntity();

            result = EntityUtils.toString(entity, "UTF-8");

        } catch (ConnectionPoolTimeoutException e) {
            log.error("http get throw ConnectionPoolTimeoutException(wait time out)");

        } catch (ConnectTimeoutException e) {
            log.error("http get throw ConnectTimeoutException");

        } catch (SocketTimeoutException e) {
            log.error("http get throw SocketTimeoutException");

        } catch (Exception e) {
            log.error("http get throw Exception");

        } finally {
            httpPost.abort();
        }

        return result;
    }
    
    
    /**
     * 设置连接超时时间
     *
     * @param socketTimeout 连接时长，默认10秒
     */
    public void setSocketTimeout(int socketTimeout) {
    	this.socketTimeout = socketTimeout;
        resetRequestConfig();
    }

    /**
     * 设置传输超时时间
     *
     * @param connectTimeout 传输时长，默认30秒
     */
    public void setConnectTimeout(int connectTimeout) {
    	this.connectTimeout = connectTimeout;
        resetRequestConfig();
    }

    private void resetRequestConfig(){
        requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
    }

    /**
     * 允许商户自己做更高级更复杂的请求器配置
     *
     * @param requestConfig 设置HttpsRequest的请求器配置
     */
    public void setRequestConfig(RequestConfig requestConfig) {
    	this.requestConfig = requestConfig;
    }
}
