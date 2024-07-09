package com.huangliner.prioritytodo.data.network

import com.huangliner.prioritytodo.data.model.request.AddAccountRequest
import com.huangliner.prioritytodo.data.model.request.LoginRequest
import com.huangliner.prioritytodo.data.model.response.AddAccountResponse
import com.huangliner.prioritytodo.data.model.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface Backend {
    @POST("/login")
    suspend fun login(@Body loginRequest: LoginRequest) : Response<LoginResponse>

    @POST("/insert/user")
    suspend fun addAccount(@Body addAccountRequest: AddAccountRequest): Response<AddAccountResponse>
}