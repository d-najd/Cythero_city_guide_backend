package com.cythero.cityguide.tokenservice.web

import com.auth0.jwt.JWT
import com.cythero.cityguide.tokenservice.config.CustomUnauthorizedException
import com.cythero.cityguide.tokenservice.config.JwtUtils
import com.cythero.cityguide.tokenservice.model.JwtTokenHolder
import com.cythero.cityguide.tokenservice.model.UserRepository
import jakarta.annotation.security.PermitAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RequestMapping("/api")
@RestController
class TokenResource {
    @Autowired
    lateinit var repository: UserRepository

    @PostMapping("/generateToken")
    fun generateToken(
        @AuthenticationPrincipal principal: JwtAuthenticationToken
    ): Mono<JwtTokenHolder> {
        val tokenType = principal.token.headers["typ"]
        if(tokenType != "JWT") {
            throw IllegalAccessException("Token must be of type standard JWT")
        }
        val email = principal.token.getClaimAsString("email")
        val user = repository.findByGmail(email).orElseThrow {
            CustomUnauthorizedException("User with email $email does not exist")
        }
        return Mono.just(JwtUtils.generateTokenHolder(user.username))
    }

    @PermitAll
    @PostMapping("/generateTokenUsingRefreshToken")
    fun generateFromRefreshToken(
        @RequestParam refreshToken: String,
    ): Mono<JwtTokenHolder> {
        val decodedToken = JWT.decode(refreshToken)
        if(decodedToken.getHeaderClaim("typ").asString() != "JWT") {
            throw IllegalAccessException("Token must be of type standard JWT")
        }
        if(!decodedToken.getClaim("refresh").asBoolean()) {
            throw IllegalAccessException("Token must be of type refresh")
        }
        JwtUtils.verifyToken(refreshToken)
        val username = decodedToken.getClaim("name").asString()
        return Mono.just(JwtUtils.generateTokenHolder(username))
    }
}