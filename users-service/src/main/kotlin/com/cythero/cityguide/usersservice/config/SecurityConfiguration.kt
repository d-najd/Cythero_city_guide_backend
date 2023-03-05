package com.cythero.cityguide.usersservice.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Configuration
@EnableWebFluxSecurity
class SecurityConfig {

    @Autowired
    lateinit var userService: UserService

    @Bean
    fun securityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        http
            .csrf().disable()
            .authorizeExchange()
            .pathMatchers("/api/testing/getAll").permitAll()
            .pathMatchers(HttpMethod.POST, "/api/generateTokenUsingRefreshToken").permitAll()
            .pathMatchers(HttpMethod.POST, "/api/generateToken").permitAll()
            .pathMatchers("/login").permitAll()
            .anyExchange().authenticated()
            .and()
            .addFilterAt(authenticationFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
        return http.build()
    }

    private fun authenticationFilter(): AuthenticationWebFilter {
        val filter = AuthenticationWebFilter(authenticationManager())
        filter.setServerAuthenticationConverter(JwtAuthenticationConverter())
        return filter
    }

    @Bean
    fun authenticationManager(): ReactiveAuthenticationManager {
        val authManager = UserDetailsRepositoryReactiveAuthenticationManager(userService)
        authManager.setPasswordEncoder(passwordEncoder())
        return authManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return NoOpPasswordEncoder.getInstance()
    }

    inner class JwtAuthenticationConverter : ServerAuthenticationConverter {
        override fun convert(exchange: ServerWebExchange): Mono<Authentication> {
            val token = extractToken(exchange.request)
            return if (token != null) {
                val userPrincipal = JwtUtils.getUserPrincipalFromToken(token)
                val authentication = UsernamePasswordAuthenticationToken(
                    userPrincipal,
                    userPrincipal.name,
                    null,
                )
                Mono.just(authentication)
            } else {
                Mono.empty()
            }
        }

        private fun extractToken(request: ServerHttpRequest): String? {
            val header = request.headers.getFirst(HttpHeaders.AUTHORIZATION)
            return if (header != null && header.startsWith("Bearer ")) {
                header.substring(7)
            } else {
                null
            }
        }
    }

}
