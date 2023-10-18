package com.trip.tripapp.service

import com.trip.tripapp.dto.ResponseMessage
import com.trip.tripapp.model.TripDetailsDao
import com.trip.tripapp.dto.TripFetchReq
import com.trip.tripapp.repository.LocationRepository
import com.trip.tripapp.repository.TripRepository
import io.mockk.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class TripServiceTest {

    private lateinit var tripRepository: TripRepository
    private lateinit var tripService: TripService
    private lateinit var locationRepository: LocationRepository

    @BeforeEach
    fun setUp() {
        tripRepository = mockk<TripRepository>()
        tripService = TripService(tripRepository,locationRepository)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `fetch a valid location`(){
        val location=1
        val mockReq=TripFetchReq(location)
        val detail= TripDetailsDao()
        with(detail){
            this.locationId=location
            this.id=1
            this.name=""
            this.details=""
            this.imgUrl=""

        }
        val tripDetailsDaoList=List<TripDetailsDao>(1){detail}

        every { tripRepository.fetchTripBylocation(location) } returns tripDetailsDaoList

        val expectedResponse = ResponseEntity(
            ResponseMessage(status = "OK", message = tripDetailsDaoList),
            HttpStatus.OK

        )

        val actualResponse=tripService.fetchTripService(mockReq)
        verify { tripRepository.fetchTripBylocation(location) }

        confirmVerified(tripRepository)
        Assertions.assertEquals(expectedResponse, actualResponse)

    }
}