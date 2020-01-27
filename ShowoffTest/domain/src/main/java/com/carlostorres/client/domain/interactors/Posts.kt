package com.carlostorres.client.domain.interactors

import arrow.core.Either
import arrow.core.Failure
import arrow.core.Left
import arrow.core.Right
import com.carlostorres.client.domain.models.Post
import com.carlostorres.client.domain.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class Posts(
    private val repository: Repository,
    background: CoroutineContext = Dispatchers.IO,
    foreground: CoroutineContext = Dispatchers.Main
) :
    UseCase<List<Post>, Posts.Params>(background, foreground) {

    override suspend fun run(parameter: Params?): Either<Failure, List<Post>> {
        repository.getPosts().fold(
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
    data class Params(val message: String? = null) : Input

}