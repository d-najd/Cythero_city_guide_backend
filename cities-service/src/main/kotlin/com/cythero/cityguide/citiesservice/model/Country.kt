package com.cythero.cityguide.citiesservice.model

import jakarta.persistence.*
import org.hibernate.Hibernate

@Entity
@Table(
    name = "countries",
)
data class Country (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    val id: Long,
)
