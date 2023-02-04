package com.cythero.cityguide.citiesservice.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import org.hibernate.Hibernate
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
@Table(name = "cities")
data class City(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    val id: Long,

    @Column(name = "country_id", nullable = false)
    val country_id: Long,

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(
        name = "country_id",
        referencedColumnName = "id",
        updatable = false,
        insertable = false,
    )
    var country: Country?,

    @Column(name = "location_id", unique = true, nullable = false)
    val location_id: Long,

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "location_id",
        referencedColumnName = "id",
        insertable = false,
        updatable = false,
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    val location: Location?,

    @Column(nullable = false)
    @NotBlank
    val name: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as City

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , country_id = $country_id , location_id = $location_id , name = $name )"
    }
}