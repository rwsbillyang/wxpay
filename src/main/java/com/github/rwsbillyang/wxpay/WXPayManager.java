/*
 * Copyright 2016 BillYang<24272238@qq.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.rwsbillyang.wxpay;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import com.github.rwsbillyang.wxpay.common.HttpsRequest;
import com.github.rwsbillyang.wxpay.common.IHttpsRequest;
import com.github.rwsbillyang.wxpay.payconfig.PayConfigBeanInterface;
import com.github.rwsbillyang.wxpay.payconfig.WxPayConfigsCache;
import com.github.rwsbillyang.wxpay.protocol.BaseReqData;

import lombok.extern.slf4j.Slf4j;

/**
 * 在整个系统运行初始化时，根据自己情况调用对应版本的initConfigure初始化支付配置缓存，SDK需要配置时将从缓存中调取这些配置信息。
 * 某些系统若需要支持多个站点，请用hashmap保存后再设置到缓存中。
 * 证书请上传apiclient_cert.p12是商户证书文件，除PHP外的开发均使用此证书文件，可以以文件的形式提供，也可以提供二进制流。
 * 注意：在使用过程中，更新支付配置时，需重启系统生效，或者更新重新调用initConfigure更新缓存，才会使配置生效。
 * */
@Slf4j
public class WXPayManager {

	
	public final static int TYPE_INDEX_ORDER_UNIFIELD=0;
	public final static int TYPE_INDEX_ORDER_QUERY=1;
	public final static int TYPE_INDEX_ORDER_CLOSE=2;
	public final static int TYPE_INDEX_REVERSE=3;
	public final static int TYPE_INDEX_REFUND=4;
	public final static int TYPE_INDEX_REFUND_QUERY=5;
	public final static int TYPE_INDEX_DOWNLOADBILL=6;
	public final static int TYPE_INDEX_REPORT=7;
	public final static int TYPE_INDEX_MICROPAY=8;
	public final static int TYPE_INDEX_TRANSFER=9;
	public final static int TYPE_INDEX_PROFIT_SHARE=10;
	
	/**
	 * 请求地址数组，添加新API时需要添加数组元素以及上面的索引，以及实现一个BaseReqData的子类并为其配置索引
	 * */
	public final static String[] REQUEST_API_ARRAY={
			"https://api.mch.weixin.qq.com/pay/unifiedorder",// 0)统一下单
			"https://api.mch.weixin.qq.com/pay/orderquery",// 1）订单查询API
			"https://api.mch.weixin.qq.com/pay/closeorder",// 2）订单关闭API
			"https://api.mch.weixin.qq.com/secapi/pay/reverse",// 3）撤销API
			"https://api.mch.weixin.qq.com/secapi/pay/refund",// 4）退款API
			"https://api.mch.weixin.qq.com/pay/refundquery", // 5）退款查询API
			"https://api.mch.weixin.qq.com/pay/downloadbill", // 6）下载对账单API
			"https://api.mch.weixin.qq.com/payitil/report",  // 7) 统计上报API
			"https://api.mch.weixin.qq.com/pay/micropay", //8）被扫支付API
			"https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers",// 9）企业付款
			"https://api.mch.weixin.qq.com/secapi/pay/profitsharing", //10）分账
	};
	
	/**
	 * 各API是否需要证书。需要证书的往往是后台管理功能，不需要的是用户支付及查询功能。
	 * 实现中，对于需要证书的后台管理功能，临时创建httpsRequest发送请求。而用户支付则是整个系统生命周期内，只有1个
	 * */
	public final static boolean[] NEED_CERT_ARRAY={
			false,// 0)统一下单
			false,// 1）订单查询API
			false,// 2）订单关闭API
			true,// 3）撤销API
			true,// 4）退款API
			false, // 5）退款查询API
			false, // 6）下载对账单API
			false,  // 7) 统计上报API
			false, //8）被扫支付API
			true,// 9）企业付款
			true,// 10）分账
	};

	private static WXPayManager instance = new WXPayManager();
	private IHttpsRequest httpsRequest = null;
	//private HashMap<Integer,IHttpsRequest> httpsRequestMap=null;
	


	private WXPayManager() {}

	public static WXPayManager getInstance() {
		if (instance == null) {
			instance = new WXPayManager();
		}
		return instance;
	}

	/**
	 * 整个运行系统中只有一个支付配置的情况。注意：需在整个系统初始化时调用此函数存储
	 * 对于需要证书的请求，需提供证书和证书密码
	 * @param bean
	 *            支付配置
	 * @param certLocalPath
	 *            支付配置证书文件路径
	 * @param certPassword
	 *            支付配置证书密码
	 * */
	public  void initConfigure(PayConfigBeanInterface bean) {
		if(bean==null)
			return;
		WxPayConfigsCache.setWxpayConfigBean(bean);
		httpsRequest =new HttpsRequest();
	}

	private boolean fileIsExist(String file) {
		if (file == null)
			return false;

		File f = new File(file);
		return f.exists() && !f.isDirectory();
	}

	/**
	 * 整个运行系统中有多个支付配置的情况。注意：需在整个系统初始化时调用此函数存储
	 * 
	 * @param beanMap
	 *            支付配置
	 * @param certLocalPath
	 *            支付配置证书文件路径
	 * @param certPassword
	 *            支付配置证书密码
	 * */
	public void initConfigure(HashMap<Integer, PayConfigBeanInterface> beanMap) {
		WxPayConfigsCache.setPayConfigBeanMap(beanMap);
		httpsRequest =new HttpsRequest();
	}
	/**
	 * 发送请求
	 * */
	public String sendRequest(BaseReqData reqData)
	{
		reqData.generateSign();
		int index=reqData.getTypeIndex();
		if(NEED_CERT_ARRAY[index])
		{
			PayConfigBeanInterface bean =WxPayConfigsCache.getPayConfigBean();
			IHttpsRequest hr = null;
			String certPwd=bean.getCertPwd();
			if(certPwd!=null)
				certPwd=bean.getMchId();
			if(bean.getCertBytes()!=null)
				hr =new HttpsRequest(bean.getCertBytes(), certPwd);
			else{
				if(fileIsExist(bean.getCertFile())){
					hr =new HttpsRequest(bean.getCertFile(), certPwd);
				}else
					hr = httpsRequest;
			}
			return hr.sendPost(REQUEST_API_ARRAY[index], convertMap2Xml(reqData));
		}else
		{
			return httpsRequest.sendPost(REQUEST_API_ARRAY[index], convertMap2Xml(reqData));
		}
		
	}
	/**
	 * 发送请求，多配置版本
	 * */
	public String sendRequest(BaseReqData reqData,Integer indexKey)
	{
		reqData.generateSign(indexKey);
		int index=reqData.getTypeIndex();
		if(NEED_CERT_ARRAY[index])
		{
			PayConfigBeanInterface bean =WxPayConfigsCache.getPayConfigBean(indexKey);
			IHttpsRequest hr = null;
			String certPwd=bean.getCertPwd();
			if(certPwd!=null)
				certPwd=bean.getMchId();
			if(bean.getCertBytes()!=null)
				hr =new HttpsRequest(bean.getCertBytes(), certPwd);
			else{
				if(fileIsExist(bean.getCertFile())){
					hr =new HttpsRequest(bean.getCertFile(), certPwd);
				}else
					hr = httpsRequest;
			}
			return hr.sendPost(REQUEST_API_ARRAY[index], convertMap2Xml(reqData));
		}else
		{
			return httpsRequest.sendPost(REQUEST_API_ARRAY[index], convertMap2Xml(reqData));
		}	
	}



	public IHttpsRequest getHttpsRequest() {
		return httpsRequest;
	}

	/**
	 * 用于指定自己自定义的HttpsRequest
	 * */
	public void setHttpsRequest(HttpsRequest httpsRequest) {
		this.httpsRequest = httpsRequest;
	}

	

	private String convertMap2Xml(HashMap<String, Object> hashMap) {
		if (hashMap.isEmpty()) {
			return "";
		}
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("<xml>\n");
		Iterator it = hashMap.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			Object value = hashMap.get(key);
			strBuffer.append("<" + key + ">" + (value == null ? "" : value)
					+ "</" + key + ">\n");
		}
		strBuffer.append("</xml>");
		String str=strBuffer.toString();
		log.info("request xml="+str);
		try {
			return new String(str.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		log.info(str);
		return str;

	}
	/**
     * 获取time:统计发送时间，格式为yyyyMMddHHmmss，如2009年12 月25 日9 点10 分10 秒表示为20091225091010。时区为GMT+8 beijing。
     * @return 订单生成时间
     */
    private static String getTime(){
        //订单生成时间自然就是当前服务器系统时间咯
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.format(new Date());
    }
}
