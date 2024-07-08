package com.huangliner.prioritytodo.domain.repository

import com.huangliner.prioritytodo.data.model.request.LoginRequest
import com.huangliner.prioritytodo.data.model.response.LoginResponse
import retrofit2.Response

interface IRepositry {
    suspend fun login(
        loginRequest: LoginRequest
    ): Response<LoginResponse>
}