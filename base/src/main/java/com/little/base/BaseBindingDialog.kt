package com.little.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.little.utils.inflateBindingWithGeneric

abstract class BaseBindingDialog<VB: ViewBinding>(context: Context, themeResId: Int): Dialog(context, themeResId) {

    lateinit var binding: VB

    constructor(context: Context) : this(context, R.style.DefaultDialogStyle)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflateBindingWithGeneric(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    abstract fun initView()

}