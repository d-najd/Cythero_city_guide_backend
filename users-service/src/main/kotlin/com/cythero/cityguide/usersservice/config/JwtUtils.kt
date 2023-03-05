package com.cythero.cityguide.usersservice.config

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.cythero.cityguide.usersservice.model.JwtTokenHolder
import com.sun.security.auth.UserPrincipal
import java.util.*

object JwtUtils {
    private const val secret = "secret"
     val algorithm = Algorithm.HMAC512(secret)
    private const val STANDARD_EXPIRE_DATE = 14L * 24L * 60L * 60L * 1000L
    private const val REFRESH_EXPIRE_DATE = 180L * 24L * 60L * 60L * 1000L

    private fun generateStandardToken(userPrincipal: UserPrincipal): String {
        return JWT.create()
            .withSubject("authentication")
            .withClaim("typ", "standard")
            .withClaim("name", userPrincipal.name)
            .withIssuedAt(Date(System.currentTimeMillis()))
            .withExpiresAt(Date(System.currentTimeMillis() + STANDARD_EXPIRE_DATE))
            .sign(algorithm)
    }

    private fun generateRefreshToken(userPrincipal: UserPrincipal): String {
        return JWT.create()
            .withSubject("authentication")
            .withClaim("typ", "refresh")
            .withClaim("name", userPrincipal.name)
            .withIssuedAt(Date(System.currentTimeMillis()))
            //.withNotBefore(Date(System.currentTimeMillis() + STANDARD_EXPIRE_DATE))
            .withExpiresAt(Date(System.currentTimeMillis() + REFRESH_EXPIRE_DATE))
            .sign(algorithm)
    }

    fun generateTokenHolder(userPrincipal: UserPrincipal): JwtTokenHolder {
        return JwtTokenHolder(
            token = generateStandardToken(userPrincipal),
            refreshToken = generateRefreshToken(userPrincipal),
        )
    }

    fun getUserPrincipalFromToken(token: String): UserPrincipal {
        val claims = JWT.require(algorithm).build().verify(token)
        return UserPrincipal(claims.getClaim("name").asString())
    }
}