package demo.simple.easypay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.xgr.alipay.alipay.AliPay
import com.xgr.alipay.alipay.AlipayInfo
import com.xgr.easypay.EasyPay
import com.xgr.easypay.callback.IPayCallback
import com.xgr.wechatpay.wxpay.WXPay
import com.xgr.wechatpay.wxpay.WXPayInfo

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun alipay(view: View) {
        val aliPay = AliPay()
        val alipayInfo = AlipayInfo()
        alipayInfo.orderInfo =
            "alipay_sdk=alipay-sdk-net-4.6.192.ALL&app_id=2021003122677358&biz_content=%7b%22body%22%3a%22%e6%a2%a6%e5%a2%83%e6%97%85%e5%ba%97%22%2c%22out_trade_no%22%3a%2220210031226773582022031811263181066145%22%2c%22product_code%22%3a%22QUICK_MSECURITY_PAY%22%2c%22subject%22%3a%22%e4%b8%a4%e6%a0%b9%e7%8c%ab%e6%9d%a1%22%2c%22timeout_express%22%3a%2230m%22%2c%22total_amount%22%3a%220.01%22%7d&charset=utf-8&format=json&method=alipay.trade.app.pay&notify_url=http%3a%2f%2f120.27.20.114%3a6912%2fapi%2fMain%2fAliPayNotify&sign_type=RSA2&timestamp=2022-03-18+11%3a26%3a32&version=1.0&sign=J6k2CJz%2bCA3fl%2bYiuub74bmWyEeAilSmVN0KQi0LJNQfvts%2fOLoXrM0J4F34Vn18xkcpN1Ej96oDIYClFE4PFrcG1Mp7GQumMFO%2f4IrBNf1i%2b9XL%2fcypUg2eP5iMbtIDP1P2lA2gmd7S0J8I6q4%2f5Hio6Z%2btCnV6SFRLE1GugyhoTc9AhkcbblXPdNYi3XuXn%2b2kweoMx3u2qX43hMbIvYncBiKwqxHsEDG7WZpqXnfvbMAkAf2ukEf7haeQlP7RG8635mI7RpcV2DFNJOsWNymzdWY1fvPeiHwOzP%2b5gDul4IJlcNIOT%2bLRq0yL41yIiHgbL6%2fwhqixXlZLrFXbHQ%3d%3d"
        EasyPay.pay(this, aliPay, alipayInfo, object : IPayCallback {
            override fun success() {
                showToast("支付成功")
            }

            override fun failed(code: Int, message: String?) {
                showToast("支付失败 -$code - $message")
            }

            override fun cancel() {
                showToast("支付取消")
            }
        })
    }

    fun wechat(view: View) {
        val wxPay = WXPay.getInstance()
//        val wxInfo = NetHelper.KTJson.decodeFromString<WxPayInfo>(order.payStr)
        val wxPayInfo = WXPayInfo().apply {
            this.timestamp = ""
            this.sign = ""
            this.prepayId = ""
            this.partnerid = ""
            this.appid = ""
            this.nonceStr = ""
            this.packageValue = ""
        }
        EasyPay.pay(this, wxPay, wxPayInfo, object : IPayCallback {
            override fun success() {
                showToast("支付成功")
            }

            override fun failed(code: Int, message: String?) {
                showToast("支付失败 -$code - $message")
            }

            override fun cancel() {
                showToast("支付取消")
            }
        })
    }

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}