package com.trip.tripapp.service

import com.trip.tripapp.dto.ResponseMessage
import com.trip.tripapp.repository.LocationRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class LocationService(val locationRepo:LocationRepository) {
    fun fetchAllLocation():ResponseEntity<Any?>{
        val response=FetchLocationService(locationRepo).fetchAllLocation()
        if(response.isNullOrEmpty()){
            return ResponseEntity<Any?>(ResponseMessage(status = "FAILED", message = "No Location found!!"), HttpStatus.valueOf(400))
        }
        else{
            return ResponseEntity<Any?>(ResponseMessage(status = "OK", message = response) , HttpStatus.valueOf(200));
        }

    }
}