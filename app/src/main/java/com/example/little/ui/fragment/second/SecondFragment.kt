package com.example.little.ui.fragment.second

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.little.MyViewModel
import com.example.little.R
import com.example.little.ui.adapter.diff.DiffUserCallback
import com.example.little.ui.fragment.main.MainFragment
import kotlinx.android.synthetic.main.fragment_main.*

class SecondFragment: Fragment() {

    private lateinit var roomAdapter: MainFragment.RoomAdapter
    private val model: MyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val layoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.VERTICAL, false)
        roomAdapter = MainFragment.RoomAdapter(null)
        //设置Diff Callback,只需设置一次
        roomAdapter.setDiffCallback(DiffUserCallback())
        recycler.layoutManager = layoutManager
        recycler.adapter = roomAdapter

        model.loadUsers()
        model.UserData.observe(viewLifecycleOwner) {
            val toMutableList = it.toMutableList()
            roomAdapter.setDiffNewData(toMutableList)
        }
    }
}