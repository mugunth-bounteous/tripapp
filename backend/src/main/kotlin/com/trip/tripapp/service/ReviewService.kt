package com.trip.tripapp.service

import com.trip.tripapp.dto.AddReviewDto
import com.trip.tripapp.dto.ResponseMessage
import com.trip.tripapp.model.ReviewDao
import com.trip.tripapp.repository.ReviewRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class ReviewService(val reviewRepository: ReviewRepository) {
    fun AddReviewService(username:String,req:AddReviewDto): ResponseEntity<Any?> {
        val response=reviewRepository.findByUsernameAndLocationId(username = username, locationId = req.locationId);
        if (response==null){
            val rd:ReviewDao = ReviewDao()
            rd.review=req.review
            rd.rating=req.rating
            rd.username=username
            rd.locationId=req.locationId
            reviewRepository.save(rd)
            return ResponseEntity<Any?>(ResponseMessage(status = "OK", message = "Review added!"), HttpStatus.valueOf(200))
        }
        return ResponseEntity<Any?>(ResponseMessage(status = "FAILED", message = "Review already exists for user!"), HttpStatus.valueOf(400))
    }

    fun FetchReviewServiceOfUser(username: String, locationId:Int):ResponseEntity<Any?>{
        val response=reviewRepository.findByUsernameAndLocationId(username = username, locationId = locationId);
        if(response!=null){
            return ResponseEntity<Any?>(ResponseMessage(status = "OK", message = response), HttpStatus.valueOf(200))
        }
        return ResponseEntity<Any?>(ResponseMessage(status = "FAILED", message = "Review not found!!"), HttpStatus.valueOf(400))
    }

    fun FetchReviewByLocationId(locationId: Int):ResponseEntity<Any?>{
        val response=reviewRepository.findByLocationId(locationId)
        if (response != null) {
            if(!response.isEmpty()){
                return ResponseEntity<Any?>(ResponseMessage(status = "OK", message = response), HttpStatus.valueOf(200))
            }
        }
        return ResponseEntity<Any?>(ResponseMessage(status = "FAILED", message = "Review not found!!"), HttpStatus.valueOf(400))
    }

}