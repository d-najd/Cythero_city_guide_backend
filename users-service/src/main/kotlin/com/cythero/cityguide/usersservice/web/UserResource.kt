package com.cythero.cityguide.usersservice.web

import com.cythero.cityguide.usersservice.model.User
import com.cythero.cityguide.usersservice.model.UserHolder
import com.cythero.cityguide.usersservice.model.UserRepository
import org.springframework.context.annotation.Primary
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.security.Principal
import java.util.UUID

@Primary
@RequestMapping("/api")
@RestController
class UserResource(
    val repository: UserRepository
) {
    @GetMapping("/testing/getAll")
    fun getAll(): Mono<UserHolder> {
        return Mono.just(UserHolder(repository.findAll()))
    }

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

    @GetMapping("/testing/getPrincipal")
    fun getPrincipal(principal: Principal): Principal {
        return principal
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