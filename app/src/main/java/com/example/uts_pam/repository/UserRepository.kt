package com.example.uts_pam.repository

import androidx.lifecycle.LiveData
import com.example.uts_pam.database.User
import com.example.uts_pam.database.UserDao


class UserRepository private constructor(
    private val userDao: UserDao
){
    suspend fun registerUser(user: User){
        userDao.insertUser(user)
    }

    fun getUserByEmail(email: String): LiveData<User?> {
        return userDao.getUserByEmail(email)
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userDao: UserDao
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userDao)
        }.also { instance = it }
    }
}