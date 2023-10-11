package com.trip.tripapp.service


import com.trip.tripapp.dto.LoginDto
import com.trip.tripapp.dto.ResponseMessage
import com.trip.tripapp.model.AccountDao
import com.trip.tripapp.dto.LoginResponseDto
import com.trip.tripapp.repository.AccountRepository
import io.mockk.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.util.Optional

class LoginServiceTest {

    private lateinit var accountRepository: AccountRepository
    private lateinit var loginService: LoginService
    private lateinit var tokenService: TokenService

    @BeforeEach
    fun setUp() {
        accountRepository = mockk<AccountRepository>()
        tokenService= mockk<TokenService>()
        loginService = LoginService(accountRepository,tokenService)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test successful login`() {
        val username = "testUser"
        val password = "testPassword"
        val loginDto = LoginDto(username, password)
        val account= AccountDao()
        val token=tokenService.createToken(account)

        with(account){
            this.password = password
            this.username = username
            location = ""
            firstName = ""
            lastName=""
        }
    val expectedResponse = ResponseEntity(
            ResponseMessage(status = "OK", message =  LoginResponseDto(token =token ,data= account)),
            HttpStatus.OK
        )
        every { accountRepository.findById(username)} returns Optional.of(account)
        val actualResponse = loginService.loginService(loginDto)
        verify { accountRepository.findById(username) }
        confirmVerified(accountRepository)
    assertEquals(expectedResponse , actualResponse)
    }

    @Test
    fun `test wrong password login`() {
        val username = "testUser"
        val password = "testPassword"
        val wrongPassword = "wrongPassword"
        val loginDto = LoginDto(username, wrongPassword)
        val account = AccountDao()
        with(account){
            this.password = password
            this.username = username
            location = ""
            firstName = ""
            lastName=""
        }
        every { accountRepository.findById(username)} returns Optional.of(account)

        val expectedResponse = ResponseEntity(
            ResponseMessage(status = "FAILED", message = "Password is wrong!"),
            HttpStatus.BAD_REQUEST
        )

        val actualResponse = loginService.loginService(loginDto)

        verify { accountRepository.findById(username) }
        confirmVerified(accountRepository)
        assertEquals(expectedResponse , actualResponse)
    }

    @Test
    fun `test username not found`() {
        val username = "nonExistentUser"
        val password = "testPassword"
        val loginDto = LoginDto(username, password)
        every { accountRepository.findById(username) } returns Optional.empty()

        val expectedResponse = ResponseEntity(
            ResponseMessage(status = "FAILED", message = "Username not found!"),
            HttpStatus.BAD_REQUEST
        )
        val actualResponse = loginService.loginService(loginDto)

        verify { accountRepository.findById(username) }

        confirmVerified(accountRepository)
        assertEquals(expectedResponse, actualResponse)
    }
}
