package com.huangliner.prioritytodo.data.model.request


import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("UserAccount")
    val account: String,
    @SerializedName("UserPassword")
    val password: String
)