package com.cythero.cityguide.usersservice.model

data class AuthenticationRequest(
    val username: String,
    val password: String = username,
)