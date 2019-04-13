package com.github.rwsbillyang.wxpay.protocol;

import com.github.rwsbillyang.wxpay.WXPayManager;

/**
 * 该接口提供所有微信支付订单的查询，商户可以通过该接口主动查询订单状态，完成下一步的业务逻辑。
需要调用查询接口的情况：
◆ 当商户后台、网络、服务器等出现异常，商户系统最终未接收到支付通知；
◆ 调用支付接口后，返回系统错误或未知交易状态情况；
◆ 调用被扫支付API，返回USERPAYING的状态；
◆ 调用关单或撤销接口API之前，需确认支付状态；
 * */
public class OrderQueryReqData  extends BaseReqData{

	@Override
	public int getTypeIndex() {
		return WXPayManager.TYPE_INDEX_ORDER_QUERY;
	}
    /**
     * 请求支付查询服务
     * @param transactionID 是微信系统为每一笔支付交易分配的订单号，通过这个订单号可以标识这笔交易，它由支付订单API支付成功时返回的数据里面获取到。建议优先使用
     * @param outTradeNo 商户系统内部的订单号,transaction_id 、out_trade_no 二选一，如果同时存在优先级：transaction_id>out_trade_no
     * @return API返回的XML数据
     * @throws Exception
     */
    public OrderQueryReqData(String transactionID, String outTradeNo){
    	super();
    	this.put("transaction_id", transactionID);
    	this.put("out_trade_no", outTradeNo);
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

    public void setOut_trade_no(String out_trade_no) {
        this.put("out_trade_no", out_trade_no);
    }



}
