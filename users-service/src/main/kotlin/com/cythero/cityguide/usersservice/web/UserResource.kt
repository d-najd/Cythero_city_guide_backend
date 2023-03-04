package com.cythero.cityguide.usersservice.web

import com.cythero.cityguide.usersservice.config.JwtUtils
import com.cythero.cityguide.usersservice.model.JwtTokenHolder
import com.cythero.cityguide.usersservice.model.User
import com.cythero.cityguide.usersservice.model.UserHolder
import com.cythero.cityguide.usersservice.model.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RequestMapping("/api")
@RestController
class UserResource(
    val repository: UserRepository
) {
    @GetMapping("/testing/getAll")
    fun getAll(): UserHolder {
        return UserHolder(repository.findAll())
    }

    @GetMapping("/{id}")
    fun getById(
        @PathVariable id: String
    ): User {
        return repository.findById(id).orElseThrow { throw IllegalArgumentException("Invalid id $id") }
    }

    @GetMapping("/username/{username}")
    fun getByUsername(
        @PathVariable username: String
    ): User {
        return repository.findByUsername(username).orElseThrow { throw
            IllegalArgumentException("invalid username $username")
        }
    }

    @PostMapping
    fun createUser(
        @RequestBody pojo: User,
    ): User {
        repository.findByUsername(pojo.username).ifPresent {
            throw IllegalArgumentException("User with username ${pojo.username} already exists")
        }
        return repository.save(pojo.copy(
            id = UUID.randomUUID().toString()
        ))
    }

    @PostMapping("/generateToken")
    fun generateToken(
        @RequestParam username: String,
    ): JwtTokenHolder {
        val user = repository.findByUsername(username).orElseThrow {
            throw IllegalArgumentException("User with credentials $username does not exist")
        }
        return JwtTokenHolder.generateTokenFromUser(user)
    }

    @PostMapping("/generateTokenUsingRefreshToken")
    fun generateFromRefreshToken(
        @RequestParam refreshToken: String,
    ): JwtTokenHolder {
        val userPrincipal = JwtUtils.getUserPrincipalFromToken(refreshToken)
        val user = repository.findByUsername(userPrincipal.name).orElseThrow {
            throw IllegalArgumentException("User with credentials ${userPrincipal.name} does not exist")
        }
        return JwtTokenHolder.generateTokenFromUser(user)
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