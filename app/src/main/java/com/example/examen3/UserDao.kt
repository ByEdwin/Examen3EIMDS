package com.example.examen3

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): User?

    @Query("UPDATE users SET lastConnection = :lastConnection WHERE id = :userId")
    suspend fun updateLastConnection(userId: Int, lastConnection: String)
}