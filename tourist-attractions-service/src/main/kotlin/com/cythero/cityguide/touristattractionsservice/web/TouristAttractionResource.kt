package com.cythero.cityguide.touristattractionsservice.web

import com.cythero.cityguide.touristattractionsservice.model.TouristAttraction
import com.cythero.cityguide.touristattractionsservice.model.TouristAttractionHolder
import com.cythero.cityguide.touristattractionsservice.model.TouristAttractionRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RequestMapping("/api")
@RestController
class TouristAttractionResource(val repository: TouristAttractionRepository) {
    @GetMapping("/testing/getAll")
    fun getAll(): TouristAttractionHolder {
        return TouristAttractionHolder(repository.findAll())
    }

    @GetMapping("/{id}")
    fun getById(
        @PathVariable id: Long
    ): TouristAttraction {
        return repository.findById(id).orElseThrow { throw IllegalArgumentException("Invalid id $id") }
    }

    @PostMapping
    fun post(
        @RequestBody pojo: TouristAttraction,
    ): TouristAttraction {
        repository.findById(pojo.id).ifPresent {
            throw IllegalArgumentException("field with id ${pojo.id} already exists")
        }
        return repository.save(pojo)
    }

    @PutMapping("/{id}")
    fun put(
        @PathVariable id: Long,
        @RequestParam("locationId") locationId: Long? = null,
        @RequestParam("cityId") cityId: Long? = null,
        @RequestParam("name") name: String? = null,
        @RequestParam("description") description: String? = null,
        @RequestParam("returnBody") shouldReturnBody: Boolean = true,
    ): TouristAttraction? {
        val persistedTouristAttraction = repository.findById(id).orElseThrow { throw IllegalArgumentException("Invalid id $id") }
        val returnBody = repository.saveAndFlush(
            persistedTouristAttraction.copy(
                locationId = locationId ?: persistedTouristAttraction.locationId,
                cityId = cityId ?: persistedTouristAttraction.cityId,
                name = name ?: persistedTouristAttraction.name,
                description = description ?: persistedTouristAttraction.description,
            )
        )
        return if(shouldReturnBody) returnBody else null
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: Long,
    ) {
        repository.deleteById(id)
    }
}
