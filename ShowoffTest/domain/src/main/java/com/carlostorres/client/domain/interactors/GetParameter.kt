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
class GetParameter(private val repository: Repository,
                   background: CoroutineContext = Dispatchers.IO,
                   foreground: CoroutineContext = Dispatchers.Main):
    UseCase<Any, GetParameter.Params>(background, foreground){

    override suspend fun run(parameter: Params?): Either<Failure, Any> {
        if (parameter?.value == null)
            return Left(Failure(EmptyParameters()))
        return when (parameter.type) {
            "boolean" -> Right(repository.getParamBoolean(parameter.value))
            "int" -> Right(repository.getParamInt(parameter.value))
            else -> Right(repository.getParamString(parameter.value))
        }
    }

    /**
     * Use case parameters
     */
    data class Params(val value: String, val type: String? = null): Input

}