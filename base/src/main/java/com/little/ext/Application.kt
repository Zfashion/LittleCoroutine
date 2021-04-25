package com.little.ext

import android.app.Application
import android.content.Context


private lateinit var base: Application

fun setAppDelegate(delegate: Application) = run { base = delegate }

val app: Application get() = base

val appContext: Context get() = app.applicationContext