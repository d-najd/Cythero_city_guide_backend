package com.cythero.cityguide.usersservice.config.security

import com.sun.security.auth.UserPrincipal
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono


@Configuration
@EnableWebFluxSecurity
class SecurityConfig {
    @Bean
    fun securityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        http
            .csrf().disable()
            .authorizeExchange()
            .pathMatchers("/api/testing/getAll").permitAll()
            .anyExchange().authenticated()
            .and()
            .addFilterAt(authenticationFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
        return http.build()
    }

    private fun authenticationFilter(): AuthenticationWebFilter {
        val filter = AuthenticationWebFilter(authenticationManager())
        filter.setServerAuthenticationConverter(jwtAuthenticationConverter())
        return filter
    }

    @Bean
    fun authenticationManager(): ReactiveAuthenticationManager = ReactiveAuthenticationManager { auth ->
        val userDetails = UserPrincipal(auth.name)
        Mono.just(UsernamePasswordAuthenticationToken(userDetails, auth.credentials, auth.authorities))
    }

    @Bean
    fun jwtAuthenticationConverter(): ServerAuthenticationConverter = ServerAuthenticationConverter { exchange ->
        val token = JwtUtils.extractToken(exchange.request)
        if (token != null) {
            val decodedToken = JwtUtils.verifyAndDecodeToken(token)
            if (decodedToken.claims["refresh"]?.asBoolean()?.equals(true) == true) {
                throw BadCredentialsException("Refresh tokens can not be used for authentication")
            }
            val userPrincipal = JwtUtils.getUserPrincipalFromDecodedToken(decodedToken)
            val authentication = UsernamePasswordAuthenticationToken(
                userPrincipal,
                userPrincipal.name,
                emptyList(),
            )
            Mono.just(authentication)
        } else {
            Mono.empty()
        }
    }
}
