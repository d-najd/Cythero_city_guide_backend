package com.cythero.cityguide.tokenservice.model

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

    @Column(name = "username", insertable = false, updatable = false)
    val username: String,

    @Column(name = "gmail", insertable = false, updatable = false)
    val gmail: String,
)
