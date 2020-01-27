package com.carlostorres.client.domain.interactors

import arrow.core.Either
import arrow.core.Failure
import arrow.core.Left
import arrow.core.Right
import com.carlostorres.client.domain.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

/**
 * Start session with user and cedula. If the response is successful, save the token
 */
class CleanParameters(private val repository: Repository,
                      background: CoroutineContext = Dispatchers.IO,
                      foreground: CoroutineContext = Dispatchers.Main):
    UseCase<Boolean, CleanParameters.Params>(background, foreground){

    override suspend fun run(parameter: Params?): Either<Failure, Boolean> {
        repository.cleanPreferences().fold(
            {
                return Left(it)
            },
            {
                return Right(it)
            }
        )
    }

    /**
     * Use case parameters
     */
    data class Params(val message: String): Input

}