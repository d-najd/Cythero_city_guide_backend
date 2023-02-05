package com.cythero.cityguide.touristattractionsservice.model.relations

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "cities")
data class City(
    @Id
    @Column(nullable = false, updatable = false, insertable = false)
    val id: Long,
)