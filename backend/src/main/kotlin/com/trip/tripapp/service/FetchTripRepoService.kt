package com.trip.tripapp.service

import com.trip.tripapp.model.TripDetailsDao
import com.trip.tripapp.dto.TripFetchReq
import com.trip.tripapp.repository.LocationRepository
import com.trip.tripapp.repository.TripRepository

class FetchTripRepoService(private val tripRepo: TripRepository,private val locationRepo: LocationRepository) {

    fun fetchTripDetails(req: TripFetchReq): List<TripDetailsDao?>? {
        val response=tripRepo.fetchTripBylocation(req.location)
        return response
    }
}