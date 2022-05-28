package com.axondragonscale.security.token

/**
 * Created by Ronak Harkhani on 28/05/22
 */
interface TokenService {
    fun generateToken(config: TokenConfig, vararg claims: TokenClaim): String
}