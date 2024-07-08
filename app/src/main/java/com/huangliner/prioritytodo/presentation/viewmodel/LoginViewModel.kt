package com.huangliner.prioritytodo.presentation.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huangliner.prioritytodo.application.util.Either
import com.huangliner.prioritytodo.domain.usecase.LoginUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUsecase: LoginUsecase
) : ViewModel() {
    val loginAccount = ObservableField("")
    val loginPassword = ObservableField("")
    private val _authLogin = MutableSharedFlow<Boolean>()
    val authLogin = _authLogin.asSharedFlow()
    private val _loginErrorMsg = MutableSharedFlow<String>()
    val loginErrorMsg = _loginErrorMsg.asSharedFlow()
    fun login() {
        viewModelScope.launch {
            val loginResult = loginUsecase.execute(loginAccount.get()!!, loginPassword.get()!!)
            when (loginResult) {
                is Either.Left -> {
                    _loginErrorMsg.emit(loginResult.value.data!!)
                    _authLogin.emit(false)
                }
                is Either.Right -> {
                    _authLogin.emit(true)
                }
            }

        }
    }

}