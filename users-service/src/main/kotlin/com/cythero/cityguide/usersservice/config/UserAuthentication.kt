package com.cythero.cityguide.usersservice.config

import com.sun.security.auth.UserPrincipal
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserAuthentication(
    private val userDetails: UserDetails,
    private val authorities: Collection<GrantedAuthority> = emptyList()
) : AbstractAuthenticationToken(authorities) {
    override fun getCredentials(): Any? {
        return null
    }

    override fun getPrincipal(): Any {
        return userDetails
    }

    override fun getAuthorities(): Collection<GrantedAuthority>  {
        return authorities
    }
}
