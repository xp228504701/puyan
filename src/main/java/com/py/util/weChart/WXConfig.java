package com.py.util.weChart;

public class WXConfig {

    /**
     * 服务号相关信息
     */
    public final static String APP_ID = "wxe1ddffe54a8ecf2f";		                //服务号的应用号
    //public final static String MCH_ID = "1498429152";						        //商户号
    //public final static String API_KEY = "zcf11111111111111111111111111111";    	//API密钥
    public final static String App_Secret = "04bf9eecbc10fd9c5de98091ba2b98d6";     //小程序密钥
    public final static String SIGN_TYPE = "MD5";									//签名加密方式

    public final static String notify_url = "http://pay.yssanzhiyan.com:8081/wxNotify";		//回调接口

    public final static String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    public final static String TRANSFERS_PAY = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers"; // 企业付款

    public static final String TRANSFERS_PAY_QUERY = "https://api.mch.weixin.qq.com/mmpaymkttransfers/gettransferinfo"; // 企业付款查询



}
