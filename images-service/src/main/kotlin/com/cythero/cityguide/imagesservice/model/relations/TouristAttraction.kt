package com.cythero.cityguide.imagesservice.model.relations

import jakarta.persistence.*

@Entity
@Table(name = "tourist_attractions")
data class TouristAttraction (
    @Id
    @Column(nullable = false, updatable = false, insertable = false)
    val id: Long,
)