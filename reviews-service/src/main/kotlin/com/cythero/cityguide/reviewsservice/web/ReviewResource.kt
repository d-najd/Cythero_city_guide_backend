package com.cythero.cityguide.reviewsservice.web

import com.cythero.cityguide.reviewsservice.model.Review
import com.cythero.cityguide.reviewsservice.model.ReviewHolder
import com.cythero.cityguide.reviewsservice.model.ReviewRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RequestMapping("/api")
@RestController
class ReviewsResource(val repository: ReviewRepository) {
    @GetMapping("/testing/getAll")
    fun getAll(): ReviewHolder {
        return ReviewHolder(repository.findAll())
    }

    @GetMapping("/{id}")
    fun getById(
        @PathVariable id: Long
    ): Review {
        return repository.findById(id).orElseThrow { throw IllegalArgumentException("Invalid id $id") }
    }

    @PostMapping
    fun post(
        @RequestBody pojo: Review,
    ): Review {
        repository.findById(pojo.id).ifPresent {
            throw IllegalArgumentException("field with id ${pojo.id} already exists")
        }
        return repository.save(pojo)
    }

    @PutMapping("/{id}")
    fun put(
        @PathVariable id: Long,
        @RequestParam("returnBody") shouldReturnBody: Boolean = true,
        @RequestParam("stars") stars: Int? = null,
        @RequestParam("title") title: String? = null,
        @RequestParam("description") description: String? = null,
    ): Review? {
        val persistedReview = repository.findById(id).orElseThrow { throw IllegalArgumentException("Invalid id $id") }
        val returnBody = repository.saveAndFlush(
            persistedReview.copy(
                stars = stars ?: persistedReview.stars,
                title = title ?: persistedReview.title,
                description = description ?: persistedReview.description,
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
