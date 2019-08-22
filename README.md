# wxpay
Wechat(WeiXin) pay sdk, 根据早期的官方sdk代码重构而来

#### 引入依赖
两种方式二选一

- 本地编译安装

git pull 后，本地打包安装:
```
mvn package install
```

在项目中引入：
```
	<dependency>
		<groupId>com.github.rwsbillyang</groupId>
		<artifactId>wxpay</artifactId>
		<version>1.0.1</version>
	</dependency>
```

- 直接导入依赖

gradle工程：
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
```
dependencies {
		implementation 'com.github.rwsbillyang:wxpay:1.0.1'
	}
```


maven工程：
```
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```

```
	<dependency>
		<groupId>com.github.rwsbillyang</groupId>
		<artifactId>wxpay</artifactId>
		<version>1.0</version>
	</dependency>
```

#### 使用参考
```
	//首先实现支付配置接口
	class WxPayConfigBean implements PayConfigBeanInterface{
		private String appId;
		private String mchId;
		private String secretKey;
		private String certFile;//certFile和certData二选一，优先使用certData
		private String certPwd;
		private byte[] certData;//certFile和certData二选一，优先使用certData
		
		
		public WxPayConfigBean(String appId, String mchId, String secretKey,
				String certFile, String certPwd) {
			super();
			this.appId = appId;
			this.mchId = mchId;
			this.secretKey = secretKey;
			this.certFile = certFile;
			this.certPwd = certPwd;
		}

		public WxPayConfigBean(String appId, String mchId, String secretKey, byte[] certData,
				String certPwd) {
			super();
			this.appId = appId;
			this.mchId = mchId;
			this.secretKey = secretKey;
			this.certPwd = certPwd;
			this.certData = certData;
		}

		@Override
		public String getAppId() {	
			return appId;
		}

		@Override
		public byte[] getCertBytes() {
			return certData;
		}

		@Override
		public String getCertFile() {	
			return certFile;
		}

		@Override
		public String getCertPwd() {
			return certPwd;
		}

		@Override
		public String getMchId() {
			return mchId;
		}

		@Override
		public String getMchSecretKey() {
			return secretKey;
		}	
	}	
```





在系统初始化时，需要将自己的微信支付参数配置数据构造出实体WxPayConfigBean，然后赋给SDK，待系统运行起来后需要微信支付时，就将用到这些配置数据：
```
//根据自己的情况将配置数据传递给构造函数。这些数据可以来自于数据库，也可来自于配置文件，也可来自于硬编码。
WxPayConfigBean payConfig=new WxPayConfigBean(your_AppId,your_MchId,your_PaySecretKey,your_CertFile,your_CertPwd);
WXPayManager.getInstance().initConfigure(payConfig);//调用SDK中的API将配置
```



实际支付请求示例代码如下：
```
		//预提交订单至微信服务器，得到prepayId
		OrderUnifieldReqData reqData = new OrderUnifieldReqData("商品名称xxx",
				order.getId(),order.getPrice(),"the_user_ip_address",
				“Your_WechatNotifyPath”,"JSAPI","the_user_OpenId");
	
	
		
		String payServiceResponseString = WXPayManager.getInstance().sendRequest(reqData);
		ResponseMap resData=null;
		try {
			resData = new ResponseMap(payServiceResponseString);
		} catch (DocumentException e) {
			log.error("maybe invalid parameter or weixin server is not acessible");
			e.printStackTrace();
			//省略处理代码
		}
		if(resData.getReturn_code()==null)
		{
			//省略处理代码
		}
		if(resData.getReturn_code().equals(ResponseMap.FAIL))
		{
			 //注意：一般这里返回FAIL是出现系统级参数错误，请检测Post给API的数据是否规范合法
			log.error("invalid post parameter");
			//省略处理代码
		}
		
		
		if (!resData.isSignatureValid(webId)) {
			log.error("invalid signature");
			//省略处理代码
		}
		
		if (resData.getResult_code().equals(ResponseMap.SUCCESS)) {
            //--------------------------------------------------------------------
            //1)预支付成功
            //--------------------------------------------------------------------
			//返回到前端页面，前端页面用js发起支付请求
			//前端得到请求跳转至相应页面，后端得到请求，更新订单及产品权限
			JsPaySignature js = new JsPaySignature(resData.getPrepay_id(),webId);
			//省略处理代码
        }else
        {
        	 //获取错误码
            String errorCode = resData.getErr_code();
            //获取错误描述
            String errorCodeDes = resData.getErr_code_des();
        	log.warn("wxpay fail:"+errorCode);
        	//省略处理代码
        }

```
API支持传入HttpRequest参数，以支持多域名多站点。

