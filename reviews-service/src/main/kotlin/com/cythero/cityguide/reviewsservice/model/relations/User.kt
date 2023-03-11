package com.cythero.cityguide.reviewsservice.model.relations

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.Hibernate

@Entity
@Table(name = "users")
data class User (
    @Id
    @Column(nullable = false, updatable = false, insertable = false)
    val id: String,

    @Column(name = "username", insertable = false, updatable = false)
    val username: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as User

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , username = $username )"
    }
}
