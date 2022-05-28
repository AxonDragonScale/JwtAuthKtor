package com.axondragonscale.plugins

import com.axondragonscale.repository.UserRepository
import com.axondragonscale.route.authenticate
import com.axondragonscale.route.getUserInfo
import com.axondragonscale.route.signIn
import com.axondragonscale.route.signUp
import com.axondragonscale.security.hash.HashService
import com.axondragonscale.security.token.TokenConfig
import com.axondragonscale.security.token.TokenService
import io.ktor.server.application.*
import io.ktor.server.routing.*

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
        getUserInfo(userRepository)
    }
}
