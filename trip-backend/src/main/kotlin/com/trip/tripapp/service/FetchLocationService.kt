package com.trip.tripapp.service

import com.trip.tripapp.dto.LocationDto
import com.trip.tripapp.model.LocationDetailsDao
import com.trip.tripapp.repository.LocationRepository


class FetchLocationService(val locationRepo:LocationRepository){
    fun fetchLocation(req:Int): LocationDto? {
    val response=locationRepo.fetchLocationById(req)
        return response;
    }

    fun fetchAllLocation(): List<LocationDetailsDao?>? {
        val response=locationRepo.findAll()
        return response.toList();
    }
}