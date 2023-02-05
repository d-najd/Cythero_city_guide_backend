package com.cythero.cityguide.citiesservice.model.relations

import jakarta.persistence.*
import org.hibernate.Hibernate

@Entity
@Table(name = "countries")
data class Country (
    @Id
    @Column(nullable = false, insertable = false, updatable = false)
    val id: Long,
)
