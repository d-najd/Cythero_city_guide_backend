package com.cythero.cityguide.citiesservice.model

import jakarta.persistence.*
import org.hibernate.Hibernate

@Entity
@Table(name = "countries")
data class Country (
    @Id
    @Column(nullable = false, insertable = false, updatable = false)
    val id: Long,
)
