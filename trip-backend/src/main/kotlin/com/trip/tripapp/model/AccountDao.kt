package com.trip.tripapp.model

import jakarta.persistence.*
import lombok.AllArgsConstructor
import kotlin.collections.ArrayList

@Entity
@AllArgsConstructor
class AccountDao()
{
    @Id
    var username:String?=null

    var password:String?=null

    var location:String?=null

    var firstName:String?=null

    var lastName:String?=null

    @ElementCollection
    val likedPlaces: MutableList<Int> = mutableListOf()

    fun appendToLiked(i:Int){
        likedPlaces.add(i)
    }
    fun removeFromLiked(i:Int){
        likedPlaces.remove(i)
}



}
