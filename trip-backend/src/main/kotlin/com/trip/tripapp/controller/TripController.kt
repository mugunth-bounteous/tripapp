package com.trip.tripapp.controller


import com.trip.tripapp.dto.TripFetchReq
import com.trip.tripapp.repository.LocationRepository
import com.trip.tripapp.repository.TripRepository
import com.trip.tripapp.service.LocationService
import com.trip.tripapp.service.TripService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/trip")
@CrossOrigin(origins = ["http://localhost:3000"])
class TripController {
    @Autowired
    lateinit var tripRepo: TripRepository

    @Autowired
    lateinit var locationRepo:LocationRepository



    @PostMapping("/fetch")
    fun tripFetch(@RequestBody req: TripFetchReq): ResponseEntity<Any?> {
        val ret: ResponseEntity<Any?> = TripService(tripRepo,locationRepo).fetchTripService(req);
        return ret;
    }

    @GetMapping("/fetch-all-location")
    fun locationFetch():ResponseEntity<Any?>{
        val ret:ResponseEntity<Any?> = LocationService(locationRepo).fetchAllLocation();
        return ret;
    }




}