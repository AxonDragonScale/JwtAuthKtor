package com.axondragonscale

import com.axondragonscale.plugins.configureMonitoring
import com.axondragonscale.plugins.configureRouting
import com.axondragonscale.plugins.configureSecurity
import com.axondragonscale.plugins.configureSerialization
import com.axondragonscale.repository.MongoUserRepository
import com.axondragonscale.security.hash.SHA256HashService
import com.axondragonscale.security.token.JwtTokenService
import com.axondragonscale.security.token.TokenConfig
import io.ktor.server.application.*
import io.ktor.server.netty.EngineMain
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

fun main(args: Array<String>) = EngineMain.main(args)

@Suppress("unused")
fun Application.module() {

    val dbName = "JwtAuth"
    val dbPassword = System.getenv("MONGO_PASSWORD")
    val db = KMongo
        .createClient("mongodb+srv://Axon:$dbPassword@axoncluster.grdtj.mongodb.net/?retryWrites=true&w=majority")
        .coroutine
        .getDatabase(dbName)

    val userRepository = MongoUserRepository(db)

    val tokenService = JwtTokenService()
    val tokenConfig = TokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        expiresIn = 365L * 24L * 60L * 60L * 1000L,
        secret = System.getenv("TOKEN_SECRET")
    )

    val hashService = SHA256HashService()

    configureMonitoring()
    configureRouting(hashService, tokenConfig, tokenService, userRepository)
    configureSecurity(tokenConfig)
    configureSerialization()
}

