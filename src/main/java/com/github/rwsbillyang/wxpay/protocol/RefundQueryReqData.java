package com.github.rwsbillyang.wxpay.protocol;

import com.github.rwsbillyang.wxpay.WXPayManager;

public class RefundQueryReqData extends BaseReqData{
	
	@Override
	public int getTypeIndex() {
		return WXPayManager.TYPE_INDEX_REFUND_QUERY;
	}

    /**
     * 请求退款查询服务
     * @param transactionID 是微信系统为每一笔支付交易分配的订单号，通过这个订单号可以标识这笔交易，它由支付订单API支付成功时返回的数据里面获取到。建议优先使用
     * @param outTradeNo 商户系统内部的订单号,transaction_id 、out_trade_no 二选一，如果同时存在优先级：transaction_id>out_trade_no
     * @param deviceInfo 微信支付分配的终端设备号，与下单一致
     * @param outRefundNo 商户系统内部的退款单号，商户系统内部唯一，同一退款单号多次请求只退一笔
     * @param refundID 来自退款API的成功返回，微信退款单号refund_id、out_refund_no、out_trade_no 、transaction_id 四个参数必填一个，如果同事存在优先级为：refund_id>out_refund_no>transaction_id>out_trade_no
     */
    public RefundQueryReqData(String transactionID,String outTradeNo,String deviceInfo,
    		String outRefundNo,String refundID){
    	super();

    	this.put("transaction_id", transactionID);

        //商户系统自己生成的唯一的订单号
    	this.put("out_trade_no", outTradeNo);
    	this.put("out_refund_no", outRefundNo);
    	this.put("refund_id", refundID);
    	this.put("device_info", deviceInfo);
    }
    /**
     * 四选一
     * */
    public RefundQueryReqData(String transactionID,String outTradeNo,String outRefundNo,String refundID){
    	super();

    	this.put("transaction_id", transactionID);

        //商户系统自己生成的唯一的订单号
    	this.put("out_trade_no", outTradeNo);
    	this.put("out_refund_no", outRefundNo);
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

	public String getTransaction_id() {
		return (String) this.get("transaction_id");
	}

	public void setTransaction_id(String transaction_id) {
		this.put("transaction_id", transaction_id);
	}

	public String getOut_trade_no() {
		return (String) this.get("out_trade_no");
	}
	   /**
	    * 商户退款单号
	    * */
	public void setOut_trade_no(String out_trade_no) {
		this.put("out_trade_no", out_trade_no);
	}

	public String getOut_refund_no() {
		return (String) this.get("out_refund_no");
		
	}

	public void setOut_refund_no(String out_refund_no) {
		this.put("out_refund_no", out_refund_no);
	}

	public String getRefund_id() {
		return (String) this.get("refund_id");
	}
	 /**
	    * 微信退款单号
	    * */
	public void setRefund_id(String refund_id) {
		this.put("refund_id", refund_id);
	}



}
