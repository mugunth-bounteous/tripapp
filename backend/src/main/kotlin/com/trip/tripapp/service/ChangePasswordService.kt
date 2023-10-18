package com.trip.tripapp.service

import com.mugunth.bankapp.util.ExecuteQueryUtil
import com.trip.tripapp.dto.ChangePasswordDto
import com.trip.tripapp.dto.ResponseMessage
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.sql.ResultSet


class ChangePasswordService {
    var logger: Logger = LoggerFactory.getLogger(ChangePasswordService::class.java)

    fun changePassword(req:ChangePasswordDto,username:String): ResponseEntity<Any?> {
        val fetchOldPwQuery:String= "Select password from tripdb.account_dao where username=\"$username\"";
        val rs:ResultSet? = ExecuteQueryUtil.executeQuery(fetchOldPwQuery);
        val rsCopy=rs
        var oldPasswordInDb:String=""
        while (rsCopy!!.next()) {
            oldPasswordInDb=rs.getString("password")
        }

        if(req.oldPassword.equals(oldPasswordInDb)){
            val setNewPwQuery= "update tripdb.account_dao set password=\"${req.newPassword}\" where username=\"$username\"";
            val rs=ExecuteQueryUtil.updateQuery(setNewPwQuery)
            logger.info(rs.toString())
            if(!rs!!){
                return ResponseEntity<Any?>(ResponseMessage(status = "OK", message = "Successfully changed passwords"), HttpStatus.valueOf(200));
            }
            else{
                return ResponseEntity<Any?>(ResponseMessage(status = "FAILED", message = "Internal server error"), HttpStatus.valueOf(400));
            }

        }
        else{
            return ResponseEntity<Any?>(ResponseMessage(status = "FAILED", message = "Passwords are not matching"), HttpStatus.valueOf(400));
        }



    }
}