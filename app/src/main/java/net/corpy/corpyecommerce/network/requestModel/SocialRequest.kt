package net.corpy.corpyecommerce.network.requestModel

import com.google.gson.annotations.SerializedName

class SocialRequest {
    @SerializedName("username")
    var name: String? = ""
    @SerializedName("email")
    var email: String? = ""
    @SerializedName("provider")
    var provider: String? = ""
    @SerializedName("lang")
    var lang: String? = "en"

}