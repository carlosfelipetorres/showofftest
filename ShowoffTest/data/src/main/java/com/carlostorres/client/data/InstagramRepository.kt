package com.carlostorres.client.data

import android.content.SharedPreferences
import arrow.core.Either
import arrow.core.Failure
import arrow.core.Left
import arrow.core.Right
import com.carlostorres.client.data.remote.InstagramService
import com.carlostorres.client.domain.constants.*
import com.carlostorres.client.domain.exceptions.ServerError
import com.carlostorres.client.domain.models.Accounts
import com.carlostorres.client.domain.models.Post
import com.carlostorres.client.domain.repository.Repository
import org.json.JSONException
import org.json.JSONObject


/**
 * Data repository pattern to interact with the data
 */
class InstagramRepository(
    private val service: InstagramService,
    private val sharedPreferences: SharedPreferences
) : Repository {

    override fun getInstagramProfile(idFacebook: String?): Either<Failure, Accounts> {
        return try {
            val accessToken = getParamString(ACCESS_TOKEN)
            val response = service.getInstagramProfile(idFacebook, accessToken).execute()
            val data = response.body()?.data
            if (response.isSuccessful && data != null && data.isNotEmpty()) {
                return Right(data[0])
            }

            val errorObject = JSONObject(response.errorBody()?.string())
            val message: String? = errorObject.getString("message")
            if (message != null) Left(Failure(ServerError(message))) else Left(Failure(Throwable()))
        } catch (ex: JSONException) {
            Left(Failure(Throwable()))
        } catch (e: Exception) {
            return Left(Failure(ServerError("Error. Try later")))
        }
    }

    override fun getPosts(): Either<Failure, List<Post>> {
        return try {
            val accessToken = getParamString(ACCESS_TOKEN)
            val idInstagram = getParamString(INSTAGRAM_ID)
            val response = service.getPosts(idInstagram, accessToken).execute()
            val data = response.body()?.posts?.data
            if (response.isSuccessful && data != null) {
                return Right(data)
            }

            val errorObject = JSONObject(response.errorBody()?.string())
            val message: String? = errorObject.getString("message")
            if (message != null) Left(Failure(ServerError(message))) else Left(Failure(Throwable()))
        } catch (ex: JSONException) {
            Left(Failure(Throwable()))
        } catch (e: Exception) {
            return Left(Failure(ServerError("Error. Try later")))
        }
    }

    override fun saveParam(key: String, value: Any): Either<Failure, Boolean> {
        val editor = sharedPreferences.edit()
        when (value) {
            is String -> editor.putString(key, value)
            is Boolean -> editor.putBoolean(key, value)
            is Int -> editor.putInt(key, value)
            else -> return Left(Failure(Throwable()))
        }
        editor.apply()
        return Right(true)
    }

    override fun getParamString(key: String): String {
        return sharedPreferences.getString(key, "")!!
    }

    override fun getParamBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    override fun getParamInt(key: String): Int {
        return sharedPreferences.getInt(key, 0)
    }

    override fun cleanPreferences(): Either<Failure, Boolean> {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        return Right(true)
    }

}