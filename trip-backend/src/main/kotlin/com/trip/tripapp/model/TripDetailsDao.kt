package com.trip.tripapp.model
import jakarta.persistence.Entity
import jakarta.persistence.ForeignKey
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne

@Entity
class TripDetailsDao(){

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int?=null
    var locationId:Int?=null
    var name:String?=null
    var details:String?=null
    var imgUrl:String?=null
    val opensAt:String?=null
    val closesAt:String?=null
    val approxPrice:Double?=null
    val suitableFor:String?=null
}
