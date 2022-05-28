package com.axondragonscale.data.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

/**
 * Created by Ronak Harkhani on 28/05/22
 */
data class User(
    val username: String,
    val password: String,
    val salt: String,
    @BsonId val id: ObjectId = ObjectId()
)
