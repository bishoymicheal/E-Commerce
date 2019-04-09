package net.corpy.corpyecommerce.network.requestModel

import com.google.gson.annotations.SerializedName

class UpdateUserRequest {
    @SerializedName("name")
    var userName: String? = ""
    @SerializedName("email")
    var email: String? = ""
    @SerializedName("current_email")
    var current_email: String? = ""
    @SerializedName("password")
    var password: String? = ""
    @SerializedName("current_password")
    var current_password: String? = ""
    @SerializedName("token")
    var token: String? = ""
    @SerializedName("lang")
    var lang: String? = "en"
}