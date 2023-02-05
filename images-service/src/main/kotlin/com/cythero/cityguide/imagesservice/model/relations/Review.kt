package com.cythero.cityguide.imagesservice.model.relations

import jakarta.persistence.*

@Entity
@Table(name = "reviews")
data class Review (
    @Id
    @Column(nullable = false, updatable = false, insertable = false)
    val id: Long,
)