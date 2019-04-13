package com.github.rwsbillyang.wxpay.protocol;

import com.github.rwsbillyang.wxpay.WXPayManager;

/**
 * 请求被扫支付API需要提交的数据
 */
public class ScanPayReqData extends BaseReqData {
	@Override
	public int getTypeIndex() {
		return WXPayManager.TYPE_INDEX_MICROPAY;
	}
	
	/**
	 * @param body
	 *            要支付的商品的描述信息，用户会在支付成功页面里看到这个信息
	 * @param outTradeNo
	 *            商户系统内部的订单号,32个字符内可包含字母, 确保在商户系统唯一
	 * @param totalFee
	 *            订单总金额，单位为“分”，只能整数
	 * @param spBillCreateIP
	 *            订单生成的机器IP
	 * @param authCode
	 *            这个是扫码终端设备从用户手机上扫取到的支付授权号，这个号是跟用户用来支付的银行卡绑定的，有效期是1分钟
	 */
	public ScanPayReqData(String body, String out_trade_no,
			int total_fee, String spbill_create_ip, 
			String auth_code) {
		super();
		this.put("fee_type", "CNY");
	
		this.put("body", body);
		this.put("out_trade_no", out_trade_no);
		this.put("total_fee", total_fee);
		this.put("spbill_create_ip", spbill_create_ip);
		this.put("auth_code", auth_code);
	}



	public String getAuth_code() {
		return (String) this.get("auth_code");
	}

	public void setAuth_code(String auth_code) {
		this.put("auth_code", auth_code);
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




	public String getGoods_tag() {
		return (String) this.get("goods_tag");
	}
	/**
	 * 商品标记，代金券或立减优惠功能的参数
	 * */
	public void setGoods_tag(String goods_tag) {
		this.put("goods_tag", goods_tag);
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


}
