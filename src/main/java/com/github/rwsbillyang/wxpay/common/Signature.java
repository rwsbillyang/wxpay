package com.github.rwsbillyang.wxpay.common;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;


public class Signature {
	public final static String TYPE_HMAC_SHA256="HMAC-SHA256";
	public final static String TYPE_MD5="MD5";
	
    /**
     * 签名算法
     * @param o 要参与签名的数据对象
     * @return 签名
     * @throws IllegalAccessException
     */
    public static String getSign(Object o,String secret, String signType) throws IllegalAccessException {
        ArrayList<String> list = new ArrayList<String>();
        Class cls = o.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            if (f.get(o) != null && f.get(o) != ""&&!f.getName().equals("sign")) {
                list.add(f.getName() + "=" + f.get(o) + "&");
            }
        }
        int size = list.size();
        String [] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + secret;
   
        if(TYPE_HMAC_SHA256.equals(signType))
        {
        	result = HMACSHA256.sha256_HMAC(result, secret);
        }else
        {
        	result = MD5.MD5Encode(result);
        }
        
      
        return result.toUpperCase();
    }

    public static String getSign(Map<String,Object> map,String secret, String signType){
        ArrayList<String> list = new ArrayList<String>();
        for(Map.Entry<String,Object> entry:map.entrySet()){
            if(entry.getValue()!=""&&entry.getValue()!=null&&!entry.getKey().equals("sign")){
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String [] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + secret;   
        
        if(TYPE_HMAC_SHA256.equals(signType))
        {
        	result = HMACSHA256.sha256_HMAC(result, secret);
        }else
        {
        	result = MD5.MD5Encode(result);
        }
        
      
        return result.toUpperCase();
    }



}
