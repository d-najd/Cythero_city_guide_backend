package com.cythero.cityguide.usersservice.config

import com.cythero.cityguide.usersservice.model.User
import com.cythero.cityguide.usersservice.model.UserRepository
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UserService(val repository: UserRepository): ReactiveUserDetailsService {

    /*
    override fun loadUserByUsername(username: String?): UserDetails {
        return User(username = username!!)
        /*
        return repository.findByUsername(username!!)
            .orElseThrow { throw IllegalArgumentException("Unable to find user with username $username") }
         */
    }
     */

    override fun findByUsername(username: String?): Mono<UserDetails> {
        val user = repository.findByUsername(username!!).get()
        val re = Mono.just(user)

        return re.cast(UserDetails::class.java)
    }

}