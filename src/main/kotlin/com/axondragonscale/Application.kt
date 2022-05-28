package com.axondragonscale

import com.axondragonscale.plugins.configureMonitoring
import com.axondragonscale.plugins.configureRouting
import com.axondragonscale.plugins.configureSecurity
import com.axondragonscale.plugins.configureSerialization
import com.axondragonscale.repository.MongoUserRepository
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

    configureMonitoring()
    configureRouting()
    configureSecurity()
    configureSerialization()
}

