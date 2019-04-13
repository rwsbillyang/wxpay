package com.github.rwsbillyang.wxpay.protocol;

import com.github.rwsbillyang.wxpay.WXPayManager;

/**
 * 请求对账单下载服务
 * @param deviceInfo 商户自己定义的扫码支付终端设备号，方便追溯这笔交易发生在哪台终端设备上,选填
 * @param billDate 下载对账单的日期，格式：yyyyMMdd 例如：20140603,必填
 * @param billType 账单类型
 *                 ALL，返回当日所有订单信息，默认值
SUCCESS，返回当日成功支付的订单
REFUND，返回当日退款订单
REVOKED，已撤销的订单
 */
public class DownloadBillReqData extends BaseReqData{
    //每个字段具体的意思请查看API文档
	@Override
	public int getTypeIndex() {
		return WXPayManager.TYPE_INDEX_DOWNLOADBILL;
	}
    private String device_info = null;
   
    private String bill_date = null;
    private String bill_type = "ALL";


    public DownloadBillReqData(String billDate){
    	super();
        this.put("bill_date", billDate);
    }
    public DownloadBillReqData(String billDate,String billType){
    	super();  
    	this.put("bill_date", billDate);
    	this.put("bill_type", billType);
    }
    public DownloadBillReqData(String deviceInfo,String billDate,String billType){
    	super(); 
    	this.put("device_info", deviceInfo);
    	this.put("bill_date", billDate);
    	this.put("bill_type", billType);
    }
    
    
    public String getDevice_info() {
        return (String) this.get("device_info");
    }

    public void setDevice_info(String device_info) {
    	this.put("device_info", device_info);
    }

    public String getBill_date() {
        return (String) this.get("bill_date");
    }

    public void setBill_date(String bill_date) {
    	this.put("bill_date", bill_date);
    }

    public String getBill_type() {
        return (String) this.get("bill_type");
    }

    public void setBill_type(String bill_type) {
        this.put("bill_type", bill_type);
    }



}
