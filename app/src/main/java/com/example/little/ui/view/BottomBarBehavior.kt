package com.example.little.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout

class BottomBarBehavior: CoordinatorLayout.Behavior<View> {

    var added = false
    var defaultDependencyTop = -1
    var offset = 0
    var offsetChangeListener = OffsetChangedListener(this)

    companion object class OffsetChangedListener(private val behavior: BottomBarBehavior): AppBarLayout.OnOffsetChangedListener {

        override fun onOffsetChanged(p0: AppBarLayout?, p1: Int) {
            behavior.offset = p1
        }
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        if (defaultDependencyTop == -1) {
            if (dependency is AppBarLayout) {
                (dependency as AppBarLayout).removeOnOffsetChangedListener(offsetChangeListener)
            }
            defaultDependencyTop = dependency.top - offset
        }
        child.translationY = (-dependency.top.toFloat() + defaultDependencyTop.toFloat())
        return true
    }

    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        if ((dependency is AppBarLayout).not()) return false
        if (added) return true
        added = true
        (dependency as AppBarLayout).addOnOffsetChangedListener(offsetChangeListener)
        return true
    }
}