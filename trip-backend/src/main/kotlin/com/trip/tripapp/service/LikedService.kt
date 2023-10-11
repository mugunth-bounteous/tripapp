package com.trip.tripapp.service

import com.trip.tripapp.dto.LikedReqDto
import com.trip.tripapp.dto.ResponseMessage
import com.trip.tripapp.model.TripDetailsDao
import com.trip.tripapp.repository.AccountRepository
import com.trip.tripapp.repository.TripRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class LikedService(val accountRepo:AccountRepository,val tripRepo:TripRepository) {
    fun AddOrRemoveLiked(req:LikedReqDto, username:String):ResponseEntity<Any?>{
        val acc=accountRepo.findById(username);
        if(acc.isPresent()){
            val obj=acc.get();
            println(obj.likedPlaces.toString())
            if(req.isLiked){
                obj.appendToLiked(req.locationId)
                accountRepo.save(obj)
                return ResponseEntity<Any?>(ResponseMessage(status = "OK", message = "Appended to the list"), HttpStatus.valueOf(200))
            }
            else{
                obj.removeFromLiked(req.locationId)
                accountRepo.save(obj)
                return ResponseEntity<Any?>(ResponseMessage(status = "OK", message = "Removed From list"), HttpStatus.valueOf(200))
            }
        }
        else{
            return ResponseEntity<Any?>(ResponseMessage(status = "FAILED", message = "Username Not Found"), HttpStatus.valueOf(400))
        }

    }

    fun FetchLikedList(username: String):ResponseEntity<Any?>{
        val acc=accountRepo.findById(username);
        if(acc.isPresent()){
            return ResponseEntity<Any?>(ResponseMessage(status = "OK", message = acc.get().likedPlaces), HttpStatus.valueOf(200))
        }
        else {
            return ResponseEntity<Any?>(
                ResponseMessage(status = "FAILED", message = "Username Not Found"),
                HttpStatus.valueOf(400)
            )
        }
        }

    fun FetchLikedData(username: String):ResponseEntity<Any?>{
        val acc=accountRepo.findById(username);
        val toFetch=acc.get().likedPlaces;
        val arrayOfDetails:List<TripDetailsDao> = tripRepo.findAll();
        val ret=arrayOfDetails.filter { a->toFetch.contains(a.id)}
        if(acc.isPresent()){
            return ResponseEntity<Any?>(ResponseMessage(status = "OK", message = ret), HttpStatus.valueOf(200))
        }
        else {
            return ResponseEntity<Any?>(
                ResponseMessage(status = "FAILED", message = "Username Not Found"),
                HttpStatus.valueOf(400)
            )
        }
    }
}