package com.axondragonscale.plugins

import com.axondragonscale.repository.UserRepository
import com.axondragonscale.route.authenticate
import com.axondragonscale.route.getUserId
import com.axondragonscale.route.signIn
import com.axondragonscale.route.signUp
import com.axondragonscale.security.hash.HashService
import com.axondragonscale.security.token.TokenConfig
import com.axondragonscale.security.token.TokenService
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*

fun Application.configureRouting(
    hashService: HashService,
    tokenConfig: TokenConfig,
    tokenService: TokenService,
    userRepository: UserRepository
) {
    routing {
        signUp(hashService, userRepository)
        signIn(hashService, tokenConfig, tokenService, userRepository)
        authenticate()
        getUserId()
    }
}
