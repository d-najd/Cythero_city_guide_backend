package com.cythero.cityguide.tokenservice.model

data class JwtTokenHolder(
    val token: String,
    val refreshToken: String,
)
