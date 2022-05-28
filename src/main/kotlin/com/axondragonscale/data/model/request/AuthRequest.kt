package com.axondragonscale.data.model.request

import kotlinx.serialization.Serializable

/**
 * Created by Ronak Harkhani on 28/05/22
 */
@Serializable
data class AuthRequest(
    val username: String,
    val password: String
)
