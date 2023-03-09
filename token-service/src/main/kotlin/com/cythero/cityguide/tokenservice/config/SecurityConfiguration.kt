package com.cythero.cityguide.tokenservice.config

import com.auth0.jwt.JWT
import com.cythero.cityguide.tokenservice.model.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.stereotype.Service
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Configuration
@EnableWebFluxSecurity
class SecurityConfig {
    @Bean
    fun securityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        http
            .csrf().disable()
            .authorizeExchange()
            .anyExchange().authenticated()
            .and()
            .oauth2ResourceServer()
            .jwt()
            .and()
            .and()
        return http.build()
    }

    inner class MyFilter : WebFilter {
        @Autowired
        lateinit var userRepository: UserRepository

        override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
            /* no need for this since token can't be generated without username and to get username we need email and an account
                saved in the db
            val token = JwtUtils.extractToken(exchange.request)
            val decodedToken = JWT.decode(token)
            val email = decodedToken.getClaim("email").toString()
            userRepository.findByGmail(decodedToken.getClaim("email").toString()).orElseThrow {
                throw CustomUnauthorizedException("user with email $email does not exist")
            }
             */
            chain.filter(exchange)
            return Mono.empty()
        }
    }
}
