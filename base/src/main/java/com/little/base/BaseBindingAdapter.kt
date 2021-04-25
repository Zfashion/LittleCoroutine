package com.little.base

import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.little.utils.inflateBindingWithGeneric

abstract class BaseBindingAdapter<Data, VB: ViewBinding>(data: MutableList<Data>?):
    BaseQuickAdapter<Data, BaseBindingAdapter.BaseBindingViewHolder<VB>>(-1, data) {


    override fun onCreateDefViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseBindingViewHolder<VB> = BaseBindingViewHolder(inflateBindingWithGeneric(parent))

    override fun convert(holder: BaseBindingViewHolder<VB>, item: Data) {
        convert(holder.binding, holder.layoutPosition, item)
    }


    abstract fun convert(binding: VB, position: Int, item: Data)

    class BaseBindingViewHolder<VB: ViewBinding>(val binding: VB): BaseViewHolder(binding.root)

}