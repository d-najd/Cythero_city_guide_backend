package com.cythero.cityguide.reviewsservice.model

import org.springframework.data.jpa.repository.JpaRepository

interface ReviewRepository : JpaRepository<Review, Long>