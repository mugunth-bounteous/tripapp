package com.trip.tripapp.dto

data class TripResponseDto(var id:Int,
                           var locationId:Int,
                           var name:String,
                           var details:String,
                           var imgUrl:String,
                           val opensAt:String,
                           val closesAt:String,
                           val approxPrice:Double,
                           val suitableFor:String,
                           val location: String)
