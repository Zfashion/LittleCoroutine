package com.example.little.ui.fragment.viewpager

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.little.databinding.FragmentMultiTabBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.little.base.BaseBindingFragment


class MultiTabFragment: BaseBindingFragment<FragmentMultiTabBinding>() {

    private val tabs = arrayOf("关注", "推荐", "最新")
    private val fragments = arrayListOf<Fragment>()
    private val colors = arrayListOf(
        "#03DAC5",
        "#3CDA03",
        "#DAB603"
    )

    private val activeColor: Int = Color.parseColor("#ff678f")
    private val normalColor: Int = Color.parseColor("#666666")

    private val activeSize = 20f
    private val normalSize = 14f

    private lateinit var tabLayoutMediator: TabLayoutMediator

    override fun initView() {
        colors.forEach {
            fragments.add(DemoFragment.newInstance(it))
        }

        val fragmentAdapter = FragmentAdapter(fragments, this.requireActivity())
        binding.apply {
            viewPager2.adapter = fragmentAdapter

            tabLayoutMediator = TabLayoutMediator(tabLayout, viewPager2, true, true) { tab, positon ->
                //这里可以自定义TabView
                val tabView = TextView(this@MultiTabFragment.context)
                val states = arrayOfNulls<IntArray>(2)
                states[0] = intArrayOf(android.R.attr.state_selected)
                states[1] = intArrayOf()

                val colors = intArrayOf(activeColor, normalColor)
                val colorStateList = ColorStateList(states, colors)
                tabView.text = tabs[positon]
                tabView.textSize = normalSize
                tabView.setTextColor(colorStateList)

                tab.customView = tabView
            }
            tabLayoutMediator.attach()

            viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    /*val tabCount = tabLayout.tabCount
                    for (i in 0..tabCount) {
                        val tabAt = tabLayout.getTabAt(i)
                        val tabView = tabAt?.customView as TextView
                        if (tabAt.position == position) {
                            tabView.textSize = activeSize
                            tabView.typeface = Typeface.DEFAULT_BOLD
                        } else {
                            tabView.textSize = normalSize
                            tabView.typeface = Typeface.DEFAULT
                        }
                    }
                    Toast.makeText(
                        this@MultiTabFragment.context,
                        "current position $position",
                        Toast.LENGTH_SHORT
                    ).show()*/
                }
            })
        }
    }

    override fun onDestroyView() {
        tabLayoutMediator.detach()
//        binding.viewPager2.unregisterOnPageChangeCallback()
        super.onDestroyView()
    }

}