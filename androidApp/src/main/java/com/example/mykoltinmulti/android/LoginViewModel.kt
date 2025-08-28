package com.example.mykoltinmulti.android

import androidx.lifecycle.ViewModel
import com.example.mykoltinmulti.auth.LoginRepository
import com.example.mykoltinmulti.auth.LoginResult

class LoginViewModel(
    private val repo: LoginRepository = LoginRepository()
) : ViewModel() {
    fun login(email: String, password: String): LoginResult = repo.login(email, password)
}
