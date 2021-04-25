package com.example.little.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.little.repository.dao.UserDao
import com.example.little.repository.entity.User

@Database(entities = [User::class], version = 2)
abstract class AppDataBase: RoomDatabase() {
    abstract fun userDao(): UserDao
}