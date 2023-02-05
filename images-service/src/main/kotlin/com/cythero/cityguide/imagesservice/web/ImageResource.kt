package com.cythero.cityguide.imagesservice.web

import com.cythero.cityguide.imagesservice.model.Image
import com.cythero.cityguide.imagesservice.model.ImageHolder
import com.cythero.cityguide.imagesservice.model.ImageRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RequestMapping("/api")
@RestController
class ImageResource(val repository: ImageRepository) {
    @GetMapping("/testing/getAll")
    fun getAll(): ImageHolder {
        return ImageHolder(repository.findAll())
    }

    @GetMapping("/{id}")
    fun getById(
        @PathVariable id: Long
    ): Image {
        return repository.findById(id).orElseThrow { throw IllegalArgumentException("Invalid id $id") }
    }

    @PostMapping
    fun post(
        @RequestBody pojo: Image,
    ): Image {
        repository.findById(pojo.id).ifPresent {
            throw IllegalArgumentException("field with id ${pojo.id} already exists")
        }
        return repository.save(pojo)
    }

    @PutMapping("/{id}")
    fun put(
        @PathVariable id: Long,
        @RequestParam("returnBody") shouldReturnBody: Boolean = true,
        @RequestParam("path") path: String? = null,
    ): Image? {
        val persistedImage = repository.findById(id).orElseThrow { throw IllegalArgumentException("Invalid id $id") }
        val returnBody = repository.saveAndFlush(
            persistedImage.copy(
                path = path ?: persistedImage.path,
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
