package com.cythero.cityguide.countriesservice.web

import com.cythero.cityguide.countriesservice.model.Country
import com.cythero.cityguide.countriesservice.model.CountryHolder
import com.cythero.cityguide.countriesservice.model.CountryRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RequestMapping("/api")
@RestController
class CountryResource(val repository: CountryRepository) {
    @GetMapping("/testing/getAll")
    fun getAll(): CountryHolder {
        return CountryHolder(repository.findAll())
    }

    @GetMapping("/{id}")
    fun getById(
        @PathVariable id: Long
    ): Country {
        return repository.findById(id).orElseThrow { throw IllegalArgumentException("Invalid id $id") }
    }

    @PostMapping
    fun post(
        @RequestBody pojo: Country,
    ): Country {
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
    ): Country? {
        val persistedCountry = repository.findById(id).orElseThrow { throw IllegalArgumentException("Invalid id $id") }
        val returnBody = repository.saveAndFlush(
            persistedCountry.copy(
                name = name ?: persistedCountry.name,
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
