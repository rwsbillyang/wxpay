package com.github.rwsbillyang.wxpay.protocol;

import java.util.List;

import com.github.rwsbillyang.wxpay.WXPayManager;


public class ProfitShareReqData  extends BaseReqData{

	@Override
	public int getTypeIndex() {
		return WXPayManager.TYPE_INDEX_PROFIT_SHARE;
	}

	public ProfitShareReqData(String transaction_id, String out_order_no, List<ProfitShareReceiver> receivers) {
		super();
		this.put("transaction_id", transaction_id);
		this.put("out_order_no", out_order_no);
		this.put("receivers", receivers);
	}

	
	
}
