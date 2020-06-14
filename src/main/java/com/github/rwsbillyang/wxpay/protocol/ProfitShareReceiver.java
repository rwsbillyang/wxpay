package com.github.rwsbillyang.wxpay.protocol;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfitShareReceiver {
	public final static String TYPE_MERCHANT = "MERCHANT_ID";
	public final static String TYPE_PERSONAL = "PERSONAL_OPENID";
	
	public String type;
	public String account;
	public int amount;
	public String description;
	
	
	public ProfitShareReceiver() {
		super();
	}


	public ProfitShareReceiver(String type, String account, int amount, String description) {
		super();
		this.type = type;
		this.account = account;
		this.amount = amount;
		this.description = description;
	}
	
	
}
