package com.example.little.ui.fragment.coroutine

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.little.databinding.FragmentCoroutineBinding
import com.little.base.BaseBindingFragment
import kotlinx.android.synthetic.main.fragment_coroutine.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class CoroutineFragment : BaseBindingFragment<FragmentCoroutineBinding>() {

    private companion object val TAG = this.javaClass.simpleName

    private var job: Job? = null
    private var i: Int = 0
    private var _job: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun initView() {
            lifecycleScope.launch {
//                (1..10).asFlow()
//                    .map { num -> performRequest(num) }
//                    .collect { response -> textView.text = "${textView.text}$response" }

//                val time = measureTimeMillis {
//                    simple2()
////                        .buffer()
//                        .collect {
//                            delay(500)
//                            withContext(Dispatchers.Main.immediate) {
//                                textView.text = "${textView.text}\n$it"
//                            }
//                        }
//                }
//                println("Collected in $time ms")

                binding.startJob.setOnClickListener {
                    if (_job == null)  _job = updateTextWithTimeOut(i)
                }

                binding.cancelJob.setOnClickListener {
                    lifecycleScope.launch {
                        if (_job != null) {
                            _job?.cancel()
                            binding.textView.text = "job: stopped, index is $i"
                            _job = null
                        }
                    }
                }

                binding.asyncJob.setOnClickListener {
                    lifecycleScope.launch {
                        val netJob = async { performRequest() }
                        Log.i("Coroutine", "1")
                        val await = netJob.await()
                        Log.i("Coroutine", "2")
                        withContext(Dispatchers.Main) {
                            Log.i("Coroutine", "3")
                            binding.textView.text = await
                        }
                    }
                }
        }
        /*CoroutineScope(Dispatchers.Main.immediate).launch {

            val a = async {
                print("hello world")
            }
            val b = async {
                print("hello world")
            }
            awaitAll(a,b)

            delay(1000)
            print("!")
            launch {
                print("")
            }
            lifecycleScope.launch {
                print("")
            }
            measureTimeMillis {
                delay(10)
                val initValue1 = initValue1()
                val initValue2 = initValue2()

                print("")
            }
        }
        print("hello ")
    }*/
    }


    private fun updateTextWithCancel(index: Int): Job = lifecycleScope.launch(Dispatchers.Default) {
        try {
            var ding = index
            while (this.isActive) {
                ensureActive()
                withContext(Dispatchers.Main) {
                    binding.textView.text = "job: I'm sleeping $ding ..."
                }
                delay(500)
                ding++
                i = ding
            }
        } finally {
            withContext(NonCancellable + Dispatchers.Main) {
                binding.textView1.text = "I'm running finally"
                delay(1000)
                binding.textView1.text = "And I've just delayed for 1 sec because I'm non-cancellable"
            }
        }
    }

    private fun updateTextWithTimeOut(index: Int): Job = lifecycleScope.launch(Dispatchers.Default) {
        try {
            var ding = index
            withTimeout(5000) {
                while (this.isActive) {
//                    ensureActive()
                    withContext(Dispatchers.Main) {
                        binding.textView.text = "job: I'm sleeping $ding ..."
                    }
                    delay(500)
                    ding++
                    i = ding
                }
            }
        } finally {
            withContext(NonCancellable + Dispatchers.Main) {
                binding.textView1.text = "I'm running finally"
                delay(1000)
                binding.textView1.text = "And I've just delayed for 1 sec because I'm non-cancellable"
            }
        }
    }



        private suspend fun simple(): List<Int> = withContext(Dispatchers.IO) {
            delay(2000)
            return@withContext listOf<Int>(1, 2, 3)
        }

        private suspend fun simple2() = (1..10).asFlow().transform { value ->
            delay(500)
            emit(value)
        }.flowOn(Dispatchers.IO)

        private suspend fun initValue1(): Int {
            delay(1000)
            return 1
        }

        private suspend fun performRequest(): String = withContext(Dispatchers.IO) {
            try {
                withTimeout(3000) {
                    delay(3500) // imitate long-running asynchronous work
                    Log.i("Coroutine", "网络请求成功")
                    return@withTimeout "response 网络请求的结果"
                }
            } finally {
                Log.i("Coroutine", "网络请求失败")
                return@withContext "response 网络请求失败，改用其它方案"
            }
        }

        private suspend fun initValue2(): Int {
            delay(3000)
            return 3
        }

    }