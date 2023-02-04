package com.cythero.cityguide.locationsservice.model

import org.springframework.data.jpa.repository.JpaRepository
import java.math.BigDecimal
import java.util.*

interface LocationRepository : JpaRepository<Location, Long> {

    fun findByLongitudeAndLatitude(longitude: BigDecimal, latitude: BigDecimal): Optional<Location>

}