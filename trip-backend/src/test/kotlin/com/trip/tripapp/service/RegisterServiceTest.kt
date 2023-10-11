package com.trip.tripapp.service

import com.trip.tripapp.model.AccountDao
import com.trip.tripapp.dto.RegisterDto
import com.trip.tripapp.dto.ResponseMessage
import com.trip.tripapp.repository.AccountRepository
import io.mockk.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.util.*

class RegisterServiceTest {
    private lateinit var accountRepository: AccountRepository
    private lateinit var registerService: RegisterService

    @BeforeEach
    fun Setup(){
        accountRepository = mockk<AccountRepository>()
        registerService = RegisterService(accountRepository)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test successful register`(){
        val username="NonExistentUsername"
        val password="TestPassword"
        val firstName="TestFirstName"
        val lastName="TestLastName"
        val registerDto = RegisterDto(username, password,firstName,lastName)
        every { accountRepository.findById(username) } returns Optional.empty()
        every { accountRepository.save(ofType(AccountDao::class)) } answers { firstArg() }
        val expectedResponse = ResponseEntity(
            ResponseMessage(status = "OK", message = "Successfully created account"),
            HttpStatus.OK
        )
        val actualResponse = registerService.registerService(registerDto)

        verify { accountRepository.findById(username) }
        verify { accountRepository.save(ofType(AccountDao::class)) }
        confirmVerified(accountRepository)
        Assertions.assertEquals(expectedResponse, actualResponse)
    }

    @Test
    fun `register test ,username already exisits`(){
        val username = "testUser"
        val password = "testPassword"
        val registerDto = RegisterDto(username, password, "", "")
        val existingAccount = AccountDao()
        existingAccount.username=username;
        existingAccount.password=password;
        existingAccount.firstName="";
        existingAccount.lastName="";
        existingAccount.location="";
        every { accountRepository.findById(username) } returns Optional.of(existingAccount)
        val expectedResponse = ResponseEntity(
            ResponseMessage(status = "FAILED", message = "Username already exists!!"),
            HttpStatus.BAD_REQUEST
        )

        val actualResponse = registerService.registerService(registerDto)

        verify { accountRepository.findById(username) }
        confirmVerified(accountRepository)

        assertEquals(expectedResponse, actualResponse)
    }
}