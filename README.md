# EasyPay

fork from [EasyPay](https://github.com/kingofglory/EasyPay)，做了一些优化，迁移到`jitpack`。

## 导入

```groovy

```

## 使用

```kotlin
//支付宝
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
```

```kotlin
// 微信
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
```



## 更新

* v1.0.0：首次上传

