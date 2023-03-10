package com.cythero.cityguide.countriesservice.model.relations

import jakarta.persistence.*
import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.NotNull
import org.hibernate.Hibernate
import java.math.BigDecimal

@Entity
@Table(name = "locations")
data class Location (
    @Id
    @Column(nullable = false, insertable = false, updatable = false)
    val id: Long,
)
