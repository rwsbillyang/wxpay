package com.github.rwsbillyang.wxpay.common.report;

import com.github.rwsbillyang.wxpay.protocol.ReportReqData;



public class Reporter {

    private ReportRunable r;
    private Thread t;
    private ReportReqData d;

    /**
     * 请求统计上报API
     * @param reportReqData 这个数据对象里面包含了API要求提交的各种数据字段
     */
    public Reporter(ReportReqData reportReqData){
        d = reportReqData;
    }

    public void run(){
        r = new ReportRunable(d);
        t = new Thread(r);
        t.setDaemon(true);  //后台线程
        t.start();
    }
}
