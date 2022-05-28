package com.axondragonscale.security.hash

import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils
import java.security.SecureRandom

/**
 * Created by Ronak Harkhani on 28/05/22
 */
class SHA256HashService: HashService {
    companion object {
        const val SECURE_RANDOM_ALGO = "SHA1PRNG"
    }

    override fun generateSaltedHash(value: String, saltLength: Int): SaltedHash {
        val saltByteArray = SecureRandom.getInstance(SECURE_RANDOM_ALGO).generateSeed(saltLength)
        val salt = Hex.encodeHexString(saltByteArray)
        val hash = DigestUtils.sha256Hex("$salt$value")
        return SaltedHash(hash, salt)
    }

    override fun verify(value: String, saltedHash: SaltedHash): Boolean {
        return DigestUtils.sha256Hex("${saltedHash.salt}$value") == saltedHash.hash
    }
}