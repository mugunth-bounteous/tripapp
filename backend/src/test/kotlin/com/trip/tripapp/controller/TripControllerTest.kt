package com.trip.tripapp.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.trip.tripapp.dto.TripFetchReq
import com.trip.tripapp.repository.LocationRepository
import com.trip.tripapp.repository.TripRepository
import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.RequestEntity
import org.springframework.test.context.junit.jupiter.SpringExtension

import java.net.URI

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Transactional
class TripControllerTest {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var tripRepository: TripRepository

    @Autowired
    private lateinit var locationRepository: LocationRepository

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var entityManager: EntityManager

    private lateinit var baseUrl: String


    var token =
        "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0VXNlciIsImV4cCI6MTY5Nzg2MTYzNiwiaWF0IjoxNjk1MjY5NjM2LCJ1c2VybmFtZSI6InRlc3RVc2VyIn0.uEk9IRJWRKsLGkPQCFjxgPZy39QjT-nLugm6Ag7Q5IU"; //for token

    @BeforeEach
    fun setUp() {
        baseUrl = "http://localhost:$port/api/trip"
//        Runtime.getRuntime().exec("mysqldump -u root -p cosmic tripdb > backup.sql")
//        entityManager.transaction.begin();
    }

//    @AfterAll
//    fun removeAllChanges(){
//        entityManager.transaction.rollback()
//    }

    @AfterEach
    fun tearDown() {
//        entityManager.transaction.commit();
//        entityManager.transaction.rollback()
    }

    @Test
    @Transactional
    fun testTripFetch() {
        val tripFetchReq= TripFetchReq(2)

        val headers = HttpHeaders()
        headers.set("Authorization", "Bearer ${token}")
        headers.contentType = MediaType.APPLICATION_JSON

        val requestEntity = RequestEntity.post(URI.create("$baseUrl/fetch"))
            .headers(headers)
            .body(objectMapper.writeValueAsString(tripFetchReq))
        val responseEntity = restTemplate.exchange(requestEntity, String::class.java)
        assert(responseEntity.statusCode == HttpStatus.OK)
    }

    @Test
    @Transactional
    fun testLocationFetch() {
        val headers = HttpHeaders()
        headers.set("Authorization", "Bearer ${token}")
        val requestEntity = RequestEntity.get(URI.create("$baseUrl/fetch-all-location"))
            .headers(headers)
            .build()

        val responseEntity = restTemplate.exchange(requestEntity, String::class.java)

        assert(responseEntity.statusCode == HttpStatus.OK)
    }
}

