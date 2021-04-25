package com.example.little

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.little.databinding.ActivityMain2Binding
import com.example.little.repository.entity.User
import com.example.little.ui.adapter.diff.DiffUserCallback
import com.little.base.BaseBindingActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseBindingActivity<ActivityMain2Binding>() {

//    private val model: MyViewModel by viewModels()
//    private lateinit var roomAdapter: RoomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        roomAdapter = RoomAdapter(null)
//        //设置Diff Callback,只需设置一次
//        roomAdapter.setDiffCallback(DiffUserCallback())
//        recycler.layoutManager = layoutManager
//        recycler.adapter = roomAdapter
//
//
//        mAdd.setOnClickListener {
//            model.addUser(roomAdapter.itemCount)
//        }
//
//        mDelete.setOnClickListener {
//            val data = roomAdapter.data
//            if (data.isNullOrEmpty()) {
//                Toast.makeText(this, "没有数据了", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//            data.last().runCatching {
//                model.deleteUser(this)
//            }
//        }
//
//        model.loadUsers()
//        model.UserData.observe(this) {
//            val toMutableList = it.toMutableList()
//            roomAdapter.setDiffNewData(toMutableList)
//            recycler.smoothScrollToPosition(roomAdapter.itemCount)
//        }
    }

    override fun initView() {

    }
//
//    companion object class RoomAdapter(data: MutableList<User>?): BaseQuickAdapter<User, BaseViewHolder>(R.layout.layout_item, data) {
//
//        override fun convert(holder: BaseViewHolder, item: User) {
//            holder.setText(R.id.txx, item.firstName + item.lastName)
//        }
//    }
}