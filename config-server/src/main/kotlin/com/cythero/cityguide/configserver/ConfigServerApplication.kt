package com.cythero.cityguide.configserver

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.config.server.EnableConfigServer
import org.springframework.context.annotation.Configuration

@Configuration
@EnableAutoConfiguration
@EnableDiscoveryClient
@EnableConfigServer
@SpringBootApplication
class ConfigServerApplication

fun main(args: Array<String>) {
	runApplication<ConfigServerApplication>(*args)
}
