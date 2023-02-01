package com.cythero.cityguide.usersservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@EnableDiscoveryClient
@SpringBootApplication
class UsersServiceApplication

fun main(args: Array<String>) {
	runApplication<UsersServiceApplication>(*args)
}
