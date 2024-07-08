package com.huangliner.prioritytodo.data.model.request


import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("account")
    val account: String,
    @SerializedName("password")
    val password: String
)