/*
 ******************************* Copyright (c)*********************************\
 **
 **                 (c) Copyright 2017, King, china
 **                          All Rights Reserved
 **
 **                              By(King)
 **
 **------------------------------------------------------------------------------
 */
package com.xgr.wechatpay.wxpay;


import com.xgr.easypay.base.IPayInfo;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 文 件 名: WXPayInfoImpli
 * 创 建 人: King
 * 创建日期: 2017/2/13 17:09
 * 邮   箱: mikey1101@163.com
 * 博   客: www.smilevenus.com
 * 描述 ：
 */
public class WXPayInfo implements IPayInfo {
    /**
     * sign : ECE311C3DF76E009E6F37F05C350625F
     * timestamp : 1474886901
     * partnerid : 1391669502
     * package : Sign=WXPay
     * appid : wx46a24ab145becbde
     * nonceStr : 0531a4a42fa846fe8a7563847cd24c2a
     * prepayId : wx20160926184820acbd9357100240402425
     */

    private String sign;
    private String timestamp;
    private String partnerId;
    private String packageValue;
    private String appId;
    private String nonceStr;
    private String prepayId;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public void parseJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            setAppId(jsonObject.optString("appId"));
            setPartnerId(jsonObject.optString("partnerId"));
            setPrepayId(jsonObject.optString("prepayId"));
            setPackageValue(jsonObject.optString("packageValue"));
            setNonceStr(jsonObject.optString("nonceStr"));
            setTimestamp(jsonObject.optString("timeStamp"));
            setSign(jsonObject.optString("sign"));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void parseString(String str, String regex1, String regex2) {
        try {
            Map<String, String> map = new HashMap<>();
            String[] strings = str.split(regex1);
            for (String string : strings) {
                String[] kv = string.split(regex2);
                map.put(kv[0], kv[1]);
            }
            setAppId(map.get("appId"));
            setPartnerId(map.get("partnerId"));
            setPrepayId(map.get("prepayId"));
            setPackageValue(map.get("packageValue"));
            setNonceStr(map.get("nonceStr"));
            setTimestamp(map.get("timeStamp"));
            setSign(map.get("sign"));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
