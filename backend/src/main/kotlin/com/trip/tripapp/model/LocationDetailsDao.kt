package com.trip.tripapp.model

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class LocationDetailsDao() {
    @Id
    val id: Int? =null

    val location: String?=null
}