package com.example.uts_pam.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uts_pam.database.User
import com.example.uts_pam.repository.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel(
    private var userRepository: UserRepository
) : ViewModel() {

    fun getProfileUser(email: String): LiveData<User?> {
        return userRepository.getUserByEmail(email)
    }
}