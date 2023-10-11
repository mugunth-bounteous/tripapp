package com.trip.tripapp.repository

import com.trip.tripapp.model.AccountDao
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
public interface AccountRepository:JpaRepository<AccountDao,String> {

    @Transactional
    @Modifying
    @Query("update AccountDao a set a.location= :location where a.username= :username")
    fun setLocation(username: String?,location: String?)

    @Transactional
    @Modifying
    @Query("from AccountDao where username=?1")
    fun findByUsername(username: String?): AccountDao?




//    @Transactional
//    @Modifying
//    @Query("from AccountDao where username=?1")
//    fun findById(username: String?):AccountDao?

}