package com.huangliner.prioritytodo.domain.usecase

import com.huangliner.prioritytodo.application.util.Constants
import com.huangliner.prioritytodo.application.util.Either
import com.huangliner.prioritytodo.application.util.Failure
import com.huangliner.prioritytodo.data.model.request.AddAccountRequest
import com.huangliner.prioritytodo.domain.repository.IRepositry
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@ActivityRetainedScoped
class AddAccountUsecase @Inject constructor(
    private val IRepositry: IRepositry
) {
    suspend fun execute(
        account: String,
        password: String,
        confirmPassword:String
    ): Either<Failure, Boolean> = withContext(Dispatchers.IO) {
        if(password != confirmPassword)
            return@withContext Either.Left(Failure.FailMessage("密碼與確認密碼不相等"))


        val addAccountRequest = AddAccountRequest(
            userAccount = account,
            userPassword = password
        )

        try{
            val result = IRepositry.addAccount(addAccountRequest)
            val response = result.body()
            if(response == null){
                throw Exception("請檢查網路狀態")
            }
            Timber.e("申請帳戶usecase ${response.errorMsg}")
            if(response.errorFlag == Constants.BACKEND_ERROR_SUCCESSFUL_ERROR_FLAG){
                return@withContext Either.Right(true)
            }
            return@withContext Either.Left(Failure.FailMessage(response.errorMsg))
        }catch (e:Exception){
            return@withContext Either.Left(Failure.Exception(e.message.toString()))
        }
    }
}