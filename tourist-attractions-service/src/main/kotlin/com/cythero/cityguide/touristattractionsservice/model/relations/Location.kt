package com.cythero.cityguide.touristattractionsservice.model.relations

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.Hibernate

@Entity
@Table(name = "locations")
data class Location (
    @Id
    @Column(nullable = false, insertable = false, updatable = false)
    val id: Long,

    @Column(name = "flag_path", nullable = false, insertable = false, updatable = false)
    val flagPath: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Location

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , flagPath = $flagPath )"
    }
}
