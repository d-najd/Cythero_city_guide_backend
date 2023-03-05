package com.cythero.cityguide.usersservice.config

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

class JwtAuthenticationFilter : WebFilter {
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val request = exchange.request
        val authorizationHeader = request.headers.getFirst("Authorization")
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return chain.filter(exchange)
        }
        val token = authorizationHeader.substring(7)
        val userPrincipal = JwtUtils.getUserPrincipalFromToken(token)
        //val authentication = UserAuthentication(userPrincipal)
        //SecurityContextHolder.getContext().authentication = authentication
        return chain.filter(exchange)
    }
}
