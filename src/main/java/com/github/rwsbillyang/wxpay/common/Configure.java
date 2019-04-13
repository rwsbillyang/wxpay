package com.github.rwsbillyang.wxpay.common;


public class Configure {

	//是否使用异步线程的方式来上报API测速，默认为异步模式
	private static boolean useThreadToDoReport = true;

	//机器IP
	private static String ip = "";

	public static void setIp(String ip) {
		Configure.ip = ip;
	}
	public static String getIP(){
		return ip;
	}

	public static boolean isUseThreadToDoReport() {
		return useThreadToDoReport;
	}

	public static void setUseThreadToDoReport(boolean useThreadToDoReport) {
		Configure.useThreadToDoReport = useThreadToDoReport;
	}

	public static String HttpsRequestClassName = "com.tencent.common.HttpsRequest";



	public static void setHttpsRequestClassName(String name){
		HttpsRequestClassName = name;
	}

}
