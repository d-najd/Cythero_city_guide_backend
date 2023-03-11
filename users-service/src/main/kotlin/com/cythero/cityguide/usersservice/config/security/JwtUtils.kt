package com.cythero.cityguide.usersservice.config.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.sun.security.auth.UserPrincipal
import org.springframework.http.HttpHeaders
import org.springframework.http.server.reactive.ServerHttpRequest
import java.util.*

object JwtUtils {
    private const val secret = "secret"
    private val algorithm = Algorithm.HMAC512(secret)

    fun getUserPrincipalFromDecodedToken(decodedToken: DecodedJWT): UserPrincipal {
        return UserPrincipal(decodedToken.claims["name"]!!.asString())
    }

    fun verifyAndDecodeToken(token: String): DecodedJWT {
        return JWT.require(algorithm).build().verify(token)
    }

    fun extractToken(request: ServerHttpRequest): String? {
        val header = request.headers.getFirst(HttpHeaders.AUTHORIZATION)
        return if (header != null && header.startsWith("Bearer ")) {
            header.substring(7)
        } else {
            null
        }
    }
}
