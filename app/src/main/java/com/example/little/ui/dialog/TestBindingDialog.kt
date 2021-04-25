package com.example.little.ui.dialog

import android.content.Context
import android.util.Base64
import com.example.little.databinding.DialogTestBindingBinding
import com.little.base.BaseBindingDialog

class TestBindingDialog(context: Context): BaseBindingDialog<DialogTestBindingBinding>(context) {

    override fun initView() {
        setCancelable(false)
        val str = "YmFydGRpbmdAcXEuY29t"
        val str2 = "ZGJmb3JldmVyc21pbGU2"
        val decodeToString = Base64.decode(str, Base64.NO_WRAP).decodeToString()
        val decodeToString2 = Base64.decode(str2, Base64.NO_WRAP).decodeToString()
        binding.two.text = "邮箱: $decodeToString"
        binding.one.text = "微信号：$decodeToString2"
    }

}