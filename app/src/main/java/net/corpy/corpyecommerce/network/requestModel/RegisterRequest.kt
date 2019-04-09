package net.corpy.corpyecommerce.network.requestModel

import com.google.gson.annotations.SerializedName

class RegisterRequest {
    @SerializedName("name")
    var userName: String? = ""
    @SerializedName("email")
    var email: String? = ""
    @SerializedName("password")
    var password: String? = ""
    @SerializedName("lang")
    var lang: String? = "en"

}