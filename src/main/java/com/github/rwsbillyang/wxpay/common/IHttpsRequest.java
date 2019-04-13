
package com.github.rwsbillyang.wxpay.common;

/**
 * 向微信url发送请求字符串的类，可以自定义自己的HttpRequest
 * */
public interface IHttpsRequest {
	 public  String sendPost(String url, String postDataXML);
}
