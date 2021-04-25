package com.example.little.ui.fragment.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentAdapter: FragmentStateAdapter {

    constructor(fgManager: FragmentManager, lifeCycle: Lifecycle) : super(fgManager, lifeCycle)

    constructor(fgActivity: FragmentActivity): super(fgActivity)

    constructor(fragment: Fragment): super(fragment)

    constructor(fgList: MutableList<Fragment>, fgActivity: FragmentActivity): this(fgActivity) {
        fragmentList.addAll(fgList)
    }

    private var fragmentList: ArrayList<Fragment> = arrayListOf()

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]

    fun addFragment(fg: Fragment) {
        fragmentList.add(fg)
        notifyDataSetChanged()
    }

    fun removeFragment() {
        if (fragmentList.size > 0) {
            fragmentList.removeLast()
            notifyDataSetChanged()
        }
    }
}