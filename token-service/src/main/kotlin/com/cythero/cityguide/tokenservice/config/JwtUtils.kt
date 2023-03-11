package com.cythero.cityguide.tokenservice.config

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.cythero.cityguide.tokenservice.model.JwtTokenHolder
import org.springframework.http.HttpHeaders
import org.springframework.http.server.reactive.ServerHttpRequest
import java.util.*

object JwtUtils {
    private const val secret = "secret"
    private val algorithm = Algorithm.HMAC512(secret)
    private const val STANDARD_EXPIRE_DATE = 100L * 24L * 60L * 60L * 1000L
    private const val REFRESH_EXPIRE_DATE = 365L * 24L * 60L * 60L * 1000L

    fun extractToken(request: ServerHttpRequest): String? {
        val header = request.headers.getFirst(HttpHeaders.AUTHORIZATION)
        return if (header != null && header.startsWith("Bearer ")) {
            header.substring(7)
        } else {
            null
        }
    }

    private fun generateStandardToken(username: String): String {
        return JWT.create()
            .withSubject("authentication")
            .withClaim("typ", "JWT")
            .withClaim("name", username)
            .withIssuedAt(Date(System.currentTimeMillis()))
            .withExpiresAt(Date(System.currentTimeMillis() + STANDARD_EXPIRE_DATE))
            .sign(algorithm)
    }

    private fun generateRefreshToken(username: String): String {
        return JWT.create()
            .withSubject("authentication")
            .withClaim("refresh", true)
            .withClaim("name", username)
            .withIssuedAt(Date(System.currentTimeMillis()))
            // .withNotBefore(Date(System.currentTimeMillis() + STANDARD_EXPIRE_DATE))
            .withExpiresAt(Date(System.currentTimeMillis() + REFRESH_EXPIRE_DATE))
            .sign(algorithm)
    }

    fun generateTokenHolder(username: String): JwtTokenHolder {
        return JwtTokenHolder(
            token = generateStandardToken(username),
            refreshToken = generateRefreshToken(username),
        )
    }

    fun verifyToken(token: String) {
        JWT.require(algorithm).build().verify(token)
    }
}
