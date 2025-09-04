package com.example.mykoltinmulti.repository

import com.example.mykoltinmulti.auth.AuthValidator
import com.example.mykoltinmulti.auth.LoginError
import com.example.mykoltinmulti.auth.LoginResult

interface ILoginRepository {
    fun login(email: String, password: String): LoginResult
}

class LoginRepository : ILoginRepository {
    override fun login(email: String, password: String): LoginResult {
        if (!AuthValidator.validateEmail(email)) return LoginResult.Failure(LoginError.InvalidEmail())
        if (!AuthValidator.validatePassword(password)) return LoginResult.Failure(LoginError.InvalidPassword())
        return if (password.trim() == "123456") LoginResult.Success
        else LoginResult.Failure(LoginError.Unauthorized())
    }
}
