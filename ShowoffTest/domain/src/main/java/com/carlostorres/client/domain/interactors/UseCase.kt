package com.carlostorres.client.domain.interactors

import arrow.core.Either
import arrow.core.Failure
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


/**
 * Abstract execution unit for different use cases.
 *
 * @param <T> request type
 * @param <I> parameter
 */
abstract class UseCase<T, I: UseCase.Input>(private val background: CoroutineContext,
                                            private val foreground: CoroutineContext) {

    /**
     * Method to execute the use case
     */
    fun execute(parameter: I? = null, block: (Either<Failure, T>) -> Unit) {
        GlobalScope.launch(foreground) {
            val job = async(background) {
                run(parameter)
            }
            block(job.await())
        }
    }

    /**
     * Abstract method to implement the use case
     */
    protected abstract suspend fun run(parameter: I?): Either<Failure, T>

    interface Input

}
