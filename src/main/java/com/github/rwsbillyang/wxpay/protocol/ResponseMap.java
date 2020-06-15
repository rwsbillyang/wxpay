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
package com.github.rwsbillyang.wxpay.protocol;

import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.QName;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.github.rwsbillyang.wxpay.common.Signature;
import com.github.rwsbillyang.wxpay.payconfig.WxPayConfigsCache;

import lombok.extern.slf4j.Slf4j;

/**
 * 用于将微信返回的xml字符串转换为HashMap，提供了基本的返回函数
 * */
@Slf4j
public class ResponseMap extends HashMap<String,Object>{

	
	//返回状态码
		public final static String  SUCCESS="SUCCESS";
		public final static String  FAIL="FAIL";
		
		
		//统一下单 错误码result_code
		public final static String  NOAUTH="NOAUTH";
		public final static String  NOTENOUGH="NOTENOUGH";
		public final static String  ORDERPAID="ORDERPAID";//订单已支付，不能发起关单	订单已支付，不能发起关单，请当作已支付的正常交易
		public final static String  ORDERCLOSED="ORDERCLOSED";//订单已关闭	订单已关闭，无法重复关闭	无需继续调用
		public final static String  SYSTEMERROR="SYSTEMERROR";//系统错误，请重新调用该API
		public final static String  APPID_NOT_EXIST="APPID_NOT_EXIST";
		public final static String  MCHID_NOT_EXIST="MCHID_NOT_EXIST";
		public final static String  APPID_MCHID_NOT_MATCH="APPID_MCHID_NOT_MATCH";
		public final static String  LACK_PARAMS="LACK_PARAMS";
		public final static String  OUT_TRADE_NO_USED="OUT_TRADE_NO_USED";
		public final static String  SIGNERROR="SIGNERROR";//签名错误	参数签名结果不正确	请检查签名参数和方法是否都符合签名算法要求
		public final static String  REQUIRE_POST_METHOD ="REQUIRE_POST_METHOD";//请使用post方法	未使用post传递参数 	请检查请求参数是否通过post方法提交
		public final static String  XML_FORMAT_ERROR ="XML_FORMAT_ERROR";//	XML格式错误	XML格式错误	请检查XML参数格式是否正确
		public final static String  POST_DATA_EMPTY="POST_DATA_EMPTY";
		public final static String  NOT_UTF8="NOT_UTF8";
		
		//ORDERNOTEXIST	此交易订单号不存在	查询系统中不存在此交易订单号	该API只能查提交支付交易返回成功的订单，请商户检查需要查询的订单号是否正确	
		
		//关闭订单 错误码result_code
		//public final static String  ORDERPAID ="ORDERPAID";
		//public final static String  SYSTEMERROR	 ="SYSTEMERROR";//系统错误，请重新调用该API
		public final static String  ORDERNOTEXIST = "ORDERNOTEXIST";//订单系统不存在此订单	不需要关单，当作未提交的支付的订单
		//public final static String  ORDERCLOSED ="ORDERCLOSED";
		//public final static String  SIGNERROR ="SIGNERROR";
		//public final static String  REQUIRE_POST_METHOD ="REQUIRE_POST_METHOD";//请使用post方法	未使用post传递参数 	请检查请求参数是否通过post方法提交
		//public final static String  XML_FORMAT_ERROR ="XML_FORMAT_ERROR";//	XML格式错误	XML格式错误	请检查XML参数格式是否正确
		
		//查询订单
		//ORDERNOTEXIST	此交易订单号不存在	查询系统中不存在此交易订单号	该API只能查提交支付交易返回成功的订单，请商户检查需要查询的订单号是否正确	
		//SYSTEMERROR
		
		//查询退款错误码result_code
		//SYSTEMERROR	接口返回错误	系统超时	请尝试再次掉调用API。
		public final static String  INVALID_TRANSACTIONID = "INVALID_TRANSACTIONID";//	无效transaction_id	请求参数未按指引进行填写	请求参数错误，检查原交易号是否存在或发起支付交易接口返回失败
		public final static String  PARAM_ERROR = "PARAM_ERROR";//	参数错误	请求参数未按指引进行填写	请求参数错误，请检查参数再调用退款申请
		//APPID_NOT_EXIST	APPID不存在	参数中缺少APPID	请检查APPID是否正确
		//MCHID_NOT_EXIST	MCHID不存在	参数中缺少MCHID	请检查MCHID是否正确
		//APPID_MCHID_NOT_MATCH	appid和mch_id不匹配	appid和mch_id不匹配	请确认appid和mch_id是否匹配
		//REQUIRE_POST_METHOD	请使用post方法	未使用post传递参数 	请检查请求参数是否通过post方法提交
		//SIGNERROR	签名错误	参数签名结果不正确	请检查签名参数和方法是否都符合签名算法要求
		//XML_FORMAT_ERROR	XML格式错误	XML格式错误	请检查XML参数格式是否正确
		
		//申请退款错误码result_code
		public final static String  USER_ACCOUNT_ABNORMAL = "USER_ACCOUNT_ABNORMAL";

		
	/**
	 * 从xml字符串中构建ResponseMap对象
	 * @throws DocumentException 
	 * */
	public ResponseMap(String xmlString) throws DocumentException {
		super();
		log.info("xmlString="+xmlString);
		xml2map(this,xmlString);
	}
	/**
	 * 从输入流中构建ResponseMap对象
	 * @throws DocumentException 
	 * */
	public ResponseMap(InputStream inStream) throws DocumentException {
		super();
		
		try {
			SAXReader sb = new SAXReader(); // 新建立构造器
			//avoid XXE
			sb.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
			 Document doc = sb.read(inStream);
		        Element rootElement = doc.getRootElement();
		        if(rootElement.getName().equalsIgnoreCase("xml"))
				{
					List<Element> elements = rootElement.elements();
					for(Element e: elements)
					{
						ele2map(this, e);
					}
				}
		} catch (SAXException e1) {
			e1.printStackTrace();
		}
       
	}
	static Map<String, Object> xml2map(Map<String, Object> map,String xmlString) throws DocumentException {
		if(xmlString==null)
		{
			return map;
		}
		try {
			Document doc = parseText(xmlString);
			Element rootElement = doc.getRootElement();
			if(rootElement.getName().equalsIgnoreCase("xml"))
			{
				List<Element> elements = rootElement.elements();
				for(Element e: elements)
				{
					ele2map(map, e);
				}
			}
		} catch (SAXException e1) {
			e1.printStackTrace();
		}
		
		
		
		return map;
	}
	/***
	  * 核心方法，里面有递归调用
	  * 
	  * @param map
	  * @param ele
	  */
	static void ele2map(Map<String, Object> map, Element ele) {
		// 获得当前节点的子节点
		List<Element> elements = ele.elements();
		if (elements.size() == 0) {
			// 没有子节点说明当前节点是叶子节点，直接取值即可
			//log.info("ele.getName()="+ele.getName()+", ele.getText()="+ele.getText());
			map.put(ele.getName(), ele.getText());
		} else if (elements.size() == 1) {
			// 只有一个子节点说明不用考虑list的情况，直接继续递归即可
			Map<String, Object> tempMap = new HashMap<String, Object>();
			ele2map(tempMap, elements.get(0));
			map.put(ele.getName(), tempMap);
			//log.info("ele.getName()="+ele.getName()+" has a child");
		} else {
			//log.info("ele.getName()="+ele.getName()+" has  children");
			// 多个子节点的话就得考虑list的情况了，比如多个子节点有节点名称相同的
			// 构造一个map用来去重
			Map<String, Object> tempMap = new HashMap<String, Object>();
			for (Element element : elements) {
				tempMap.put(element.getName(), null);
			}
			Set<String> keySet = tempMap.keySet();
			for (String string : keySet) {
				List<Element> elements2 = ele.elements(new QName(string));
				// 如果同名的数目大于1则表示要构建list
				if (elements2.size() > 1) {
					List<Map> list = new ArrayList<Map>();
					for (Element element : elements2) {
						Map<String, Object> tempMap1 = new HashMap<String, Object>();
						ele2map(tempMap1, element);
						list.add(tempMap1);
					}
					map.put(string, list);
				} else {
					// 同名的数量不大于1则直接递归去
					Map<String, Object> tempMap1 = new HashMap<String, Object>();
					ele2map(tempMap1, elements2.get(0));
					map.put(string, tempMap1);
				}
			}
		}
	}
	
/*
    public static Map<String,Object> getMapFromXML(String xmlString) throws ParserConfigurationException, IOException, SAXException {

        //这里用Dom的方式解析回包的最主要目的是防止API新增回包字段
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputStream is =  Util.getStringStream(xmlString);
        Document document = builder.parse(is);

        //获取到document里面的全部结点
        NodeList allNodes = document.getFirstChild().getChildNodes();
        Node node;
        Map<String, Object> map = new HashMap<String, Object>();
        int i=0;
        while (i < allNodes.getLength()) {
            node = allNodes.item(i);
            if(node instanceof Element){
                map.put(node.getNodeName(),node.getTextContent());
            }
            i++;
        }
        return map;

    }
    */
	/**
	 * 检查xml串签名是否有效，有效返回true，无效返回false
	 * */
	public boolean isSignatureValid(String signType) {
		String signInResponse = getSign();
		if (signInResponse == null || signInResponse == "") {
			log.warn("signature in response is null");
			return false;
		}
		String signForAPIResponse = Signature.getSign(this,
				WxPayConfigsCache.getMchSecretKey(),signType);
		if (signForAPIResponse.equals(signInResponse))
			return true;
		else
			return false;
	}

	/**
	 * 多配置版本，检查xml串签名是否有效，有效返回true，无效返回false
	 * */
	public boolean isSignatureValid(Integer indexKey, String signType) {
		String signInResponse = getSign();
		if (signInResponse == null || signInResponse == "") {
			log.warn("signature in response is null");
			return false;
		}
		String signForAPIResponse = Signature.getSign(this,
				WxPayConfigsCache.getMchSecretKey(indexKey),signType);
		if (signForAPIResponse.equals(signInResponse))
			return true;
		else
			return false;
	}

	public String getReturn_code() {
		return (String) this.get("return_code");
	}

	public String getReturn_msg() {
		return (String) this.get("return_msg");
	}

	public String getAppid() {
		return (String) this.get("appid");
	}

	public String getMch_id() {
		return (String) this.get("mch_id");
	}

	public String getDevice_info() {
		return (String) this.get("device_info");
	}

	public String getNonce_str() {
		return (String) this.get("nonce_str");
	}

	public String getSign() {
		return (String) this.get("sign");
	}

	public String getResult_code() {
		return (String) this.get("result_code");
	}

	public String getErr_code() {
		return (String) this.get("err_code");
	}

	public String getErr_code_des() {
		return (String) this.get("err_code_des");
	}


///////////////////////////////////////////////////////////////////////////////////
////以下函数是不同的请求结果出现的字段，有该字段就用，没有就不用，若无对象函数，请自行调用get(key)///
///////////////////////////////////////////////////////////////////////////////////

	


	public String getPrepay_id() {
		return (String) this.get("prepay_id");
	}

	public String getCode_url() {
		return (String) this.get("code_url");
	}

	
	public String getTrade_state() {
		return (String) this.get("trade_state");
	}

	public String getOpenid() {
		return (String) this.get("openid");
	}

	public String getIs_subscribe() {
		return (String) this.get("is_subscribe");
	}

	public String getTrade_type() {
		return (String) this.get("trade_type");
	}

	public String getBank_type() {
		return (String) this.get("bank_type");
	}

	public String getCoupon_fee() {
		return (String) this.get("coupon_fee");
	}

	public String getFee_type() {
		return (String) this.get("fee_type");
	}

	public String getTransaction_id() {
		return (String) this.get("transaction_id");
	}

	public String getOut_trade_no() {
		return (String) this.get("out_trade_no");
	}

	public String getAttach() {
		return (String) this.get("attach");
	}

	public String getTime_end() {
		return (String) this.get("time_end");
	}

	public String getTrade_state_desc() {
		return (String) this.get("trade_state_desc");

	}
    public Integer getRefund_count() {
    	return Integer.parseInt((String) this.get("refund_count"));
    }
	public Integer getTotal_fee() {
		return Integer.parseInt((String) this.get("total_fee"));
	}
	public Integer getCash_fee() {
		return Integer.parseInt((String) this.get("cash_fee"));
	}

	/**
	 * 是否需要继续调用撤销，Y-需要，N-不需要
	 * */
	public String getRecall() {
		return (String) this.get("recall");
	}
	
	//copy from DocumentHelper
	 private static Document parseText(String text) throws DocumentException, SAXException {
	        Document result = null;

	        SAXReader reader = new SAXReader();
	        //avoid XXE
	        reader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
			
	        String encoding = getEncoding(text);

	        InputSource source = new InputSource(new StringReader(text));
	        source.setEncoding(encoding);

	        result = reader.read(source);

	        // if the XML parser doesn't provide a way to retrieve the encoding,
	        // specify it manually
	        if (result.getXMLEncoding() == null) {
	            result.setXMLEncoding(encoding);
	        }

	        return result;
	    }

	    private static String getEncoding(String text) {
	        String result = null;

	        String xml = text.trim();

	        if (xml.startsWith("<?xml")) {
	            int end = xml.indexOf("?>");
	            String sub = xml.substring(0, end);
	            StringTokenizer tokens = new StringTokenizer(sub, " =\"\'");

	            while (tokens.hasMoreTokens()) {
	                String token = tokens.nextToken();

	                if ("encoding".equals(token)) {
	                    if (tokens.hasMoreTokens()) {
	                        result = tokens.nextToken();
	                    }

	                    break;
	                }
	            }
	        }

	        return result;
	    }

		
}
