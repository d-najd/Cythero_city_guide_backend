package com.cythero.cityguide.usersservice.config

import org.springframework.cloud.config.environment.PropertySource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebSession
import org.springframework.web.server.session.WebSessionManager
import reactor.core.publisher.Mono
import reactor.util.annotation.NonNull
import java.time.Duration
import java.time.Instant


@Configuration
@EnableWebFluxSecurity
class SecurityConfiguration(
    val userDetailsService: UserDetailsService,
    val jwtRequestFilter: JwtRequestFilter,
) {
    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        http.csrf().disable() // TODO check if this can be get rid off
            .authorizeExchange().pathMatchers("/authenticate").permitAll()
            .anyExchange().authenticated().and()
            .exceptionHandling()
        http.addFilterBefore(jwtRequestFilter, SecurityWebFiltersOrder.AUTHENTICATION)
        return http.build()
    }

    @Bean
    fun webSessionManager(): WebSessionManager? {
        // Emulate SessionCreationPolicy.STATELESS
        return WebSessionManager { Mono.empty() }
    }
    /*
    @Bean
    fun webSessionManager(): WebSessionManager? {
        val re = WebSessionManager {
            Mono.just(object : WebSession {
                @NonNull
                override fun getId(): String {
                    return ""
                }

                @NonNull
                override fun getAttributes(): Map<String, Any> {
                    return HashMap()
                }

                override fun start() {}
                override fun isStarted(): Boolean {
                    return true
                }

                @NonNull
                override fun changeSessionId(): Mono<Void> {
                    return Mono.empty()
                }

                @NonNull
                override fun invalidate(): Mono<Void> {
                    return Mono.empty()
                }

                @NonNull
                override fun save(): Mono<Void> {
                    return Mono.empty()
                }

                override fun isExpired(): Boolean {
                    return false
                }

                @NonNull
                override fun getCreationTime(): Instant {
                    return Instant.now()
                }

                @NonNull
                override fun getLastAccessTime(): Instant {
                    return Instant.now()
                }

                override fun setMaxIdleTime(maxIdleTime: Duration) {}

                @NonNull
                override fun getMaxIdleTime(): Duration {
                    return Duration.ofMinutes(1)
                }
            })
        }
    }
     */

    /*
    @Bean
    fun userService(): UserDetailsService = userService

     */
    /*
    @Bean
    fun authenticationManager(detailsService: ReactiveUserDetailsService?): ReactiveAuthenticationManager? {
        return UserDetailsRepositoryReactiveAuthenticationManager(detailsService)
    }
     */
    /*
    @Bean
    fun userDetailsService(): MapReactiveUserDetailsService {
        val user = User.withDefaultPasswordEncoder()
            .username("user")
            .password("password")
            .roles("USER")
            .build()
        return MapReactiveUserDetailsService(user)
    }
     */

    @Bean
    fun passwordEncoder(): PasswordEncoder =
        NoOpPasswordEncoder.getInstance()
}
