package com.cythero.cityguide.usersservice.config


import com.cythero.cityguide.usersservice.web.UserResource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter

@Configuration
@EnableWebFluxSecurity
class SecurityConfig {

    @Autowired
    lateinit var userResource: UserResource

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
        /*
        http
            .csrf().disable()
            .authenticationManager(authenticationManager)
            .authorizeExchange()
            .pathMatchers("/login").permitAll()
            .anyExchange().authenticated()
            .and().addFilterAt(jwtAuthenticationFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
        return http.build()

         */
    }


    @Bean
    fun authenticationFilter(): AuthenticationWebFilter {
        return AuthenticationWebFilter(authenticationManager())
    }

    @Bean
    fun authenticationManager(): ReactiveAuthenticationManager {
        return JwtAuthenticationManager()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return NoOpPasswordEncoder.getInstance()
    }
}

/*
@Component
class JwtAuthenticationManager() : ReactiveAuthenticationManager {
    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        return Mono.just(authentication)
            .onErrorResume { Mono.empty() }
            .map { jws ->
                UsernamePasswordAuthenticationToken(
                    jws.principal,
                    authentication.credentials as String,
                    mutableListOf(SimpleGrantedAuthority("ROLE_USER"))
                )
            }
    }
}

/*
@Configuration
@EnableWebFluxSecurity
class MyExplicitSecurityConfiguration(
    val userService: UserService
) {
    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        http
            .authorizeExchange()
            .anyExchange().authenticated()
            .and()
            .httpBasic().and()
            .formLogin()
        return http.build()
    }



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


 */