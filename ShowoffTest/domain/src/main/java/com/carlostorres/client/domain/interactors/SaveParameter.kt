package com.carlostorres.client.domain.interactors

import arrow.core.Either
import arrow.core.Failure
import arrow.core.Left
import arrow.core.Right
import com.carlostorres.client.domain.exceptions.EmptyParameters
import com.carlostorres.client.domain.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

/**
 * Start session with user and cedula. If the response is successful, save the token
 */
class SaveParameter(private val repository: Repository,
                    background: CoroutineContext = Dispatchers.IO,
                    foreground: CoroutineContext = Dispatchers.Main):
    UseCase<Boolean, SaveParameter.Params>(background, foreground){

    override suspend fun run(parameter: Params?): Either<Failure, Boolean> {
        if (parameter?.key == null)
            return Left(Failure(EmptyParameters()))
        repository.saveParam(parameter.key, parameter.value).fold(
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
    data class Params(val key: String, val value: Any): Input

}