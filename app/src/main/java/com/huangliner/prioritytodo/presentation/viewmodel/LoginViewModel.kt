package com.huangliner.prioritytodo.presentation.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huangliner.prioritytodo.application.util.Either
import com.huangliner.prioritytodo.application.util.NetworkResult
import com.huangliner.prioritytodo.domain.usecase.LoginUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUsecase: LoginUsecase
) : ViewModel() {
    val loginAccount = ObservableField("")
    val loginPassword = ObservableField("")
    private val _loginState = MutableLiveData<NetworkResult<String>>()

    val loginState : LiveData<NetworkResult<String>> = _loginState
    fun login() {
        viewModelScope.launch {
            _loginState.postValue(NetworkResult.Loading())
            val loginResult = loginUsecase.execute(loginAccount.get()!!, loginPassword.get()!!)
            when (loginResult) {
                is Either.Left -> {
                    Timber.e("登入失敗")
                    _loginState.postValue(NetworkResult.Error(loginResult.value.data))
                }
                is Either.Right -> {
                    Timber.e("登入成功")
                    _loginState.postValue(NetworkResult.Success(""))
                }
            }

        }
    }

}