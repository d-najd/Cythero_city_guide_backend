package com.cythero.cityguide.reviewsservice.model

import com.cythero.cityguide.reviewsservice.model.relations.TouristAttraction
import com.cythero.cityguide.reviewsservice.model.relations.User
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.hibernate.Hibernate
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
@Table(name = "reviews")
data class Review (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false, updatable = false)
    val id: Long,

    @Column(name = "attraction_id", nullable = false, updatable = false)
    val attractionId: Long,

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

    @Column(name = "user_id", nullable = false, updatable = false)
    val userId: String,

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(
        name = "user_id",
        referencedColumnName = "id",
        updatable = false,
        insertable = false,
    )
    val user: User?,

    @Column(name = "stars", nullable = false, length = 1)
    val stars: Int,

    @Column(name = "title", nullable = false)
    val title: String,

    @Column(name = "description", length = 65534)
    val description: String?,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Review

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , attractionId = $attractionId , userId = $userId , stars = $stars , title = $title , description = $description )"
    }
}
