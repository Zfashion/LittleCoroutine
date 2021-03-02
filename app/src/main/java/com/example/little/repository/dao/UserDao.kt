package com.example.little.repository.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.little.repository.entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM user WHERE first_name LIKE :firstName AND last_name LIKE :lastName LIMIT 1")
    fun findByName(firstName: String, lastName: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE) //插入时如果重复则替换
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)

}