package com.axondragonscale.security.hash

/**
 * Created by Ronak Harkhani on 28/05/22
 */
data class SaltedHash(
    /**
     * The final salted hash value
     */
    val hash: String,

    /**
     * The salt used to create the above hash
     */
    val salt: String
)
