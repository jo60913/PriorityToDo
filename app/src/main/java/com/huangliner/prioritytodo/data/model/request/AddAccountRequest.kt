package com.huangliner.prioritytodo.data.model.request


import com.google.gson.annotations.SerializedName

data class AddAccountRequest(
    @SerializedName("UserAccount")
    val userAccount: String,
    @SerializedName("UserPassword")
    val userPassword: String,
    @SerializedName("UserName")
    val userName : String
)