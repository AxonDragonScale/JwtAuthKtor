package com.axondragonscale.security.token

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

/**
 * Created by Ronak Harkhani on 28/05/22
 */
class JwtTokenService: TokenService {

    override fun generateToken(config: TokenConfig, vararg claims: TokenClaim): String {
        val tokenBuilder = JWT.create()
            .withAudience(config.audience)
            .withIssuer(config.issuer)
            .withExpiresAt(Date(System.currentTimeMillis() + config.expiresIn))

        claims.forEach { claim ->
            tokenBuilder.withClaim(claim.name, claim.value)
        }

        return tokenBuilder.sign(Algorithm.HMAC256(config.secret))
    }
}