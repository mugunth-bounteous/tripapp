package com.trip.tripapp.service

import com.trip.tripapp.dto.ResponseMessage
import com.trip.tripapp.dto.TripFetchReq
import com.trip.tripapp.repository.LocationRepository
import com.trip.tripapp.repository.TripRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class TripService(private val tripRepo: TripRepository,private val locationRepo:LocationRepository) {

    fun fetchTripService(req:TripFetchReq): ResponseEntity<Any?> {
        val response= FetchTripRepoService(tripRepo,locationRepo).fetchTripDetails(req)
        if(response.isNullOrEmpty()){
            return ResponseEntity<Any?>(ResponseMessage(status = "FAILED", message = "Location not found!!"), HttpStatus.valueOf(400))
        }
//        val ret=response.map { q->TripResponseDao(id=q.id, locationId = q.locationId, name = q.name, details = q.details, imgUrl = q.imgUrl, opensAt = q.) }
        return ResponseEntity<Any?>(ResponseMessage(status = "OK", message = response), HttpStatus.valueOf(200))
    }
}