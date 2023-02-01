package com.cythero.cityguide.usersservice.web

import com.cythero.cityguide.usersservice.model.User
import com.cythero.cityguide.usersservice.model.UserHolder
import com.cythero.cityguide.usersservice.model.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.lang.IllegalArgumentException
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

@ExperimentalStdlibApi
@RequestMapping("/api")
@RestController
class UserResource(val repository: UserRepository) {
    @GetMapping("/testing/getAll")
    fun getAll(): UserHolder {
        return UserHolder(repository.findAll())
    }

    @GetMapping("/{id}")
    fun getById(
        @PathVariable id: String
    ): User {
        return repository.findById(id).getOrNull() ?: throw IllegalArgumentException("invalid id $id")
    }

    @GetMapping("/username/{username}")
    fun getByUsername(
        @PathVariable username: String
    ): User {
        return repository.findByUsername(username).getOrNull() ?: throw IllegalArgumentException("invalid username $username")
    }

    @PostMapping
    fun post(
        @RequestBody pojo: User,
    ): User {
        if (repository.findByUsername(pojo.username).isPresent) {
            throw IllegalArgumentException("User with username ${pojo.username} already exists")
        }
        return repository.save(pojo.copy(
            id = UUID.randomUUID().toString()
        ))
    }

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