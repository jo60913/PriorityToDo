package com.huangliner.prioritytodo.presentation.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huangliner.prioritytodo.application.util.Either
import com.huangliner.prioritytodo.application.util.NetworkResult
import com.huangliner.prioritytodo.domain.usecase.AddAccountUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddAccountViewModel @Inject constructor(
    private val addAccountUsecase: AddAccountUsecase
) : ViewModel() {
    val account = ObservableField("")
    val password = ObservableField("")
    val confirmPassword = ObservableField("")
    val userName = ObservableField("")
    private val _addAccountState = MutableLiveData<NetworkResult<String>>()

    val addAccountState : LiveData<NetworkResult<String>> = _addAccountState

    fun addAccount(){
        if(hasEmptyField())
            return _addAccountState.postValue(NetworkResult.Error("請填寫所有欄位"))
        Timber.e("執行addaccount")
        viewModelScope.launch {
            _addAccountState.postValue(NetworkResult.Loading())
            val result = addAccountUsecase.execute(
                account = account.get()!!,
                password = password.get()!!,
                confirmPassword = confirmPassword.get()!!,
                userName = userName.get()!!
            )

            when(result){
                is Either.Left ->{
                    Timber.e("新增帳號時錯誤 ${result.value.data}")
                    _addAccountState.postValue(NetworkResult.Error(result.value.data))
                }

                is Either.Right ->{
                    _addAccountState.postValue(NetworkResult.Success())
                }
            }
        }
    }

    private fun hasEmptyField() = account.get()!!.isEmpty() || password.get()!!.isEmpty() || confirmPassword.get()!!.isEmpty() || userName.get()!!.isEmpty()
}