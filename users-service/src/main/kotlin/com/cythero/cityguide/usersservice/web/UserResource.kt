package com.cythero.cityguide.usersservice.web

import com.cythero.cityguide.usersservice.config.JwtUtils
import com.cythero.cityguide.usersservice.model.JwtTokenHolder
import com.cythero.cityguide.usersservice.model.User
import com.cythero.cityguide.usersservice.model.UserHolder
import com.cythero.cityguide.usersservice.model.UserRepository
import org.springframework.context.annotation.Primary
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.util.UUID

@Primary
@RequestMapping("/api")
@RestController
class UserResource(
    val repository: UserRepository
): ReactiveUserDetailsService {
    @GetMapping("/testing/getAll")
    fun getAll(): Mono<UserHolder> {
        return Mono.just(UserHolder(repository.findAll()))
    }

    @GetMapping("/{id}")
    fun getById(
        @PathVariable id: String
    ): Mono<User> {
        return Mono.just(repository.findById(id).orElseThrow { throw IllegalArgumentException("Invalid id $id") })
    }


    override fun findByUsername(username: String?): Mono<UserDetails> = Mono.justOrEmpty(
        username?.let {
            repository.findByUsername(it).orElseThrow {
                IllegalArgumentException("invalid username $username")
            }
        }
    )
    /*
        return Mono.justOrEmpty(
            username?.let {
                repository.findByUsername(it).orElseThrow {
                    IllegalArgumentException("invalid username $username")
                }
            }
        )

     */

    @GetMapping("/username/{username}")
    fun getByUsername(
        @PathVariable username: String
    ): Mono<User> = Mono.justOrEmpty(
        repository.findByUsername(username).orElseThrow {
            IllegalArgumentException("invalid username $username")
        }
    )


    @PostMapping
    fun createUser(
        @RequestBody pojo: User,
    ): Mono<User> {
        repository.findByUsername(pojo.username).ifPresent {
            throw IllegalArgumentException("User with username ${pojo.username} already exists")
        }
        return Mono.just(repository.save(pojo.copy(
            id = UUID.randomUUID().toString()
        )))
    }

    @PostMapping("/generateToken")
    fun generateToken(
        @RequestParam username: String,
    ): Mono<JwtTokenHolder> {
        val user = repository.findByUsername(username).orElseThrow {
            throw IllegalArgumentException("User with credentials $username does not exist")
        }
        return Mono.just(JwtTokenHolder.generateTokenFromUser(user))
    }

    @PostMapping("/generateTokenUsingRefreshToken")
    fun generateFromRefreshToken(
        @RequestParam refreshToken: String,
    ): Mono<JwtTokenHolder> {
        val userPrincipal = JwtUtils.getUserPrincipalFromToken(refreshToken)
        val user = repository.findByUsername(userPrincipal.name).orElseThrow {
            throw IllegalArgumentException("User with credentials ${userPrincipal.name} does not exist")
        }
        return Mono.just(JwtTokenHolder.generateTokenFromUser(user))
    }

    // Put request intentionally left out

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: String
    ) {
        repository.deleteById(id)
    }



    /* Doesn't want to work for some reason
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/username/{username}")
    fun deleteByUsername(
        @PathVariable username: String
    ) {
        repository.deleteByUsername(username)
    }
     */
}