package com.cythero.cityguide.usersservice.config


import com.cythero.cityguide.usersservice.util.JwtUtil
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono


/*
@Component
class JwtRequestFilter(
    private val userDetailsService: UserDetailsService,
    private val jwtUtil: JwtUtil,
) : OncePerRequestFilter() {
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val authorizationHeader: String = request.getHeader("Authorization")
        var username: String? = null
        var jwt: String? = null

        if (authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7)
            username = jwtUtil.extractUsername(jwt)
        }
        if (username != null && SecurityContextHolder.getContext().authentication == null) {
            // TODO I doubt that this line will go through
            val userDetails: UserDetails = userDetailsService.findByUsername(username).block()!!
            if (jwtUtil.validateToken(jwt!!, userDetails)) {
                val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.authorities
                )
                usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
            }
        }
        chain.doFilter(request, response)
    }
}
 */

@Component
class JwtRequestFilter(
    private val userDetailsService: UserDetailsService,
    private val jwtUtil: JwtUtil,
): WebFilter {
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val authorizationHeader: String = exchange.request.headers.getFirst("Authorization")!!
        var username: String? = null
        var jwt: String? = null

        if (authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7)
            username = jwtUtil.extractUsername(jwt)
        }
        if (username != null && SecurityContextHolder.getContext().authentication == null) {
            // TODO I doubt that this line will go through
            val userDetails: UserDetails = userDetailsService.findByUsername(username).block()!!
            if (jwtUtil.validateToken(jwt!!, userDetails)) {
                val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.authorities
                )
                usernamePasswordAuthenticationToken.details = WebAuthenticationDetails(exchange.request.remoteAddress.toString(), exchange.request.id)
                SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
            }
        }
        return chain.filter(exchange)
    }
}
