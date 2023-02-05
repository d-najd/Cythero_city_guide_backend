package com.cythero.cityguide.usersservice.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.hibernate.Hibernate
import java.util.*

@Entity
@Table(name = "users")
data class User (
    @Id
    // @GeneratedValue(strategy = GenerationType.UUID) doesn't work
    @Column(nullable = false, updatable = false)
    val id: String = UUID.randomUUID().toString(),

    @Column(name = "username", unique = true, nullable = false)
    @NotNull
    @NotEmpty
    val username: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as User

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , username = $username)"
    }

}