package com.axondragonscale.security.hash

/**
 * Created by Ronak Harkhani on 28/05/22
 */
interface HashService {
    fun generateSaltedHash(value: String, saltLength: Int = 32): SaltedHash
    fun verify(value: String, saltedHash: SaltedHash): Boolean
}