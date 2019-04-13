
package com.github.rwsbillyang.wxpay.business;

import com.github.rwsbillyang.wxpay.payconfig.PayConfigBeanInterface;

public class WxPayConfigBeanExample implements PayConfigBeanInterface {

	@Override
	public String getAppId() {
		return "my_app_id_str";
	}

	@Override
	public String getMchId() {
		return "my_mch_id_str";
	}

	@Override
	public String getMchSecretKey() {
		return "my_mch_secret_key";
	}

	@Override
	public String getCertFile() {
		return "my_app_cert_file_path";
	}

	@Override
	public byte[] getCertBytes() {
		return null;
	}

	@Override
	public String getCertPwd() {
		return "my_app_cert_file_pwd";
	}

}
