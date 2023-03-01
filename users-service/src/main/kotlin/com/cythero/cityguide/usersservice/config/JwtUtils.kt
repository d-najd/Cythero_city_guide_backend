package com.cythero.cityguide.usersservice.config

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.sun.security.auth.UserPrincipal
import java.util.*

object JwtUtils {
    private const val secret = "secret"
    private val algorithm = Algorithm.HMAC512(secret)

    fun generateToken(userPrincipal: UserPrincipal): String {
        return JWT.create()
            .withSubject("authentication")
            .withIssuedAt(Date(System.currentTimeMillis()))
            .withExpiresAt(Date(System.currentTimeMillis() + (14 * 24 * 60 * 60 * 1000)))
            .withClaim("name", userPrincipal.name)
            .sign(algorithm)
    }

    fun getUserPrincipalFromToken(token: String): UserPrincipal {
        val claims = JWT.require(algorithm).build().verify(token)
        return UserPrincipal(claims.getClaim("name").asString())
    }
}