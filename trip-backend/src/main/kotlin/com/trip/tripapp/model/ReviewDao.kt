package com.trip.tripapp.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id


@Entity
class ReviewDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int?=null;
    var locationId:Int?=null;
    var username:String?=null;
    var review:String?=null;
    var rating:Int?=null;
}