package com.axondragonscale.security.token

/**
 * Created by Ronak Harkhani on 28/05/22
 */
data class TokenConfig(
    val issuer: String,
    val audience: String,
    val expiresIn: Long,
    val secret: String
)
