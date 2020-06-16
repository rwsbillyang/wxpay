package com.github.rwsbillyang.wxpay.protocol;


import com.github.rwsbillyang.wxpay.WXPayManager;


public class ProfitShareReqData  extends BaseReqData{

	@Override
	public int getTypeIndex() {
		return WXPayManager.TYPE_INDEX_PROFIT_SHARE;
	}

	/**
	 * @param receivers 不超过50个json对象，不能设置分账方作为分账接受方
	 * */
	public ProfitShareReqData(String transaction_id, String out_order_no, String receiverListJson) {
		super();
		this.put("transaction_id", transaction_id);
		this.put("out_order_no", out_order_no);
		this.put("receivers", receiverListJson);
	}

	
	
}
