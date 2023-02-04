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
    val id: Long = -1L,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Country

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
}
