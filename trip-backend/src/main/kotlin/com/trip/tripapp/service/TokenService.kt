package com.trip.tripapp.service

import com.trip.tripapp.model.AccountDao
import com.trip.tripapp.repository.AccountRepository
import org.springframework.security.oauth2.jwt.JwsHeader
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Service
import java.lang.Exception
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Optional

@Service
class TokenService(
    private val jwtDecoder: JwtDecoder,
    private val jwtEncoder: JwtEncoder,
    private val accountRepo: AccountRepository
) {
    fun createToken(user: AccountDao): String {
        val jwsHeader = JwsHeader.with { "HS256" }.build()
        val claims = JwtClaimsSet.builder()
            .issuedAt(Instant.now())
            .expiresAt(Instant.now().plus(30L, ChronoUnit.DAYS))
            .subject(user.username)
            .claim("username", user.username)
            .build()
        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).tokenValue
    }


    fun parseToken(token: String): Optional<AccountDao?>? {
        return try {
            val jwt = jwtDecoder.decode(token)
            val username = jwt.claims["username"] as String
            val fetchAccountRepoService= FetchAccountRepoService(accountRepo)
            fetchAccountRepoService.isUsernamePresent(username)
        } catch (e: Exception) {
            null
        }
    }
}