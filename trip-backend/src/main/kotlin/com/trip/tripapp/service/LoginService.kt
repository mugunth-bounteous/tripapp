package com.trip.tripapp.service

import com.trip.tripapp.dto.LoginDto
import com.trip.tripapp.dto.LoginResponseDto
import com.trip.tripapp.dto.ResponseMessage
import com.trip.tripapp.repository.AccountRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
@Service
class LoginService(private val accountRepo: AccountRepository,private val tokenService: TokenService) {


    fun loginService(req:LoginDto): ResponseEntity<Any?> {
        val response= FetchAccountRepoService(accountRepo).fetchAccount(req)
//        println("response.isPresent() : ${response.isPresent()}")
//        println(response.get().password)
        if(response.isPresent()){
//            println("req.password : ${req.password}")
//            println("response.password : ${response.get().password}")
            if(req.password.equals(response.get().password)){
                val retemp=LoginResponseDto(token = tokenService.createToken(response.get()),data= response.get())
              return ResponseEntity<Any?>(ResponseMessage(status = "OK", message = retemp), HttpStatus.valueOf(200))
            }
            else{
                return ResponseEntity<Any?>(ResponseMessage(status = "FAILED", message = "Password is wrong!"), HttpStatus.valueOf(400))
            }
        }
        return ResponseEntity<Any?>(ResponseMessage(status = "FAILED", message = "Username not found!"), HttpStatus.valueOf(400))
    }
}
