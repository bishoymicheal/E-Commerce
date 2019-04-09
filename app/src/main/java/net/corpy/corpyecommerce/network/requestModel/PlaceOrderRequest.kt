package net.corpy.corpyecommerce.network.requestModel

import com.google.gson.annotations.SerializedName

class PlaceOrderRequest{
    @SerializedName("user_id")
    var userId: String? = ""
    @SerializedName("city")
    var city: String? = ""
    @SerializedName("name")
    var name: String? = ""
    @SerializedName("address")
    var address: String? = ""
    @SerializedName("email")
    var email: String? = ""
    @SerializedName("phone")
    var phone: String? = ""
    @SerializedName("lang")
    var lang: String? = "en"

}