package com.cythero.cityguide.touristattractionsservice.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.hibernate.Hibernate
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
@Table(name = "tourist_attractions")
data class TouristAttraction (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    val id: Long,

    @Column(name = "city_id", nullable = false)
    val city_id: Long,

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(
        name = "city_id",
        referencedColumnName = "id",
        updatable = false,
        insertable = false,
    )
    val city: City?,

    @Column(name = "location_id", nullable = false)
    val location_id: Long,

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(
        name = "location_id",
        referencedColumnName = "id",
        updatable = false,
        insertable = false,
    )
    val location: Location?,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "description", length = 65534)
    val description: String? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as TouristAttraction

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , city_id = $city_id , location_id = $location_id , name = $name , description = $description )"
    }
}