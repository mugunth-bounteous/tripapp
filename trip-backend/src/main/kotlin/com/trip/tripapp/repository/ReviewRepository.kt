package com.trip.tripapp.repository

import com.trip.tripapp.model.AccountDao
import com.trip.tripapp.model.ReviewDao
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface ReviewRepository:JpaRepository<ReviewDao,Int> {


    @Query("from ReviewDao where locationId=?1")
    fun findByLocationId(locationId: Int?): List<ReviewDao?>?


    @Query("from ReviewDao where username=?1")
    fun findByUsername(username: String?): List<ReviewDao?>?


    @Query("from ReviewDao where username=?1 and locationId=?2")
    fun findByUsernameAndLocationId(username: String?,locationId:Int?): ReviewDao?
}