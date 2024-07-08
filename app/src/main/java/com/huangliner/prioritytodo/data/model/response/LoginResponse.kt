package com.huangliner.prioritytodo.data.model.response


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("errorFlag")
    val errorFlag: String,
    @SerializedName("errorMsg")
    val errorMsg: String
)