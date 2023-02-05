package com.cythero.cityguide.imagesservice.model.relations

import jakarta.persistence.*

@Entity
@Table(name = "cities")
data class City (
    @Id
    @Column(nullable = false, updatable = false, insertable = false)
    val id: Long,
)