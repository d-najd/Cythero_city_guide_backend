package com.cythero.cityguide.usersservice.model

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, String> {

    fun findByUsername(username: String): Optional<User>

    // fun deleteByUsername(username: String): Void

}