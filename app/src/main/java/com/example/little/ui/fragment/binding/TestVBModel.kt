package com.example.little.ui.fragment.binding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.little.retrofit.ApiResult
import com.little.retrofit.api.TranslateApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TestVBModel: ViewModel() {

    private val _translateResult: MutableLiveData<String> = MutableLiveData()

    val translateResult: LiveData<String>
        get() = _translateResult

    fun translate(word: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = TranslateApi.retrofitService.translate(word)) {
                is ApiResult.Success -> {
                    result.data?.let { _translateResult.postValue(it.translateResult[0][0].tgt) }
                }

                is ApiResult.Failure -> {
                    _translateResult.postValue("errorCode: ${result.errorCode}, errorMsg: ${result.errorMsg}")
                }
            }
        }
    }

}