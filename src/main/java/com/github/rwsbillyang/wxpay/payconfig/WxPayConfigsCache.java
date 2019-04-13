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

import java.util.HashMap;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WxPayConfigsCache {

	/**
	 * 整个系统只有一个支付配置
	 * */
	public static PayConfigBeanInterface payConfigBean=null;
	
	/**
	 * 整个系统有多个配置，以Integer作为键值索引进行存储
	 * */
	public static HashMap<Integer,PayConfigBeanInterface> payConfigBeanMap=null;
	
	
	
	public static PayConfigBeanInterface getPayConfigBean() {
		return payConfigBean;
	}
	public static void setWxpayConfigBean(PayConfigBeanInterface payConfigBean) {
		WxPayConfigsCache.payConfigBean = payConfigBean;
	}
	
	public static PayConfigBeanInterface getPayConfigBean(Integer key) {
		return payConfigBeanMap.get(key);
	}
	public static HashMap<Integer, PayConfigBeanInterface> getWxpayConfigBeanMap() {
		return payConfigBeanMap;
	}
	public static void setPayConfigBeanMap(
			HashMap<Integer, PayConfigBeanInterface> payConfigBeanMap) {
		WxPayConfigsCache.payConfigBeanMap = payConfigBeanMap;
	}
	/**
	 * 整个系统只有一个支付配置,返回公众号Id
	 * */
	public static String getAppId()
	{
		if(payConfigBean!=null)
		{
			return payConfigBean.getAppId();
		}else
		{ 
			log.error("getAppId: payConfigBean is null, not initialized");
			return null;
		}
		
	}
	/**
	 * 返回商户号ID
	 * */
	public static String getMchId()
	{
		if(payConfigBean!=null)
		{
			return payConfigBean.getMchId();
		}else
		{ 
			log.error("getMchId: payConfigBean is null, not initialized");
			return null;
		}
	}
	
	/**
	 * 返回商户号加密密钥
	 * */
	public static String getMchSecretKey()
	{
		if(payConfigBean!=null)
		{
			return payConfigBean.getMchSecretKey();
		}else
		{ 
			log.error("getMchSecretKey: payConfigBean is null, not initialized");
			return null;
		}
	}
	
	/**
	 * 多配置时版本，返回公众号Id
	 * */
	public static String getAppId(Integer key)
	{
		if(payConfigBeanMap!=null)
		{
			PayConfigBeanInterface bean=payConfigBeanMap.get(key);
			if(bean!=null)
				return bean.getAppId();
			else{
				log.warn("getAppId: payConfigBean in map is null, key="+key);
				return null;
			}
		}else
		{ 
			log.error("getAppId: payConfigBeanMap is null, not initialized");
			return null;
		}
		
	}
	/**
	 * 多配置时版本，返回商户号Id
	 * */
	public static String getMchId(Integer key)
	{
		if(payConfigBeanMap!=null)
		{
			PayConfigBeanInterface bean=payConfigBeanMap.get(key);
			if(bean!=null)
				return bean.getMchId();
			else{
				log.warn("getMchId: payConfigBean in map is null, key="+key);
				return null;
			}
		}else
		{ 
			log.error("getMchId: payConfigBeanMap is null, not initialized");
			return null;
		}
	}
	/**
	 * 多配置时版本，返回商户号加密密钥
	 * */
	public static String getMchSecretKey(Integer key)
	{
		if(payConfigBeanMap!=null)
		{
			PayConfigBeanInterface bean=payConfigBeanMap.get(key);
			if(bean!=null)
				return bean.getMchSecretKey();
			else{
				log.warn("getMchSecretKey: payConfigBean in map is null, key="+key);
				return null;
			}
		}else
		{ 
			log.error("getMchSecretKey: payConfigBeanMap is null, not initialized");
			return null;
		}
	}
	
	public static String getCertFile()
	{
		if(payConfigBean!=null)
		{
			return payConfigBean.getCertFile();
		}else
		{ 
			log.error("getCertFile: payConfigBean is null, not initialized");
			return null;
		}
	}
	public static String getCertFile(Integer key)
	{
		if(payConfigBeanMap!=null)
		{
			PayConfigBeanInterface bean=payConfigBeanMap.get(key);
			if(bean!=null)
				return bean.getCertFile();
			else{
				log.warn("getCertFile: payConfigBean in map is null, key="+key);
				return null;
			}
		}else
		{ 
			log.error("getCertFile: payConfigBeanMap is null, not initialized");
			return null;
		}
	}
	public static byte[] getCertBytes()
	{
		if(payConfigBean!=null)
		{
			return payConfigBean.getCertBytes();
		}else
		{ 
			log.error("getCertBytes: payConfigBean is null, not initialized");
			return null;
		}
	}
	public static byte[] getCertBytes(Integer key)
	{
		if(payConfigBeanMap!=null)
		{
			PayConfigBeanInterface bean=payConfigBeanMap.get(key);
			if(bean!=null)
				return bean.getCertBytes();
			else{
				log.warn("getCertBytes: payConfigBean in map is null, key="+key);
				return null;
			}
		}else
		{ 
			log.error("getCertBytes: payConfigBeanMap is null, not initialized");
			return null;
		}
	}
	
	public static String getCertPwd()
	{
		if(payConfigBean!=null)
		{
			return payConfigBean.getCertPwd();
		}else
		{ 
			log.error("getCertPwd: payConfigBean is null, not initialized");
			return null;
		}
	}
	public static String getCertPwd(Integer key)
	{
		if(payConfigBeanMap!=null)
		{
			PayConfigBeanInterface bean=payConfigBeanMap.get(key);
			if(bean!=null)
				return bean.getCertPwd();
			else{
				log.warn("getCertPwd: payConfigBean in map is null, key="+key);
				return null;
			}
		}else
		{ 
			log.error("getCertPwd: payConfigBeanMap is null, not initialized");
			return null;
		}
	}
}
