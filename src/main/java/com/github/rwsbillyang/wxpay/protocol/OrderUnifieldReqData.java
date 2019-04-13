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
* Create date: 2 Apr, 2016 10:42:34 pm
* Description: TODO
*
*/
package com.github.rwsbillyang.wxpay.protocol;

import com.github.rwsbillyang.wxpay.WXPayManager;

/**
 * 统一下单请求数据，参见https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1#
 * */
public class OrderUnifieldReqData extends BaseReqData{
	
	@Override
	public int getTypeIndex() {
		return WXPayManager.TYPE_INDEX_ORDER_UNIFIELD;
	}
	
	/**
	 * @param body 商品描述
	 * @param out_trade_no 商户系统内部的订单号,32个字符内、可包含字母
	 * @param total_fee 订单总金额，单位为分
	 * @param spbill_create_ip APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP
	 * @param notify_url 接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数
	 * @param trade_type 取值如下：JSAPI，NATIVE，APP
	 * */
	public OrderUnifieldReqData(String body, String out_trade_no,
			int total_fee, String spbill_create_ip, 
			String notify_url, String trade_type) {
		super();
		this.put("fee_type", "CNY");
	
		this.put("body", body);
		this.put("out_trade_no", out_trade_no);
		this.put("total_fee", total_fee);
		this.put("spbill_create_ip", spbill_create_ip);
		this.put("notify_url", notify_url);
		this.put("trade_type", trade_type);
	}
	/**
	 * trade_type=JSAPI，openId参数必传，用户在商户appid下的唯一标识
	 * */
	public OrderUnifieldReqData(String body, String out_trade_no,
			int total_fee, String spbill_create_ip, 
			String notify_url, String trade_type,String openId) {
		super();
		
		this.put("fee_type", "CNY");
	
		this.put("body", body);
		this.put("out_trade_no", out_trade_no);
		this.put("total_fee", total_fee);
		this.put("spbill_create_ip", spbill_create_ip);
		this.put("notify_url", notify_url);
		this.put("trade_type", trade_type);
		this.put("openid", openId);
	}
	/**
	 * trade_type=NATIVE，productId参数必传。此id为二维码中包含的商品ID，商户自行定义。
	 * */
	public OrderUnifieldReqData(String body, String out_trade_no,
			int total_fee, String spbill_create_ip, 
			String notify_url, String trade_type,String openId,String productId) {
		super();
		this.put("fee_type", "CNY");
	
		this.put("body", body);
		this.put("out_trade_no", out_trade_no);
		this.put("total_fee", total_fee);
		this.put("spbill_create_ip", spbill_create_ip);
		this.put("notify_url", notify_url);
		this.put("trade_type", trade_type);
		if(openId!=null)
			this.put("openid", openId);
		this.put("product_id", productId);
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

	public String getBody() {
		return (String) this.get("body");
	}
	/**
	 * 商品描述
	 * */
	public void setBody(String body) {
		this.put("body", body);
	}

	public String getDetail() {
		return (String) this.get("detail");
	}
	/**
	 * 商品详情
	 * */
	public void setDetail(String detail) {
		this.put("detail", detail);
	}

	public String getAttach() {
		return (String) this.get("attach");
	}
	/**
	 * 附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
	 * */
	public void setAttach(String attach) {
		this.put("attach", attach);
	}

	public String getOut_trade_no() {
		return (String) this.get("out_trade_no");
	}
	/**
	 * 商户系统内部的订单号,32个字符内、可包含字母
	 * 商户支付的订单号由商户自定义生成，微信支付要求商户订单号保持唯一性（建议根据当前系统时间加随机序列来生成订单号）。
	 * 重新发起一笔支付要使用原订单号，避免重复支付；已支付过或已调用关单、撤销（请见后文的API列表）的订单号不能重新发起支付。
	 * */
	public void setOut_trade_no(String out_trade_no) {
	
		this.put("out_trade_no", out_trade_no);
	}

	public String getFee_type() {
		return (String) this.get("fee_type");
	}
	/**
	 * 符合ISO 4217标准的三位字母代码，默认人民币：CNY
	 * */
	public void setFee_type(String fee_type) {
		this.put("fee_type", fee_type);
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

	public String getSpbill_create_ip() {
		return (String) this.get("spbill_create_ip");
	}
	/**
	 * APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP
	 * */
	public void setSpbill_create_ip(String spbill_create_ip) {
		this.put("spbill_create_ip", spbill_create_ip);
	}

	public String getTime_start() {
		return (String) this.get("time_start");
	}
	/**
	 * 订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010
	 * */
	public void setTime_start(String time_start) {
		
		this.put("time_start", time_start);
	}

	public String getTime_expire() {
		return (String) this.get("time_expire");
	}
	/**
	 * 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则
	 * 注意：最短失效时间间隔必须大于5分钟
	 * */
	public void setTime_expire(String time_expire) {
		this.put("time_expire", time_expire);
	}

	public String getGoods_tag() {
		return (String) this.get("goods_tag");
	}
	/**
	 * 商品标记，代金券或立减优惠功能的参数
	 * */
	public void setGoods_tag(String goods_tag) {
		this.put("goods_tag", goods_tag);
	}

	public String getNotify_url() {
		return (String) this.get("notify_url");
	}
	/**
	 * 接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
	 * */
	public void setNotify_url(String notify_url) {
		this.put("notify_url", notify_url);
	}

	public String getTrade_type() {
		return (String) this.get("trade_type");
	}
	/**
	 * 交易类型 取值如下：JSAPI，NATIVE，APP
	 * */
	public void setTrade_type(String trade_type) {
		this.put("trade_type", trade_type);
	}

	public String getProduct_id() {
		return (String) this.get("product_id");
	}
	/**
	 * 商品ID trade_type=NATIVE，此参数必传。此id为二维码中包含的商品ID，商户自行定义。
	 * */
	public void setProduct_id(String product_id) {
		this.put("product_id", product_id);
	}

	public String getLimit_pay() {
		return (String) this.get("limit_pay");
	}
	/**
	 * 指定支付方式  no_credit--指定不能使用信用卡支付
	 * */
	public void setLimit_pay(String limit_pay) {
		this.put("limit_pay", limit_pay);
	}

	public String getOpenid() {
		return (String) this.get("openid");
	}
	/**
	 * 用户标识： rade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识。openid如何获取，可参考【获取openid】。
	 * 企业号请使用【企业号OAuth2.0接口】获取企业号内成员userid，再调用【企业号userid转openid接口】进行转换
	 * */
	public void setOpenid(String openid) {
		this.put("openid", openid);
	}

	
}
