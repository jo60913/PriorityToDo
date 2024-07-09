package com.huangliner.prioritytodo.domain.usecase

import com.huangliner.prioritytodo.application.util.Constants.Companion.BACKEND_ERROR_SUCCESSFUL_ERROR_FLAG
import com.huangliner.prioritytodo.application.util.Either
import com.huangliner.prioritytodo.application.util.Failure
import com.huangliner.prioritytodo.data.model.request.LoginRequest
import com.huangliner.prioritytodo.domain.repository.IRepositry
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@ActivityRetainedScoped
class LoginUsecase @Inject constructor(
    private val IRepositry: IRepositry
) {
    suspend fun execute(
        account: String,
        password: String) : Either<Failure,Boolean> = withContext(Dispatchers.IO){
        val loginRequest = LoginRequest(
            account = account,
            password = password
        )

        try {
            val loginResult = IRepositry.login(loginRequest)
            val response = loginResult.body()
            if(response == null)
                throw Exception("請檢查網路狀態")

            if(response.errorFlag == BACKEND_ERROR_SUCCESSFUL_ERROR_FLAG){
                return@withContext Either.Right(true)
            }
            Timber.e("登入 回傳${response.errorMsg}")
            return@withContext Either.Left(Failure.FailMessage(response.errorMsg))
        }catch (e:Exception){
            return@withContext Either.Left(Failure.Exception(e.message.toString()))
        }
    }
}