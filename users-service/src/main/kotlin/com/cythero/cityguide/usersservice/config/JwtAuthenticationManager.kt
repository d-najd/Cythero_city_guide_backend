package com.cythero.cityguide.usersservice.config

import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import reactor.core.publisher.Mono

class JwtAuthenticationManager : ReactiveAuthenticationManager {
    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        val authToken = authentication as? JwtAuthenticationToken ?: return Mono.empty()
        val username = JwtUtils.getUserPrincipalFromToken(authToken.token).name ?: return Mono.empty()
        // Retrieve user details from authentication service or database
        val userDetails = User.withUsername(username).password("").roles("USER").build()
        // Validate JWT token and create custom Authentication object
        return Mono.just(authToken)
            .map { UserAuthentication(userDetails) }
    }
}
