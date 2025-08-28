package com.example.mykoltinmulti.auth

object AuthValidator {
    private val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")

    fun validateEmail(email: String): Boolean = emailRegex.matches(email.trim())
    fun validatePassword(password: String): Boolean = password.trim().length >= 6
}
