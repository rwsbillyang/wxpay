package com.github.rwsbillyang.wxpay.common.report;



import com.github.rwsbillyang.wxpay.WXPayManager;
import com.github.rwsbillyang.wxpay.protocol.ReportReqData;


public class ReportRunable implements Runnable {

    //private ReportService reportService ;
	ReportReqData reqData;
    ReportRunable(ReportReqData reqData){
    	this.reqData = reqData;
    }

    @Override
    public void run() {
        try {
        	WXPayManager.getInstance().sendRequest(reqData);
        } catch (Exception e) {
			e.printStackTrace();
		}
    }
}
