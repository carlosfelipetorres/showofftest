package com.carlostorres.client.data.dto

data class DataResponse<out T>(val code: Int, val data: T?, val message: String?, val token: String? , val refresh_token: String?)