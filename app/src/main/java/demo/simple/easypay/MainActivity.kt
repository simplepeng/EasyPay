package demo.simple.easypay

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
        alipayInfo.orderInfo = ""
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
        val wxPayInfo = WXPayInfo().apply {
            this.appId = ""
            this.partnerId = ""
            this.prepayId = ""
            this.packageValue = ""
            this.nonceStr = ""
            this.timestamp = ""
            this.sign = ""
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