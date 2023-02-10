package com.cythero.cityguide.usersservice.model

data class AuthenticationResponse(
    val token: String,
    val refreshToken: String? = null,
)
