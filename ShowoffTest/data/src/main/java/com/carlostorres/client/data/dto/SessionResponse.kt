package com.carlostorres.client.data.dto

data class SessionResponse(val code: Int, val token: String?, val refresh_token: String?)