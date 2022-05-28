package com.axondragonscale.data.model.response

import kotlinx.serialization.Serializable

/**
 * Created by Ronak Harkhani on 28/05/22
 */
@Serializable
data class AuthResponse(
    val authToken: String
)
