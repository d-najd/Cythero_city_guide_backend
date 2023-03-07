package com.cythero.cityguide.touristattractionsservice.model.relations

import com.cythero.cityguide.touristattractionsservice.model.TouristAttraction
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.hibernate.Hibernate
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
@Table(name = "images")
data class Image (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false, updatable = false, insertable = false)
    val id: Long,

    @Column(name = "path", nullable = false, insertable = false, updatable = false)
    val path: String,

    @Column(name = "attraction_id", updatable = false, insertable = false)
    val attractionId: Long?,

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(
        name = "attraction_id",
        referencedColumnName = "id",
        updatable = false,
        insertable = false,
    )
    val attraction: TouristAttraction?,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Image

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , path = $path , attractionId = $attractionId )"
    }

}
