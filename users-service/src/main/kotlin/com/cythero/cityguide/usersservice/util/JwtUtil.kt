package com.cythero.cityguide.usersservice.util

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.util.Date
import java.util.HashMap
import kotlin.reflect.KFunction1

@Service
class JwtUtil {
    companion object {
        // TODO get this val from config
        private const val SECRET_KEY = "secret"
    }

    fun extractUsername(token: String): String {
        return extractClaim(token, Claims::getSubject)
    }

    fun extractExpiration(token: String): Date {
        return extractClaim(token, Claims::getExpiration)
    }

    fun <T> extractClaim(token: String, claimsResolver: KFunction1<Claims, T>): T {
        val claims: Claims = extractAllClaims(token)
        return claimsResolver.call(claims)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).body
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    fun generateToken(userDetails: UserDetails): String {
        val claims: Map<String, Any> = HashMap()
        return createToken(claims, userDetails.username)
    }

    private fun createToken(claims: Map<String, Any>, subject: String): String {
        return Jwts.builder().setClaims(claims).setSubject(subject)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + (15 * 24 * 60 * 60)))
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact()
    }

    fun validateToken(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

}