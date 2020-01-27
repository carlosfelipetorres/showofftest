package com.carlostorres.client.domain.exceptions


/**
 * App level exception for server error
 */
class ServerError(message: String = ""): Throwable(message)