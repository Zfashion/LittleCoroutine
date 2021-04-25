package com.example.little.ui.fragment.viewpager

import android.graphics.Color
import androidx.core.os.bundleOf
import com.example.little.databinding.FragmentDemoBinding
import com.little.base.BaseBindingFragment

class DemoFragment: BaseBindingFragment<FragmentDemoBinding>() {

    companion object {
        fun newInstance(color: String): DemoFragment {
            val bundleOf = bundleOf("key" to color)
            val demoFragment = DemoFragment()
            demoFragment.arguments = bundleOf
            return demoFragment
        }
    }

    override fun initView() {
        val str = arguments?.getString("key")
        binding.apply {
            root.setBackgroundColor(Color.parseColor(str))
            txt.text = str
        }
    }
}