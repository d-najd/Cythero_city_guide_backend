package com.cythero.cityguide.citiesservice.web

import com.cythero.cityguide.citiesservice.model.City
import com.cythero.cityguide.citiesservice.model.CityHolder
import com.cythero.cityguide.citiesservice.model.CityRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RequestMapping("/api")
@RestController
class CityResource(val repository: CityRepository) {
    @GetMapping("/testing/getAll")
    fun getAll(): CityHolder {
        return CityHolder(repository.findAll())
    }

    @GetMapping("/{id}")
    fun getById(
        @PathVariable id: Long
    ): City {
        return repository.findById(id).orElseThrow { throw IllegalArgumentException("Invalid id $id") }
    }

    @PostMapping
    fun post(
        @RequestBody pojo: City,
    ): City {
        repository.findById(pojo.id).ifPresent {
            throw IllegalArgumentException("field with id ${pojo.id} already exists")
        }
        return repository.save(pojo)
    }

    @PutMapping("/{id}")
    fun put(
        @PathVariable id: Long,
        @RequestParam("name") name: String? = null,
        @RequestParam("returnBody") shouldReturnBody: Boolean = true,
    ): City? {
        val persistedCity = repository.findById(id).orElseThrow { throw IllegalArgumentException("Invalid id $id") }
        val returnBody = repository.saveAndFlush(
            persistedCity.copy(
                name = name ?: persistedCity.name,
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
