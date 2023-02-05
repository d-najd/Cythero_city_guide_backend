package com.cythero.cityguide.locationsservice.web

import com.cythero.cityguide.locationsservice.model.Location
import com.cythero.cityguide.locationsservice.model.LocationHolder
import com.cythero.cityguide.locationsservice.model.LocationRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RequestMapping("/api")
@RestController
class UserResource(val repository: LocationRepository) {
    @GetMapping("/testing/getAll")
    fun getAll(): LocationHolder {
        return LocationHolder(repository.findAll())
    }

    @GetMapping("/{id}")
    fun getById(
        @PathVariable id: Long
    ): Location {
        return repository.findById(id).orElseThrow { throw IllegalArgumentException("Invalid id $id") }
    }

    @GetMapping("/longitude/{longitude}/latitude/{latitude}")
    fun getByLongitudeAndLatitude(
        @PathVariable longitude: BigDecimal,
        @PathVariable latitude: BigDecimal,
    ): Location {
        return repository.findByLongitudeAndLatitude(longitude, latitude).orElseThrow { throw java.lang.IllegalArgumentException("Invalid longitude $longitude and $latitude") }
    }

    @PostMapping
    fun post(
        @RequestBody pojo: Location,
    ): Location {
        repository.findById(pojo.id).ifPresent {
            throw IllegalArgumentException("field with id ${pojo.id} already exists")
        }
        return repository.save(pojo)
    }

    @PutMapping("/{id}")
    fun put(
        @PathVariable id: Long,
        @RequestParam("address") address: String? = null,
        @RequestParam("longitude") longitude: BigDecimal? = null,
        @RequestParam("latitude") latitude: BigDecimal? = null,
        @RequestParam("flagPath") flagPath: String? = null,
        @RequestParam("returnBody") shouldReturnBody: Boolean = true,
    ): Location? {
        val persistedLocation = repository.findById(id).orElseThrow { throw IllegalArgumentException("Invalid id $id") }
        val returnBody = repository.saveAndFlush(
            persistedLocation.copy(
                longitude = longitude ?: persistedLocation.longitude,
                latitude = latitude ?: persistedLocation.latitude,
                address = address ?: persistedLocation.address,
                flagPath = flagPath ?: persistedLocation.flagPath
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
