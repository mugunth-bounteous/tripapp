package com.trip.tripapp.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.trip.tripapp.dto.LikedReqDto
import com.trip.tripapp.dto.LoginDto
import com.trip.tripapp.dto.RegisterDto
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
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.junit.jupiter.SpringExtension

import java.net.URI

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@org.springframework.transaction.annotation.Transactional
class CustomerControllerTest {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var entityManager: EntityManager

    private lateinit var baseUrl: String


    var token="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0VXNlciIsImV4cCI6MTY5Nzg2MTYzNiwiaWF0IjoxNjk1MjY5NjM2LCJ1c2VybmFtZSI6InRlc3RVc2VyIn0.uEk9IRJWRKsLGkPQCFjxgPZy39QjT-nLugm6Ag7Q5IU"; //for token

    @BeforeEach
    fun setUp() {
        baseUrl = "http://localhost:$port/api/customer"
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
    fun testLogin() {
        val loginReq = LoginDto(username = "testUser", password = "testpassword")

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val requestEntity = RequestEntity.post(URI.create("$baseUrl/login"))
            .headers(headers)
            .body(objectMapper.writeValueAsString(loginReq))

        val responseEntity = restTemplate.exchange(requestEntity, String::class.java)


        assert(responseEntity.statusCode == HttpStatus.valueOf(200))
    }

    @Test
    @Transactional
    fun testRegister() {

        val registerReq = RegisterDto(username = "newuser", password = "newpassword", firstname = "testFirstName", lastname = "testLastName")

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val requestEntity = RequestEntity.post(URI.create("$baseUrl/register"))
            .headers(headers)
            .body(objectMapper.writeValueAsString(registerReq))

        val responseEntity = restTemplate.exchange(requestEntity, String::class.java)

        assert(responseEntity.statusCode == HttpStatus.valueOf(200))
    }

    @Test
    @Transactional
    @Rollback
    fun testUpdateLikedList() {
        val likedReq = LikedReqDto(isLiked = true, locationId = 33)

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.set("Authorization", "Bearer ${token}")

        val requestEntity = RequestEntity.post(URI.create("$baseUrl/add_liked"))
            .headers(headers)
            .body(objectMapper.writeValueAsString(likedReq))

        val responseEntity = restTemplate.exchange(requestEntity, String::class.java)


        assert(responseEntity.statusCode == HttpStatus.valueOf(200))
    }

    @Test
    @Transactional
    @Rollback
    fun testFetchLikedList() {
        val headers = HttpHeaders()
        headers.set("Authorization", "Bearer ${token}")

        val requestEntity = RequestEntity.get(URI.create("$baseUrl/fetch_liked_list"))
            .headers(headers)
            .build()

        val responseEntity = restTemplate.exchange(requestEntity, String::class.java)


        assert(responseEntity.statusCode == HttpStatus.valueOf(200))

    }

    @Test
    @Transactional
    fun testFetchLikedData() {
        val headers = HttpHeaders()
        headers.set("Authorization", "Bearer ${token}")

        val requestEntity = RequestEntity.get(URI.create("$baseUrl/fetch_liked_data"))
            .headers(headers)
            .build()

        val responseEntity = restTemplate.exchange(requestEntity, String::class.java)

        assert(responseEntity.statusCode == HttpStatus.valueOf(200))

    }

}
