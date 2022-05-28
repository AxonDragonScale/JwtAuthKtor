package com.axondragonscale.route

import com.axondragonscale.data.model.User
import com.axondragonscale.data.model.request.AuthRequest
import com.axondragonscale.data.model.response.AuthResponse
import com.axondragonscale.repository.UserRepository
import com.axondragonscale.security.hash.HashService
import com.axondragonscale.security.hash.SaltedHash
import com.axondragonscale.security.token.TokenClaim
import com.axondragonscale.security.token.TokenConfig
import com.axondragonscale.security.token.TokenService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlin.run

/**
 * Created by Ronak Harkhani on 28/05/22
 */

fun Route.signUp(
    hashService: HashService,
    userRepository: UserRepository
) {
    post("signUp") {
        val request = call.receiveOrNull<AuthRequest>() ?: run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val isUsernameInvalid = request.username.isNullOrBlank()
        val isPasswordInvalid = request.password.isNullOrBlank() || request.password.length < 8
        if (isUsernameInvalid || isPasswordInvalid) {
            call.respond(HttpStatusCode.ExpectationFailed)
            return@post
        }

        val saltedHash = hashService.generateSaltedHash(request.password)
        val user = User(request.username, saltedHash.hash, saltedHash.salt)
        val wasInserted = userRepository.insertUser(user)
        if (!wasInserted) {
            call.respond(HttpStatusCode.InternalServerError)
            return@post
        }

        call.respond(HttpStatusCode.OK)
    }
}

fun Route.signIn(
    hashService: HashService,
    tokenConfig: TokenConfig,
    tokenService: TokenService,
    userRepository: UserRepository
) {
    post("signIn") {
        val request = call.receiveOrNull<AuthRequest>() ?: run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val user = userRepository.getUserByUsername(request.username)
        if (user == null) {
            call.respond(
                HttpStatusCode.Unauthorized,
                "User ${request.username} doesn't exist"
            )
            return@post
        }

        val isPasswordVerified = hashService.verify(
            request.password,
            SaltedHash(user.password, user.salt)
        )
        if (!isPasswordVerified) {
            call.respond(HttpStatusCode.Unauthorized, "Incorrect Password")
            return@post
        }

        val token = tokenService.generateToken(
            config = tokenConfig,
            TokenClaim("userId", user.id.toString())
        )
        call.respond(HttpStatusCode.OK, AuthResponse(token))
    }
}

fun Route.authenticate() {
    authenticate {
        get("authenticate") {
            call.respond(HttpStatusCode.OK)
        }
    }
}

fun Route.getUserId() {
    authenticate {
        get("userId") {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", String::class)

            if (userId.isNullOrBlank()) {
                call.respond(HttpStatusCode.InternalServerError)
            } else {
                call.respond(HttpStatusCode.OK, userId)
            }
        }
    }
}