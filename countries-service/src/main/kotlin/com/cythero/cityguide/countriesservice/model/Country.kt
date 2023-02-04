package com.cythero.cityguide.countriesservice.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import org.hibernate.Hibernate
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
@Table(
    name = "countries",
)
data class Country (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    val id: Long = -1L,

    @Column(nullable = false)
    val location_id: Long = -1L,

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "location_id",
        referencedColumnName = "id",
        insertable = false,
        updatable = false,
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    val location: Location? = null,

    @Column(unique = true, nullable = false)
    @NotBlank
    val name: String = "",
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Country

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , location_id = $location_id , name = $name )"
    }

}
