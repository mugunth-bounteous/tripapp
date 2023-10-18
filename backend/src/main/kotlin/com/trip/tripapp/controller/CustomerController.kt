package com.trip.tripapp.controller

import com.trip.tripapp.dto.*
import com.trip.tripapp.repository.AccountRepository
import com.trip.tripapp.repository.ReviewRepository
//import com.trip.tripapp.repository.ReviewRepository
import com.trip.tripapp.repository.TripRepository
import com.trip.tripapp.service.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/customer")
@CrossOrigin(origins = ["http://localhost:3000"])
class CustomerController(private val accountRepo:AccountRepository, private val tokenService: TokenService,private val tripRepo: TripRepository,private val reviewRepository: ReviewRepository) {
//
    @PostMapping("/login")
    fun login(@RequestBody req: LoginDto): ResponseEntity<Any?> {

        val ret: ResponseEntity<Any?> = LoginService(accountRepo, tokenService).loginService(req);
        return ret;
    }

    @PostMapping("/register")
    fun register(@RequestBody req: RegisterDto): ResponseEntity<Any?> {
        val ret: ResponseEntity<Any?> = RegisterService(accountRepo).registerService(req);
        return ret;
    }


    @PostMapping("/add_liked")
    fun updateLikedList(
        @RequestBody req: LikedReqDto,
        @RequestHeader(name = "Authorization") authorizationHeader: String
    ): ResponseEntity<Any?> {
        val ret = authorizationHeader.substring(7)
        val data = tokenService.parseToken(ret);
        val username = data!!.get().username!!;
        return LikedService(accountRepo, tripRepo).AddOrRemoveLiked(req, username);
    }

    @GetMapping("/fetch_liked_list")
    fun fetchLikedList(@RequestHeader(name = "Authorization") authorizationHeader: String): ResponseEntity<Any?> {
        val ret = authorizationHeader.substring(7)
        val data = tokenService.parseToken(ret);
        val username = data!!.get().username!!;
        return LikedService(accountRepo, tripRepo).FetchLikedList(username);
    }

    @GetMapping("/fetch_liked_data")
    fun fetchLikedData(@RequestHeader(name = "Authorization") authorizationHeader: String): ResponseEntity<Any?> {
        val ret = authorizationHeader.substring(7)
        val data = tokenService.parseToken(ret);
        val username = data!!.get().username!!;
        return LikedService(accountRepo, tripRepo).FetchLikedData(username);
    }

    @PostMapping("/add_review")
    fun addReview(
        @RequestBody req: AddReviewDto,
        @RequestHeader(name = "Authorization") authorizationHeader: String
    ): ResponseEntity<Any?> {
        val ret = authorizationHeader.substring(7)
        val data = tokenService.parseToken(ret);
        val username = data!!.get().username!!;
        return ReviewService(reviewRepository).AddReviewService(username,req);
    }

    @PostMapping("/fetch_review_of_user")
    fun fetchReview(
        @RequestBody req: LocationIdReqDto,
        @RequestHeader(name = "Authorization") authorizationHeader: String
    ): ResponseEntity<Any?> {
        val ret = authorizationHeader.substring(7)
        val data = tokenService.parseToken(ret);
        val username = data!!.get().username!!;
        return ReviewService(reviewRepository).FetchReviewServiceOfUser(username,req.locationId);
    }
    @PostMapping("/fetch_review_by_location")
    fun fetchReviewByLocationId(
        @RequestBody req: LocationIdReqDto,
        @RequestHeader(name = "Authorization") authorizationHeader: String
    ): ResponseEntity<Any?> {
        val ret = authorizationHeader.substring(7)
        val data = tokenService.parseToken(ret);
        val username = data!!.get().username!!;
        return ReviewService(reviewRepository).FetchReviewByLocationId(req.locationId);
    }

    @PostMapping("/change_password")
    fun changePassword(
        @RequestBody req: ChangePasswordDto,
        @RequestHeader(name = "Authorization") authorizationHeader: String
    ): ResponseEntity<Any?> {
        val ret = authorizationHeader.substring(7)
        val data = tokenService.parseToken(ret);
        val username = data!!.get().username!!;
        return ChangePasswordService().changePassword(req,username);
    }


}