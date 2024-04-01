package com.example.uts_pam.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE email = :email")
    fun getUserByEmail(email: String): LiveData<User?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)
}