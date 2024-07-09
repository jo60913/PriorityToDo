package com.huangliner.prioritytodo.data.repository

import com.huangliner.prioritytodo.data.model.request.AddAccountRequest
import com.huangliner.prioritytodo.data.model.request.LoginRequest
import com.huangliner.prioritytodo.data.network.Backend
import com.huangliner.prioritytodo.domain.repository.IRepositry
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val backend: Backend
) : IRepositry {
    override suspend fun login(loginRequest: LoginRequest) = backend.login(loginRequest = loginRequest)
    override suspend fun addAccount(addAccountRequest: AddAccountRequest) = backend.addAccount(addAccountRequest)
}