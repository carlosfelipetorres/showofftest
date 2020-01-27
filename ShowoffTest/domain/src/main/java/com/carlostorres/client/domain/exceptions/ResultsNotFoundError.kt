package com.carlostorres.client.domain.exceptions


/**
 * App level exception for server error
 */
class ResultsNotFoundError(message: String = ""): Throwable(message)