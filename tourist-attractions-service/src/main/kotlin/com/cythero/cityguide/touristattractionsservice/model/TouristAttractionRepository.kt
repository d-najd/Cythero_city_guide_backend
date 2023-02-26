package com.cythero.cityguide.touristattractionsservice.model

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface TouristAttractionRepository : JpaRepository<TouristAttraction, Long> {
   // override fun findAll(pageable: Pageable): Page<TouristAttraction>

}