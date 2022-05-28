package com.axondragonscale.repository

import com.axondragonscale.data.model.User
import org.bson.types.ObjectId

/**
 * Created by Ronak Harkhani on 28/05/22
 */
interface UserRepository {
    suspend fun getUserByUsername(username: String): User?
    suspend fun getUserById(id: ObjectId): User?
    suspend fun insertUser(user: User): Boolean
}