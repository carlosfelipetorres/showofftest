package com.carlostorres.client.domain.exceptions


/**
 * App level exception for server error
 */
class UserNotLoggedError(message: String = ""): Throwable(message)