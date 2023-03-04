package com.cythero.cityguide.usersservice.model

import com.cythero.cityguide.usersservice.config.JwtUtils
import com.cythero.cityguide.usersservice.model.User.Companion.toUserPrincipal

data class JwtTokenHolder(
    val token: String,
    val refreshToken: String,
) {
    companion object {
        fun generateTokenFromUser(
            user: User
        ): JwtTokenHolder = JwtUtils.generateTokenHolder(user.toUserPrincipal())
    }
}
