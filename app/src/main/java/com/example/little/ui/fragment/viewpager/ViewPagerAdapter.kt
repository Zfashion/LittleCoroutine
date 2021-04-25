package com.example.little.ui.fragment.viewpager

import android.graphics.Color
import android.util.Log
import com.example.little.databinding.ItemColorBinding
import com.little.base.BaseBindingAdapter

class ViewPagerAdapter(data: MutableList<String>): BaseBindingAdapter<String, ItemColorBinding>(data) {

    override fun convert(binding: ItemColorBinding, position: Int, item: String) {
        binding.apply {
            root.setBackgroundColor(Color.parseColor(item))
            num.text = position.toString()
        }
        Log.d("view pager adapter", "position: $position, item: $item")
    }

}