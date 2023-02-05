package com.cythero.cityguide.reviewsservice.model.relations

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "users")
data class User (
    @Id
    @Column(nullable = false, updatable = false, insertable = false)
    val id: String,
)
