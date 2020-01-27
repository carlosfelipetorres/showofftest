package com.carlostorres.client.domain.exceptions


/**
 * App level exception for server error
 */
class TooManyTrysError(message: String = ""): Throwable(message)