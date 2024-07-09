package com.huangliner.prioritytodo.data.model.response


import com.google.gson.annotations.SerializedName

data class AddAccountResponse(
    @SerializedName("ErrorFlag")
    val errorFlag: String,
    @SerializedName("ErrorMsg")
    val errorMsg: String
)