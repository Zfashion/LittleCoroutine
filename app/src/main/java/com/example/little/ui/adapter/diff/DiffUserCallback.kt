package com.example.little.ui.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import com.example.little.repository.entity.User

class DiffUserCallback: DiffUtil.ItemCallback<User>() {

    /**
     * 判断是否是同一个item
     *
     * @param oldItem old Data
     * @param newItem New data
     * @return
     */
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.uid == newItem.uid
    }

    /**
     * 当是同一个item时，再判断内容是否发生改变
     *
     * @param oldItem old Data
     * @param newItem New data
     * @return
     */
    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.firstName == newItem.firstName && oldItem.lastName == newItem.lastName
    }

}