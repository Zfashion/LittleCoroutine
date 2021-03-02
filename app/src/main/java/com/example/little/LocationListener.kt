package com.example.little

import android.os.Handler
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

class LocationListener : LifecycleObserver {

    private lateinit var mLifecycle: Lifecycle
    private lateinit var mCallBack: (Handler.Callback) -> Unit

    constructor(lifecycle: Lifecycle, callback: (Handler.Callback) -> Unit) {
        this.mLifecycle = lifecycle
        this.mCallBack = callback
        mLifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun init(owner: LifecycleOwner) {
        Log.d("LocationListener", "do create")
        val atLeast = mLifecycle.currentState.isAtLeast(Lifecycle.State.CREATED) //判断当前是否已经在生命周期onCreate
        Log.d("LocationListener", "current state is create? : $atLeast")
        val isOne = owner.lifecycle == mLifecycle
        Log.d("LocationListener", "$isOne")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start() {
        Log.d("LocationListener", "do start")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun resume() {
        Log.d("LocationListener", "to resume")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pause() {
        Log.d("LocationListener", "do pause")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop() {
        Log.d("LocationListener", "do stop")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroy() {
        Log.d("LocationListener", "to destroy")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onLifecycleChanged() {
        Log.d("LocationListener", "in on any...")
    }

}