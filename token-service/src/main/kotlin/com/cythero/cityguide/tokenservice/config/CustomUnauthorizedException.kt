package com.cythero.cityguide.tokenservice.config

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/** TODO replace this */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
class CustomUnauthorizedException(message: String?) : RuntimeException(message)