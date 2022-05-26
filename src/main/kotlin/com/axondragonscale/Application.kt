package com.axondragonscale

import com.axondragonscale.plugins.configureMonitoring
import com.axondragonscale.plugins.configureRouting
import com.axondragonscale.plugins.configureSecurity
import com.axondragonscale.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>) = EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    configureMonitoring()
    configureRouting()
    configureSecurity()
    configureSerialization()
}

