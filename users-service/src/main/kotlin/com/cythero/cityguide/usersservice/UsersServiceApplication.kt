package com.cythero.cityguide.usersservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@EnableDiscoveryClient
@SpringBootApplication
class UsersServiceApplication

fun main(args: Array<String>) {
	runApplication<UsersServiceApplication>(*args)
}