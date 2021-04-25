package com.example.little.ui.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.little.MyViewModel
import com.example.little.R
import com.example.little.databinding.LayoutItemBinding
import com.example.little.repository.entity.User
import com.example.little.ui.adapter.diff.DiffUserCallback
import com.little.base.BaseBindingAdapter
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment: Fragment() {

    private lateinit var roomAdapter: RoomAdapter
    private val model: MyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, null)
//        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val layoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.VERTICAL, false)
        roomAdapter = RoomAdapter(null)
        //设置Diff Callback,只需设置一次
        roomAdapter.setDiffCallback(DiffUserCallback())
        recycler.layoutManager = layoutManager
        recycler.adapter = roomAdapter


        mAdd.setOnClickListener {
            model.addUser(roomAdapter.itemCount)
        }

        mDelete.setOnClickListener {
            val data = roomAdapter.data
            if (data.isNullOrEmpty()) {
                Toast.makeText(this.requireContext(), "没有数据了", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            data.last().runCatching {
                model.deleteUser(this)
            }
        }

        model.loadUsers()

        model.UserData.observe(viewLifecycleOwner) {
            val toMutableList = it.toMutableList()
            roomAdapter.setDiffNewData(toMutableList)
            recycler.smoothScrollToPosition(roomAdapter.itemCount)
        }

        next.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_secondFragment)
//            Navigation.createNavigateOnClickListener(R.id.secondFragment, null)
        }

        coroutine.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToCoroutineFragment())
        }

        bindingFm.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToTestVBFragment())
        }

        viewPager.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToViewPagerFragment())
        }

        multiTab.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToMultiTabFragment())
        }
    }

//    companion object class RoomAdapter(data: MutableList<User>?):
//        BaseQuickAdapter<User, BaseViewHolder>(R.layout.layout_item, data) {
//
//        override fun convert(holder: BaseViewHolder, item: User) {
//            holder.setText(R.id.txx, item.firstName + item.lastName)
//        }
//    }

    class RoomAdapter(data: MutableList<User>?) :
        BaseBindingAdapter<User, LayoutItemBinding>(data) {

        override fun convert(binding: LayoutItemBinding, position: Int, item: User) {
            binding.txx.text = "${(item.firstName + item.lastName)} + $position"
        }

    }
}