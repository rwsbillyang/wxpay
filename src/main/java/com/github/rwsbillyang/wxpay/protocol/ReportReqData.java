package com.github.rwsbillyang.wxpay.protocol;

import com.github.rwsbillyang.wxpay.WXPayManager;

public class ReportReqData extends BaseReqData{
	
	@Override
	public int getTypeIndex() {
		return WXPayManager.TYPE_INDEX_REPORT;
	}
    /**
     * 请求统计上报API
     * @param deviceInfo 微信支付分配的终端设备号，商户自定义
     * @param interfaceUrl 上报对应的接口的完整URL，类似： https://api.mch.weixin.qq.com/pay/unifiedorder
     * @param executeTimeCost 接口耗时情况，单位为毫秒
     * @param returnCode API返回的对应字段
     * @param returnMsg API返回的对应字段
     * @param resultCode API返回的对应字段
     * @param errCode API返回的对应字段
     * @param errCodeDes API返回的对应字段
     * @param outTradeNo API返回的对应字段
     * @param userIp 发起接口调用时的机器IP
     */
    public ReportReqData(String deviceInfo, String interfaceUrl,int executeTimeCost,
    		String returnCode,String returnMsg,String resultCode,String errCode,String errCodeDes,
    		String outTradeNo,String userIp){
    	super();
        setDevice_info(deviceInfo);
        setInterface_url(interfaceUrl);
        setExecute_time_(executeTimeCost);
        setReturn_code(returnCode);
        setReturn_msg(returnMsg);
        setResult_code(resultCode);
        setErr_code(errCode);
        setErr_code_des(errCodeDes);
        setUser_ip(userIp);
        setTime(getTime());
    }



	public String getDevice_info() {
		return (String) this.get("device_info");
	}
	/**
	 * 设备号.终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB"
	 * */
	public void setDevice_info(String device_info) {
		this.put("device_info", device_info);
	}


	public String getInterface_url() {
		return (String) this.get("interface_url");
	}
	/**
	 *  上报对应的接口的完整URL，类似：https://api.mch.weixin.qq.com/pay/unifiedorder
	 * */
	public void setInterface_url(String interface_url) {
		
	}

    public Integer getExecute_time_() {
    	return Integer.parseInt((String) get("execute_time_")) ;
    }

    public void setExecute_time_(int execute_time) {
    	this.put("execute_time_", execute_time);
    }

    public String getReturn_code() {
        return (String) this.get("return_code"); 
    }

    public void setReturn_code(String return_code) {
    	this.put("return_code", return_code);
        
    }

    public String getReturn_msg() {
        return (String) this.get("return_msg");
    }

    public void setReturn_msg(String return_msg) {
    	this.put("return_msg", return_msg);
    }

    public String getResult_code() {
        return (String) this.get("result_code");
    }

    public void setResult_code(String result_code) {
    	this.put("result_code", result_code);
    }

    public String getErr_code() {
        return (String) this.get("err_code");
    }

    public void setErr_code(String err_code) {
    	this.put("err_code", err_code);
    }

    public String getErr_code_des() {
        return (String) this.get("err_code_des");
    }

    public void setErr_code_des(String err_code_des) {
    	this.put("err_code_des", err_code_des);
    }

    public String getOut_trade_no() {
        return (String) this.get("out_trade_no");
    }

    public void setOut_trade_no(String out_trade_no) {
    	this.put("out_trade_no", out_trade_no);
    }

    public String getUser_ip() {
        return (String) this.get("user_ip");
    }
/**
 * 发起接口调用时的机器IP
 * */
    public void setUser_ip(String user_ip) {
    	this.put("user_ip", user_ip);
    }

    public String getTime() {
        return (String) this.get("time");
    }
/**
 * 上报该统计请求时的系统时间，格式为yyyyMMddHHmmss
 * */
    public void setTime(String time) {
    	this.put("time", time);
    }


}
