package com.github.rwsbillyang.wxpay.protocol;

import com.github.rwsbillyang.wxpay.WXPayManager;

/**
 * 支付交易返回失败或支付系统超时，调用该接口撤销交易。如果此订单用户支付失败，微信支付系统会将此订单关闭；如果用户支付成功，微信支付系统会将此订单资金退还给用户。
注意：7天以内的交易单可调用撤销，其他正常支付的单如需实现相同功能请调用申请退款API。提交支付交易后调用【查询订单API】，没有明确的支付结果再调用【撤销订单API】。

调用支付接口后请勿立即调用撤销订单API，建议支付后至少15s后再调用撤销订单接口。
 * */
public class ReverseReqData extends BaseReqData{
	@Override
	public int getTypeIndex() {
		return WXPayManager.TYPE_INDEX_REVERSE;
	}
    /**
     * 请求撤销服务
     * @param transactionID 是微信系统为每一笔支付交易分配的订单号，通过这个订单号可以标识这笔交易，它由支付订单API支付成功时返回的数据里面获取到。建议优先使用
     * @param outTradeNo 商户系统内部的订单号,transaction_id 、out_trade_no 二选一，如果同时存在优先级：transaction_id>out_trade_no
     * @return API返回的XML数据
     * @throws Exception
     */
    public ReverseReqData(String transactionID,String outTradeNo){
    	super();
    	if(transactionID!=null)
			this.put("transaction_id", transactionID);
		else
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
	   /**
	    * 商户退款单号
	    * */
	public void setOut_trade_no(String out_trade_no) {
		this.put("out_trade_no", out_trade_no);
	}





}
