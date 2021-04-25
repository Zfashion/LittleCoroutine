package com.example.little.ui.fragment.binding

import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.example.little.databinding.FragmentVbTestBinding
import com.example.little.ui.dialog.TestBindingDialog
import com.little.base.BaseBindingFragment
import java.lang.StringBuilder

class TestVBFragment: BaseBindingFragment<FragmentVbTestBinding>() {

    private val dialog: TestBindingDialog by lazy { TestBindingDialog(this.requireContext()) }
    private val model : TestVBModel by viewModels()

    private val inputTxt by lazy { StringBuilder() }

    override fun initView() {
        binding.openDialog.setOnClickListener {
            if (dialog.isShowing) dialog.dismiss()
            dialog.show()
        }

        //translate
        binding.editText.addTextChangedListener(onTextChanged = { text, start, before, count ->
            inputTxt.append(text)
        })

        binding.requestTranslate.setOnClickListener {
            model.translate(inputTxt.toString())
        }

        model.translateResult.observe(this) {
            binding.result.text = it
        }
    }
}