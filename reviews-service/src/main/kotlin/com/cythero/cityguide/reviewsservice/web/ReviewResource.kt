package com.cythero.cityguide.reviewsservice.web

import com.cythero.cityguide.reviewsservice.config.security.JwtUtils
import com.cythero.cityguide.reviewsservice.model.Review
import com.cythero.cityguide.reviewsservice.model.ReviewHolder
import com.cythero.cityguide.reviewsservice.model.ReviewRepository
import org.aspectj.weaver.TypeVariable
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RequestMapping("/api")
@RestController
class ReviewsResource(val repository: ReviewRepository) {
    @GetMapping("/testing/getAll")
    fun getAll(): ReviewHolder {
        return ReviewHolder(repository.findAll())
    }

    @GetMapping("/page/{page}/attraction/{attractionId}")
    fun getNextReviewsPageByAttraction(
        @PathVariable page: Int,
        @PathVariable attractionId: Long,
        @RequestParam("pageSize") pageSize: Int? = null,
        @RequestParam("sort") sort: Array<String>? = null,
        //@RequestParam("filters") filter: List<String> = listOf("id"),
    ): ReviewHolder {
        if (pageSize != null && pageSize > 100) {
            throw IllegalArgumentException("pageSize must not exceed 100, current is $pageSize")
        }
        return ReviewHolder(
            repository.findAllByAttractionId(
                pageable = PageRequest.of(
                    page,
                    pageSize ?: 10, Sort.DEFAULT_DIRECTION, *sort ?: arrayOf("id")
                ),
                attractionId = attractionId
            ).content
        )
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
        @AuthenticationPrincipal principal: Authentication,
    ): Review {
        repository.findById(pojo.id).ifPresent {
            throw IllegalArgumentException("field with id ${pojo.id} already exists")
        }
        val userId = JwtUtils.getUserId(principal.credentials)
        return repository.save(
            pojo.copy(
                userId = userId,
            )
        )
    }

    @PutMapping("/{id}")
    fun put(
        @AuthenticationPrincipal principal: Authentication,
        @PathVariable id: Long,
        @RequestParam("returnBody") shouldReturnBody: Boolean = true,
        @RequestParam("stars") stars: Int? = null,
        @RequestParam("title") title: String? = null,
        @RequestParam("description") description: String? = null,
    ): Review? {
        val userId = JwtUtils.getUserId(principal.credentials)
        val persistedReview =
            repository.findByIdAndUserId(id, userId).orElseThrow { throw IllegalArgumentException("Invalid id $id") }
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
        @AuthenticationPrincipal principal: Authentication,
        @PathVariable id: Long,
    ) {
        val userId = JwtUtils.getUserId(principal.credentials)
        repository.deleteByIdAndUserId(id, userId)
    }
}
