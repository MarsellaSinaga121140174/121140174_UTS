package com.example.uts_pam.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uts_pam.database.User
import com.example.uts_pam.repository.UserRepository
import kotlinx.coroutines.launch

class RegisterViewModel(
    private var userRepository: UserRepository
) : ViewModel() {

    fun registerUser(user: User) {
        viewModelScope.launch {
            userRepository.registerUser(user)
        }
    }
}