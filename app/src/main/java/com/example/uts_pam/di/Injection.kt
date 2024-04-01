package com.example.uts_pam.di

import android.content.Context
import com.example.uts_pam.database.UserRoomDatabase
import com.example.uts_pam.repository.UserRepository

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val database = UserRoomDatabase.getInstance(context)
        val userDao = database.userDao()
        return UserRepository.getInstance(userDao)
    }
}