package com.example.mykoltinmulti.auth

sealed class LoginError(message: String) : Throwable(message) {
    class InvalidEmail : LoginError("Email inválido")
    class InvalidPassword : LoginError("Senha inválida (mínimo 6 caracteres)")
    class Unauthorized : LoginError("Credenciais incorretas")
}

sealed class LoginResult {
    data object Success : LoginResult()
    data class Failure(val error: LoginError) : LoginResult()
}
