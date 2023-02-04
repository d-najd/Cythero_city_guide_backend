package com.cythero.cityguide.countriesservice.model

import org.springframework.data.jpa.repository.JpaRepository

interface CountryRepository : JpaRepository<Country, Long>

