package com.carlostorres.client.domain.repository

import arrow.core.Either
import arrow.core.Failure
import com.carlostorres.client.domain.models.*

interface Repository {

    /**
     * Save shared preferences values
     */
    fun saveParam(key: String, value: Any): Either<Failure, Boolean>

    /**
     * Retrieve shared preferences String
     */
    fun getParamString(key: String): String

    /**
     * Retrieve shared preferences Boolean
     */
    fun getParamBoolean(key: String): Boolean

    /**
     * Retrieve shared preferences Integer
     */
    fun getParamInt(key: String): Int

    /**
     * Clean all sharePreferences
     */
    fun cleanPreferences(): Either<Failure, Boolean>

    /**
     * Retrieve the user profile
     */
    fun getInstagramProfile(idFacebook: String?): Either<Failure, Accounts>

    /**
     * Get most recent posts
     */
    fun getPosts(): Either<Failure, List<Post>>
}