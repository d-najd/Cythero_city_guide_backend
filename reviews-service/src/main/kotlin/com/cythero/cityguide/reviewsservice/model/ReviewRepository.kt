package com.cythero.cityguide.reviewsservice.model

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface ReviewRepository : JpaRepository<Review, Long> {

    fun findByIdAndUserId(id: Long, userId: String): Optional<Review>

    fun deleteByIdAndUserId(id: Long, userId: String)

}