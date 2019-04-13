/**  
* @Copyright  www.redwolf-soft.com Inc.  All rights reserved. 
* This software is the confidential and proprietary   
* information of RedWolf Software.   
* ("Confidential Information"). You shall not disclose   
* such Confidential Information and shall use it only  
* in accordance with the terms of the contract agreement   
* you entered into with  RedWolf Software.  

* http://www.redwolf-soft.com

* @author:ycg<billyang@redwolf-soft.com>,24272238@qq.com
* Create date: 3 Apr, 2016 1:10:35 pm
* Description: TODO
*
*/
package com.github.rwsbillyang.wxpay.protocol;

import com.github.rwsbillyang.wxpay.WXPayManager;

/**
 * 以下情况需要调用关单接口：商户订单支付失败需要生成新单号重新发起支付，要对原订单号调用关单，避免重复支付；系统下单后，用户支付超时，系统退出不再受理，避免用户继续，请调用关单接口。
 * 注意：订单生成后不能马上调用关单接口，最短调用时间间隔为5分钟。
 * */
public class OrderCloseReqData extends BaseReqData{
	@Override
	public int getTypeIndex() {
		return WXPayManager.TYPE_INDEX_ORDER_CLOSE;
	}
	
	/**
	 * 商户订单号,商户系统内部的订单号
	 * */
	public OrderCloseReqData(String out_trade_no) {
		super();
		this.put("out_trade_no", out_trade_no);
	}

	public String getOut_trade_no() {
		return (String) this.get("out_trade_no");
	}

	public void setOut_trade_no(String out_trade_no) {
		this.put("out_trade_no", out_trade_no);
	}
	
	
}
