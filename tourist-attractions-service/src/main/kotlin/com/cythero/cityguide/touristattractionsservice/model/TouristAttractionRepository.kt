package com.cythero.cityguide.touristattractionsservice.model

import org.springframework.data.jpa.repository.JpaRepository

interface TouristAttractionRepository : JpaRepository<TouristAttraction, Long>