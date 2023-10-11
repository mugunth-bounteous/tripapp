package com.trip.tripapp.service

import com.trip.tripapp.dto.RegisterDto
import com.trip.tripapp.dto.ResponseMessage
import com.trip.tripapp.repository.AccountRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class RegisterService(private val accountRepo: AccountRepository) {
    fun registerService(req:RegisterDto):ResponseEntity<Any?>{
        val response= FetchAccountRepoService(accountRepo).isAccountPresent(req);
        if(response.isPresent()){
            return ResponseEntity<Any?>(ResponseMessage(status = "FAILED", message = "Username already exists!!"), HttpStatus.valueOf(400))
        }
        else{
            FetchAccountRepoService(accountRepo).createAccount(req)
            return ResponseEntity<Any?>(ResponseMessage(status = "OK", message = "Successfully created account"), HttpStatus.valueOf(200))
        }
    }
}