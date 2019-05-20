package com.py.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class OrderNumUtil {

    //客户端生成自己的唯一订单号
    public static String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss");
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        String rdmStr="";
        for(int i=0;i<5;i++){
            rdmStr=rdmStr+String.valueOf(r.nextInt(10));
        }
        key = key + rdmStr;
        return key;
    }


    public static String getOrderNum() {
        int r1=(int)(Math.random()*(10));//产生2个0-9的随机数
        int r2=(int)(Math.random()*(10));
        long now = System.currentTimeMillis();//一个13位的时间戳
        String paymentID =String.valueOf(r1)+String.valueOf(r2)+String.valueOf(now);
        return paymentID;
    }
    public static void main(String[] args) {
        System.out.println(getOrderNum());
    }


}
