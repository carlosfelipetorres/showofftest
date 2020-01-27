package com.carlostorres.client.domain.models

import java.io.Serializable

data class Posts(var posts: Data? = null): Serializable

data class Data(var data: List<Post>? = null): Serializable

data class Post(
    var message: String? = null,
    var full_picture: String? = null,
    var id: String? = null,
    var place: Place? = null,
    var name: String? = null): Serializable