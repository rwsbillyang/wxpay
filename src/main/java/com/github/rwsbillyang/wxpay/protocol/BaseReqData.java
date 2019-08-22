/**  
* @Copyright  http://www.apache.org/licenses/LICENSE-2.0.html

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
    
    private boolean isTransfer = false;
	private String APPID = "appid"; 
	private String MCHID = "mch_id"; 
    public BaseReqData() {
		super();
	}
    /**
     * 是否是企业付款，因为其字段不同
     * https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=14_2
     * */
	public boolean isTransfer() {
		return isTransfer;
	}

	/**
     * 企业付款时，mch_appid代替appid，mchid代替mch_id
     * */
	public void setTransfer(boolean isTransfer) {
		this.isTransfer = isTransfer;
		if(isTransfer)
		{
			APPID = "mch_appid";
			MCHID = "mchid";
		}else
		{
			APPID = "appid"; 
			MCHID = "mch_id"; 
		}
	}

    public void generateSign()
    {
    	this.put(APPID, WxPayConfigsCache.getAppId());
    	this.put(MCHID, WxPayConfigsCache.getMchId());
    	this.put("nonce_str", RandomStringGenerator.getRandomStringByLength(32));
		this.put("sign", Signature.getSign(this,WxPayConfigsCache.getMchSecretKey())) ;
    }

	/**
	 * 整个系统有多个配置
	 * */
    public void generateSign(Integer indexKey)
    {
    	this.put(APPID, WxPayConfigsCache.getAppId(indexKey));
    	this.put(MCHID, WxPayConfigsCache.getMchId(indexKey));
    	this.put("nonce_str", RandomStringGenerator.getRandomStringByLength(32));
		this.put("sign", Signature.getSign(this,WxPayConfigsCache.getMchSecretKey(indexKey))) ;
    }


	public String getAppid() {
		return (String) this.get(APPID);
	}


	public void setAppid(String appid) {
		this.put(APPID, appid);
	}


	public String getMch_id() {
		return (String) this.get(MCHID);
	}


	public void setMch_id(String mch_id) {
		this.put(MCHID, mch_id);
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
