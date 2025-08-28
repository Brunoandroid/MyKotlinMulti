package com.example.mykoltinmulti.auth

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
