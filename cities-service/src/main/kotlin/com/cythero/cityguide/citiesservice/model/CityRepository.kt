package com.cythero.cityguide.citiesservice.model

import org.springframework.data.jpa.repository.JpaRepository

interface CityRepository : JpaRepository<City, Long>