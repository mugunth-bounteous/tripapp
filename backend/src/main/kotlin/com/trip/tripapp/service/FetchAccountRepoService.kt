package com.trip.tripapp.service

import com.trip.tripapp.dto.*
import com.trip.tripapp.model.AccountDao
import com.trip.tripapp.repository.AccountRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*
import org.slf4j.Logger


@Component
class FetchAccountRepoService(private val accountRepo: AccountRepository){

    var logger: Logger = LoggerFactory.getLogger(FetchAccountRepoService::class.java)


        fun fetchAccount(req:LoginDto): Optional<AccountDao?> {
        val account = accountRepo.findById(req.username)
//        println(account)
            logger.info(account.toString());
//        println(account.get().username)
            logger.info(account.get().username)
//        accountRepo.findAll().forEach { k -> println(k.username) }
        return account;
    }
    fun isAccountPresent(req:RegisterDto): Optional<AccountDao?> {
        val account=accountRepo.findById(req.username)
        return account;
    }

    fun isUsernamePresent(username:String): Optional<AccountDao?> {
        val account=accountRepo.findById(username)
        return account;
    }

    fun getRandomLocation(): Location {
        val colors = Location.values()
        val randomIndex = (0 until colors.size).random()
        return colors[randomIndex]
    }

    fun createAccount(req: RegisterDto){
        val account: AccountDao = AccountDao()
        account.firstName=req.firstname
        account.lastName=req.lastname
        account.username=req.username
        account.password=req.password
        account.location=getRandomLocation().toString()
        val k= accountRepo!!.save(account)
    }

    fun updateUserlocation(req:UpdateUserLocationDto){
        val ret=accountRepo.setLocation(username = req.username, location = req.location.toString())
        return ret
    }
}