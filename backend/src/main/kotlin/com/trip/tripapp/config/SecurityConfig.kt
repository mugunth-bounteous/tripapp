package com.example.demo.config

import com.trip.tripapp.service.TokenService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
@EnableWebSecurity
class SecurityConfig (
    private val tokenService: TokenService,
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        // Define public and private routes
        http.authorizeHttpRequests{auth -> auth
            .requestMatchers(HttpMethod.POST, "/api/customer/login").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/customer/register").permitAll()
            .requestMatchers("/api/trip/**").authenticated()
            .requestMatchers("/api/customer/add_liked").authenticated()
            .requestMatchers("/api/customer/fetch_liked_list").authenticated()
            .requestMatchers("/api/customer/fetch_liked_data").authenticated()
            .requestMatchers("/api/customer/fetch_review_of_user").authenticated()
            .requestMatchers("/api/customer/fetch_review_by_location").authenticated()
            .requestMatchers("/api/customer/add_review").authenticated()
            .requestMatchers("/api/customer/change_password").authenticated()
            .anyRequest().permitAll()
        }




        // Configure JWT
        http.oauth2ResourceServer {  auth->auth.jwt{} }
        http.authenticationManager { auth ->
            val jwt = auth as BearerTokenAuthenticationToken
            val user = tokenService.parseToken(jwt.token) ?: throw InvalidBearerTokenException("Invalid token")
            UsernamePasswordAuthenticationToken(user, "", listOf(SimpleGrantedAuthority("USER")))
        }

//        // Other configuration
        http.cors {  }
        http.sessionManagement { v->v.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
        http.csrf { v->v.disable() }
        http.headers { v->v.frameOptions { k->k.disable() } }
        http.headers { v->v.xssProtection { k->k.disable() } }

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        // allow localhost for dev purposes
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("http://localhost:3000", "http://localhost:8080")
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE")
        configuration.allowedHeaders = listOf("authorization", "content-type")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}