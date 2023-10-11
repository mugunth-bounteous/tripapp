package com.trip.tripapp.repository

import com.trip.tripapp.dto.LocationDto
import com.trip.tripapp.model.LocationDetailsDao
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface LocationRepository: JpaRepository<LocationDetailsDao, Int> {
    @Query("from LocationDetailsDao where id=?1")
    fun fetchLocationById(id: Int?): LocationDto?
}