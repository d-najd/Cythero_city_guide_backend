package com.cythero.cityguide.citiesservice.model

import jakarta.persistence.*
import org.hibernate.Hibernate

@Entity
@Table(name = "countries")
data class Country (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, insertable = false, updatable = false)
    val id: Long,
)
