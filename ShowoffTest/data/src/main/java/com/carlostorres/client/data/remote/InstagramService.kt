package com.carlostorres.client.data.remote

import com.carlostorres.client.data.dto.*
import com.carlostorres.client.domain.models.*
import retrofit2.Call
import retrofit2.http.*

/**
 * Rest API Interface for Retrofit
 */
interface InstagramService {

    @GET("/{idFacebook}/accounts")
    fun getInstagramProfile(@Path("idFacebook") idFacebook: String?, @Query("access_token") accessToken: String): Call<DataResponse<List<Accounts>>>

    @GET("{idInstagram}?fields=posts{full_picture,place,message}")
    fun getPosts(@Path("idInstagram") idInstagram: String?, @Query("access_token") accessToken: String): Call<Posts>
}