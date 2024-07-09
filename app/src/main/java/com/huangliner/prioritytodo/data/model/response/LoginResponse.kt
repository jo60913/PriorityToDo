package com.huangliner.prioritytodo.data.model.response


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("ErrorFlag")
    val errorFlag: String,
    @SerializedName("ErrorMsg")
    val errorMsg: String
)