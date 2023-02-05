package com.cythero.cityguide.imagesservice.model

import com.cythero.cityguide.imagesservice.model.relations.City
import com.cythero.cityguide.imagesservice.model.relations.Review
import com.cythero.cityguide.imagesservice.model.relations.TouristAttraction
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.hibernate.Hibernate
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.lang.IllegalArgumentException

@Entity
@Table(name = "images")
data class Image (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false, updatable = false)
    val id: Long,

    @Column(name = "path", nullable = false)
    val path: String,

    @Column(name = "city_id", updatable = false)
    val cityId: Long?,

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

    @Column(name = "attraction_id", updatable = false)
    val attractionId: Long?,

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(
        name = "attraction_id",
        referencedColumnName = "id",
        updatable = false,
        insertable = false,
    )
    val attraction: TouristAttraction?,

    @Column(name = "review_id", updatable = false)
    val reviewId: Long?,

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(
        name = "review_id",
        referencedColumnName = "id",
        updatable = false,
        insertable = false,
    )
    val review: Review?,
) {
    @PrePersist
    fun validate() {
        var imageUsed = false

        fun Long?.validateUsed() = this?.let {
            if (!imageUsed) {
                imageUsed = true
            } else {
                throw IllegalArgumentException("One image can be used for one thing, create another image that points to the same url to solve this issue")
            }
        }

        cityId.validateUsed()
        attractionId.validateUsed()
        reviewId.validateUsed()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Image

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id )"
    }
}
