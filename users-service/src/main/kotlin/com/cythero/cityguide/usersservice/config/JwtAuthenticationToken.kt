package com.cythero.cityguide.usersservice.config

import com.auth0.jwt.JWT
import com.cythero.cityguide.usersservice.config.JwtUtils.algorithm
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority

class JwtAuthenticationToken(val token: String) : Authentication {
    private var authenticated = false

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf()
    }

    override fun setAuthenticated(isAuthenticated: Boolean) {
        this.authenticated = isAuthenticated
    }

    override fun isAuthenticated(): Boolean {
        return authenticated
    }

    override fun getPrincipal(): Any {
        return token
    }

    override fun getCredentials(): Any {
        return ""
    }

    override fun getDetails(): Any {
        return ""
    }

    override fun getName(): String {
        return ""
        // return JwtUtils.getUserPrincipalFromToken(token).name
    }
}