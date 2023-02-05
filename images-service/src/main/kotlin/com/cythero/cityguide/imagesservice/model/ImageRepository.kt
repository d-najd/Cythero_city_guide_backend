package com.cythero.cityguide.imagesservice.model

import org.springframework.data.jpa.repository.JpaRepository

interface ImageRepository : JpaRepository<Image, Long>