package com.example.little.ui.fragment.viewpager

import androidx.viewpager2.widget.ViewPager2
import com.example.little.databinding.FragmentViewpager2Binding
import com.little.base.BaseBindingFragment
import kotlin.random.Random

class ViewPagerFragment: BaseBindingFragment<FragmentViewpager2Binding>() {

    private val colors = arrayListOf(
        "#03DAC5",
        "#3CDA03",
        "#DAB603",
        "#DA6003",
        "#8803DA"
    )

    private val fragmentAdapter by lazy { FragmentAdapter(this.childFragmentManager, this.lifecycle) }
    private val recyclerAdapter by lazy { ViewPagerAdapter(colors) }

    override fun initView() {
        binding.apply {
            simple.setOnClickListener {
                viewPager2.adapter = recyclerAdapter
            }

            fragmentViewPager.setOnClickListener {
                viewPager2.adapter = fragmentAdapter
            }

            addFragment.setOnClickListener {
                fragmentAdapter.addFragment(DemoFragment.newInstance(colors[Random.nextInt(5)]))
            }

            removeFragment.setOnClickListener {
                fragmentAdapter.removeFragment()
            }

            setOrientation.setOnClickListener {
                if (viewPager2.orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
                    viewPager2.orientation = ViewPager2.ORIENTATION_VERTICAL
                } else {
                    viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                }
            }
        }
    }



}