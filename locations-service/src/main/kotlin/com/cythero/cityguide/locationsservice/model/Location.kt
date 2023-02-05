package com.cythero.cityguide.locationsservice.model

import jakarta.persistence.*
import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.NotNull
import org.hibernate.Hibernate
import java.math.BigDecimal

@Entity
@Table(
    name = "locations",
    uniqueConstraints = [
        UniqueConstraint(name = "locations_unique_0", columnNames = ["longitude", "latitude"])
    ]
)
data class Location (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    val id: Long,

    @Digits(integer = 20, fraction = 12)
    @Column(nullable = false)
    @NotNull
    val longitude: BigDecimal,

    @Digits(integer = 20, fraction = 12)
    @Column(nullable = false)
    @NotNull
    val latitude: BigDecimal,

    @Column(unique = true, nullable = false)
    @NotNull
    val address: String,

    @Column(name = "flag_path")
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
        return this::class.simpleName + "(id = $id , longitude = $longitude , latitude = $latitude , address = $address , flagPath = $flagPath )"
    }

}
