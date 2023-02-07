package com.cythero.cityguide.usersservice.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.SecurityFilterChain





@Configuration
@EnableWebSecurity
class SecurityConfiguration : WebSecurityConfigurerAdapter() {
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.csrf().disable().authorizeRequests().anyRequest().authenticated().and().httpBasic()
    }
}

/*
@EnableWebFluxSecurity
class SecurityConfig{

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain? {
        return http
            .csrf().disable()
            .authorizeExchange()
                .pathMatchers(HttpMethod.GET, "/**").permitAll() //.hasRole("USER")
            .anyExchange()
            .permitAll() //.authenticated()
            .and()
                .formLogin()
            .and()
                .httpBasic()
            .and()
                .build()
    }

    @Bean
    fun userDetailsService(): MapReactiveUserDetailsService? {
        val user = User.withDefaultPasswordEncoder()
            .username("user1")
            .password("password")
            .roles("USER")
            .authorities("USER")
            .build()

        val user2 = User.withUsername("user2")
            .password(NoOpPasswordEncoder.getInstance().encode("password"))
            .roles("USER")
            .build()

        val re = user.password
        val se = user2.password
        val s = re + se
        return MapReactiveUserDetailsService(user, user)
    }

}

 */