package com.cythero.cityguide.tokenservice.model

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

/**
 * this repository is to not be used for modifying data in any way shape or form
 */
interface UserRepository : JpaRepository<User, String> {

    fun findByGmail(gmail: String): Optional<User>

}
