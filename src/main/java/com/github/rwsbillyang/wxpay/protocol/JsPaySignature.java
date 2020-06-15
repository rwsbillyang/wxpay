/**  
* @Copyright  www.redwolf-soft.com Inc.  All rights reserved. 
* This software is the confidential and proprietary   
* information of RedWolf Software.   
* ("Confidential Information"). You shall not disclose   
* such Confidential Information and shall use it only  
* in accordance with the terms of the contract agreement   
* you entered into with  RedWolf Software.  

* http://www.redwolf-soft.com

* @author:ycg<billyang@redwolf-soft.com>,24272238@qq.com
* Create date: 5 Apr, 2016 5:14:46 pm
* Description: TODO
*
*/
package com.github.rwsbillyang.wxpay.protocol;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.github.rwsbillyang.wxpay.common.RandomStringGenerator;
import com.github.rwsbillyang.wxpay.common.Signature;
import com.github.rwsbillyang.wxpay.payconfig.WxPayConfigsCache;

/**
 * 
 * Weixin js pay的API，直接用构造函数生成js请求所需的数据，至少需要提供prepayId 
 * https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
 * 
 * */
public class JsPaySignature
{
	private String appId = null;
	private long timeStamp = 0L;
	private String nonceStr = null;
	private String prepay_id = null;
	private String signType = Signature.TYPE_MD5;
	private String paySign = null;
	
	public JsPaySignature(String prepay_id) {
		super();
		this.prepay_id = prepay_id;
		appId=WxPayConfigsCache.getAppId();
		 //当前时间的秒数
		timeStamp = System.currentTimeMillis() / 1000;
		nonceStr=RandomStringGenerator.getRandomStringByLength(32);
		paySign = Signature.getSign(toMap(),WxPayConfigsCache.getMchSecretKey(),signType);
	}
	
	/**
	 * 自定义timeStamp和nonceStr版本
	 * */
	public JsPaySignature(long timeStamp, String nonceStr, String prepay_id) {
		super();
		this.timeStamp = timeStamp;
		this.nonceStr = nonceStr;
		this.prepay_id = prepay_id;
		appId=WxPayConfigsCache.getAppId();
		paySign = Signature.getSign(toMap(),WxPayConfigsCache.getMchSecretKey(),signType);
	}

	/**
	 * 多配置版本
	 * */
	public JsPaySignature(String prepay_id,Integer indexKey) {
		super();
		this.prepay_id = prepay_id;
		appId=WxPayConfigsCache.getAppId(indexKey);
		 //当前时间的秒数
		timeStamp = System.currentTimeMillis() / 1000;
		nonceStr=RandomStringGenerator.getRandomStringByLength(32);
		paySign = Signature.getSign(toMap(),WxPayConfigsCache.getMchSecretKey(indexKey),signType);
	}
	
	/**
	 * 自定义timeStamp和nonceStr多配置版本
	 * */
	public JsPaySignature(long timeStamp, String nonceStr, String prepay_id,Integer indexKey) {
		super();
		this.timeStamp = timeStamp;
		this.nonceStr = nonceStr;
		this.prepay_id = prepay_id;
		appId=WxPayConfigsCache.getAppId(indexKey);
		paySign = Signature.getSign(toMap(),WxPayConfigsCache.getMchSecretKey(indexKey),signType);
	}
	
	
	public String getAppId() {
		return appId;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public String getPrepay_id() {
		return prepay_id;
	}

	public String getSignType() {
		return signType;
	}

	public String getPaySign() {
		return paySign;
	}

	public Map<String,Object> toMap(){
		
        Map<String,Object> map = new HashMap<String, Object>();
        
        
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object obj;
            try {
                obj = field.get(this);
                if(obj!=null){
                	if(field.getName().equals("prepay_id"))
                	{
                		map.put("package","prepay_id="+obj);
                	}else
                		map.put(field.getName(), obj);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
