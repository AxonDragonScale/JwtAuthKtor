package com.axondragonscale.repository

import com.axondragonscale.data.model.User
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

/**
 * Created by Ronak Harkhani on 28/05/22
 */
class MongoUserRepository(
    private val db: CoroutineDatabase
): UserRepository {

    private val users = db.getCollection<User>()

    override suspend fun getUserByUsername(username: String): User? {
        return users.findOne(User::username eq username)
    }

    override suspend fun insertUser(user: User): Boolean {
        return users.insertOne(user).wasAcknowledged()
    }
}