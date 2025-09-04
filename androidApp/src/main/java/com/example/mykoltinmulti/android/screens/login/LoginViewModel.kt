package com.example.mykoltinmulti.android.screens.login

import androidx.lifecycle.ViewModel
import com.example.mykoltinmulti.auth.LoginResult
import com.example.mykoltinmulti.repository.LoginRepository

class LoginViewModel(
    private val repo: LoginRepository = LoginRepository()
) : ViewModel() {
    fun login(email: String, password: String): LoginResult = repo.login(email, password)
}