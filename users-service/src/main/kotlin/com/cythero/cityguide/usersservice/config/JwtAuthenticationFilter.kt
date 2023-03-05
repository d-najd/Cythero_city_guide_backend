package com.cythero.cityguide.usersservice.config
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
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
        val authentication = UsernamePasswordAuthenticationToken(userPrincipal, null, null)
        SecurityContextHolder.getContext().authentication = authentication
        //if (authentication.isAuthenticated) {
         //   exchange.response.statusCode = HttpStatus.ACCEPTED
        //}
        return chain.filter(exchange)
    }
}
