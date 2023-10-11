package com.trip.tripapp.repository

import com.trip.tripapp.model.TripDetailsDao
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TripRepository: JpaRepository<TripDetailsDao, Int> {
    @Query("from TripDetailsDao where locationId=?1")
    fun fetchTripBylocation(loc: Int?): List<TripDetailsDao?>?
}