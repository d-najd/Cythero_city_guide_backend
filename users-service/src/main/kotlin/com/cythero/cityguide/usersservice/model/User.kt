package com.cythero.cityguide.usersservice.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.hibernate.Hibernate
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

@Entity
@Table(name = "users")
data class User(
    @Id
    // @GeneratedValue(strategy = GenerationType.UUID) doesn't work
    @Column(nullable = false, updatable = false)
    val id: String = UUID.randomUUID().toString(),


    @Column(name = "username", unique = true, nullable = false)
    @NotNull
    @NotEmpty
    private val username: String,
) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        mutableListOf(SimpleGrantedAuthority("USER"))

    override fun getUsername(): String = username

    override fun getPassword(): String = username


    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as User

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , username = $username )"
    }

}

/*
@Entity
@Table(name = "users")
data class User: UserDetails (
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
 */