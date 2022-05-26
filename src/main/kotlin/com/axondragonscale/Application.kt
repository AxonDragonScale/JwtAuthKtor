package com.axondragonscale

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.axondragonscale.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureSecurity()
        configureMonitoring()
        configureSerialization()
    }.start(wait = true)
}
