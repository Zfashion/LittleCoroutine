package com.example.little.repository

import android.content.Context
import androidx.room.Room

class DBHelper {

    companion object {
        private val INSTANCE by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            DBHelper()
        }
        private fun getInstance() = INSTANCE
        @Volatile private var db: AppDataBase? = null

        fun getDataBase(context: Context) = getInstance().getDb(context)

        fun getUserDao(context: Context) = getDataBase(context).userDao()
    }

    private fun getDb(context: Context) : AppDataBase {
        return db ?: synchronized(this) {
            db ?: buildDataBase(context).also { db = it }
        }
    }
    
    private fun buildDataBase(context: Context): AppDataBase {
        return Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            "database-name"
        ).build()
    }

}