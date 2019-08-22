package com.github.rwsbillyang.wxpay.protocol;

import com.github.rwsbillyang.wxpay.WXPayManager;

/**
 * 企业付款
 * https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=14_2
 * */
public class TransferReqData  extends BaseReqData{
	@Override
	public int getTypeIndex() {
		return WXPayManager.TYPE_INDEX_TRANSFER;
	}
	public final static int FORCE_CHECK=0;
	public final static int OPTION_CHECK=1;
	public final static int NO_CHECK=2;

	/**
	 * FORCE_CHECK
	 * */
	public TransferReqData(String partner_trade_no, String openid,
			String spbill_create_ip, int amount,String desc,int nameCheck,String realname) {
		super();
		
		setTransfer(true);
		
		this.put("partner_trade_no", partner_trade_no);
		this.put("openid", openid);
		
		
		this.put("spbill_create_ip", spbill_create_ip);
		this.put("amount", amount);
		switch(nameCheck)
		{
		case FORCE_CHECK:
			this.put("check_name", "FORCE_CHECK");
			this.put("re_user_name", realname);
			break;
		case OPTION_CHECK:
			this.put("check_name", "OPTION_CHECK");
			this.put("re_user_name", realname);
			break;
		case NO_CHECK:
			this.put("check_name", "NO_CHECK");
		default:
			break;
		}

		this.put("desc", desc);
	}
	

	
	
	public String getPartner_trade_no() {
		return (String) this.get("partner_trade_no") ;
	}
	public void setPartner_trade_no(String partner_trade_no) {
		this.put("partner_trade_no", partner_trade_no);
	}
	public String getOpenid() {
		return (String) this.get("openid");
	}
	public void setOpenid(String openid) {
		this.put("openid", openid);
	}
	public String getSpbill_create_ip() {
		return (String) this.get("spbill_create_ip");
	}
	public void setSpbill_create_ip(String spbill_create_ip) {
		this.put("spbill_create_ip", spbill_create_ip);
	}
	public int getAmount() {
		return Integer.parseInt((String) get("amount")) ; 
	}
	public void setAmount(int amount) {
		this.put("amount", amount);
	}
	
}
