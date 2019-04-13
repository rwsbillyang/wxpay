package com.github.rwsbillyang.wxpay.protocol;

import com.github.rwsbillyang.wxpay.WXPayManager;

/**
 * 用来存放退款订单数据
 */
public class RefundReqData extends BaseReqData{
	
	@Override
	public int getTypeIndex() {
		return WXPayManager.TYPE_INDEX_REFUND;
	}

/**
 * 微信订单号	transaction_id	商户订单号	out_trade_no 二选一	
 * */
	public RefundReqData(String transaction_id, String out_trade_no,
			String out_refund_no, Integer total_fee, Integer refund_fee,
			String op_user_id) {
		super();
		if(transaction_id!=null)
			this.put("transaction_id", transaction_id);
		else
			this.put("out_trade_no", out_trade_no);
		this.put("out_refund_no", out_refund_no);
		this.put("total_fee", total_fee);
		this.put("refund_fee", refund_fee);
		this.put("op_user_id", op_user_id);
		this.put("refund_fee_type", "CNY");
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
	public Integer getTotal_fee() {
		return Integer.parseInt((String) get("total_fee")) ;
	}
	/**
	 * 交易金额默认为人民币交易，接口中参数支付金额单位为【分】，参数值不能带小数。对账单中的交易金额单位为【元】。
	 * 外币交易的支付金额精确到币种的最小单位，参数值不能带小数点。
	 * */
	public void setTotal_fee(int total_fee) {
		this.put("total_fee", total_fee);
	}

	public Integer getRefund_fee() {
		return Integer.parseInt((String) get("refund_fee")) ;
	}
	public void setRefund_fee(Integer refund_fee) {
		this.put("refund_fee", refund_fee);
	}
	
	public String getRefund_fee_type() {
		return (String) this.get("refund_fee_type");
	}
	public void setRefund_fee_type(String refund_fee_type) {
		this.put("refund_fee_type", refund_fee_type);
	}

	public String getOp_user_id() {
		return (String) this.get("op_user_id");
	}
	public void setOp_user_id(String op_user_id) {
		this.put("op_user_id", op_user_id);
	}
}
