package com.carlostorres.client.domain.models

import java.io.Serializable

data class Accounts(
    var access_token: String? = null,
    var category: String? = null,
    var name: String? = null,
    var id: String? = null,
    var tasks: List<String>? = null): Serializable
