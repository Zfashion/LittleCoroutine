package com.example.little

import android.util.Log
import android.widget.Toast
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.little.repository.DBHelper
import com.example.little.repository.entity.User
import com.example.little.utils.IDUtils
import com.little.ext.appContext
import kotlinx.coroutines.*
import java.io.Serializable
import kotlin.random.Random

class MyViewModel : ViewModel() {

    var UserData = MutableLiveData<List<User>>()

    fun addUser(size: Int) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val nextInt1 = Random.nextInt(10, 100)
            val user = User(size, "莱昂纳德$nextInt1", "迪卡普里奥$nextInt1")
            DBHelper.getUserDao(appContext).insertAll(user)
            loadUsers()
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                DBHelper.getUserDao(appContext).delete(user)
                loadUsers()
            }
        }
    }

    fun loadUsers() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val all = DBHelper.getUserDao(appContext).getAll()
            UserData.postValue(all)
        }
    }

    private val mLiveData: MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>().also {
            viewModelScope.launch { loadData(5) }
        }
    }

    private suspend fun loadData(count: Int) {
        //Do an asynchronous operation to fetch data.
        CoroutineScope(Dispatchers.Default).launch {
            var nextInt = count
            val arrayList = if (mLiveData.value.isNullOrEmpty().not()) mLiveData.value!!
            else ArrayList()
            arrayList as ArrayList
            arrayList.clear()
            while (nextInt > 0) {
                val str: StringBuilder = StringBuilder("num is $nextInt")
                arrayList.add(str.toString())
                nextInt--
            }
            mLiveData.postValue(arrayList)
        }
    }

    fun getData(): MutableLiveData<List<String>> {
        return mLiveData
    }

    suspend fun pin(): String {
        var str = ""
        mLiveData.value?.forEach {ss ->
            str = if (str.isEmpty()) ss
            else "$str\n$ss"
        }
        return str
    }

    suspend fun reLoadData() : String {
        loadData(9999)
        return pin()
    }

}