package com.cythero.cityguide.usersservice.config

import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

// PAIN
@Service
class UserService: ReactiveUserDetailsService {
    override fun findByUsername(username: String?): Mono<UserDetails> {
        return Mono.just(
            User.withUsername(username)
                .password(username)
                .authorities(emptyList())
                .build()
        )
    }
}
