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

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xgr.easypay.base.IPayStrategy;
import com.xgr.easypay.callback.IPayCallback;

/**
 * 文 件 名: WXPay
 * 创 建 人: King
 * 创建日期: 2017/2/13 19:03
 * 邮   箱: mikey1101@163.com
 * 博   客: www.smilevenus.com
 *
 * @see <a href="https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=1417751808&token=&lang=zh_CN">Des</a>
 */
public class WXPay implements IPayStrategy<WXPayInfo> {

    private static WXPay mWXPay;
    private static IPayCallback sPayCallback;
    private IWXAPI mWXApi;
    private boolean initialized;

    private WXPay() {

    }

    public static WXPay getInstance() {
        if (mWXPay == null) {
            synchronized (WXPay.class) {
                if (mWXPay == null) {
                    mWXPay = new WXPay();
                }
            }
        }
        return mWXPay;
    }

    public IWXAPI getWXApi() {
        return mWXApi;
    }

    private void initWXApi(Context context, String appId) {
        mWXApi = WXAPIFactory.createWXAPI(context.getApplicationContext(), appId);
        mWXApi.registerApp(appId);
        initialized = true;
    }

    @Override
    public void pay(Activity activity, WXPayInfo payInfo, IPayCallback payCallback) {
        sPayCallback = payCallback;

        if (payInfo == null
                || TextUtils.isEmpty(payInfo.getAppId())
                || TextUtils.isEmpty(payInfo.getPartnerId())
                || TextUtils.isEmpty(payInfo.getPrepayId())
                || TextUtils.isEmpty(payInfo.getPackageValue())
                || TextUtils.isEmpty(payInfo.getNonceStr())
                || TextUtils.isEmpty(payInfo.getTimestamp())
                || TextUtils.isEmpty(payInfo.getSign())) {
            if (payCallback != null) {
                payCallback.failed(WXErrCodeEx.CODE_ILLEGAL_ARGURE, WXErrCodeEx.getMessageByCode(WXErrCodeEx.CODE_ILLEGAL_ARGURE));
            }
            return;
        }

        if (!initialized) {
            initWXApi(activity.getApplicationContext(), payInfo.getAppId());
        }

        if (!check()) {
            if (payCallback != null) {
                payCallback.failed(WXErrCodeEx.CODE_UNSUPPORT, WXErrCodeEx.getMessageByCode(WXErrCodeEx.CODE_UNSUPPORT));
            }
            return;
        }

        PayReq req = new PayReq();
        req.appId = payInfo.getAppId();
        req.partnerId = payInfo.getPartnerId();
        req.prepayId = payInfo.getPrepayId();
        req.packageValue = payInfo.getPackageValue();
        req.nonceStr = payInfo.getNonceStr();
        req.timeStamp = payInfo.getTimestamp();
        req.sign = payInfo.getSign();

        mWXApi.sendReq(req);
    }

    /**
     * 支付回调响应
     */
    public void onResp(int errorCode, String errorMsg) {
        if (sPayCallback == null) {
            return;
        }

        if (errorCode == BaseResp.ErrCode.ERR_OK) {
            sPayCallback.success();
        } else if (errorCode == BaseResp.ErrCode.ERR_COMM) {
            sPayCallback.failed(errorCode, errorMsg);
        } else if (errorCode == BaseResp.ErrCode.ERR_USER_CANCEL) {
            sPayCallback.cancel();
        } else {
            sPayCallback.failed(errorCode, errorMsg);
        }

        sPayCallback = null;
    }

    /**
     * 检测是否支持微信支付
     */
    private boolean check() {
        return mWXApi.isWXAppInstalled() && mWXApi.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
    }
}
