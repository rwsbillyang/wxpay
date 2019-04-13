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
* Create date: 2 Apr, 2016 11:23:38 pm
* Description: TODO
*
*/
package com.github.rwsbillyang.wxpay.protocol;

import java.util.HashMap;

import com.github.rwsbillyang.wxpay.common.RandomStringGenerator;
import com.github.rwsbillyang.wxpay.common.Signature;
import com.github.rwsbillyang.wxpay.payconfig.WxPayConfigsCache;


public abstract class BaseReqData extends HashMap<String,Object>{


	
    public abstract int getTypeIndex();



	public BaseReqData() {
		super();
	}

    public void generateSign()
    {
    	this.put("appid", WxPayConfigsCache.getAppId());
    	this.put("mch_id", WxPayConfigsCache.getMchId());
    	this.put("nonce_str", RandomStringGenerator.getRandomStringByLength(32));
		this.put("sign", Signature.getSign(this,WxPayConfigsCache.getMchSecretKey())) ;
    }

	/**
	 * 整个系统有多个配置
	 * */
    public void generateSign(Integer indexKey)
    {
    	this.put("appid", WxPayConfigsCache.getAppId(indexKey));
    	this.put("mch_id", WxPayConfigsCache.getMchId(indexKey));
    	this.put("nonce_str", RandomStringGenerator.getRandomStringByLength(32));
		this.put("sign", Signature.getSign(this,WxPayConfigsCache.getMchSecretKey(indexKey))) ;
    }
  

	public String getAppid() {
		return (String) this.get("appid");
	}


	public void setAppid(String appid) {
		this.put("appid", appid);
	}


	public String getMch_id() {
		return (String) this.get("mch_id");
	}


	public void setMch_id(String mch_id) {
		this.put("mch_id", mch_id);
	}


	public String getNonce_str() {
		return (String) this.get("nonce_str");
	}


	public void setNonce_str(String nonce_str) {
		this.put("nonce_str", nonce_str);
	}


	public String getSign() {
		return (String) this.get("sign");
	}

}
