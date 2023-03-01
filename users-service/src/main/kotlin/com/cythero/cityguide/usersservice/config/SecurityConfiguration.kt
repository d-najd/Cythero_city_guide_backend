package com.cythero.cityguide.usersservice.config


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        http
            .csrf().disable()
            .authorizeExchange()
            .pathMatchers("/testing/getAll").permitAll()
            .pathMatchers("/login").permitAll()
            .anyExchange().permitAll()
            .and()
            .addFilterAt(jwtAuthenticationFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
            //.httpBasic().and()
            //.formLogin()
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
    fun jwtAuthenticationFilter(): JwtAuthenticationFilter {
        return JwtAuthenticationFilter()
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
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