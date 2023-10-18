package com.trip.tripapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories("com.trip.tripapp.repository")
@SpringBootApplication(scanBasePackages = ["com.trip.tripapp"])
@ComponentScan("com")
class TripappApplication

fun main(args: Array<String>) {
	runApplication<TripappApplication>(*args)
}
