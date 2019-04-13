/*
 * Copyright 2016 BillYang<24272238@qq.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.rwsbillyang.wxpay.payconfig;

/**
 * 微信支付配置实体类接口，SDK的使用者需要写一个子类，里面保存着支付配置信息；
 * 当系统启动初始化SDK时，将配置信息保存到SDK中，参见WXPayManager.initConfigure函数
 * */
public interface  PayConfigBeanInterface {

	/**
	 * 支付配置子类实体需返回公众号AppId
	 * */
	public  String getAppId();
	
	/**
	 * 支付配置子类实体需返回商户号Id
	 * */
	public  String getMchId(); 
	
	/**
	 * 支付配置子类实体需返回商户号加密秘钥
	 * */
	public  String getMchSecretKey(); 
	
	/**
	 * 支付配置子类实体需返回商户号证书密码
	 * */
	public String getCertPwd();
	/**
	 * 支付配置子类实体需返回证书文件路径或证件二进制数据（二选一，另一个返回null，两者都提供SDK将优先使用二进制）
	 * */
	public  String getCertFile();
	
	/**
	 * 支付配置子类实体需返回证书文件路径或证件二进制数据（二选一，另一个返回null，两者都提供SDK将优先使用二进制）
	 * 
	 * */
	public  byte[] getCertBytes(); 
}
