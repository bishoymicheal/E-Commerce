package net.corpy.corpyecommerce.network.requestModel

import com.google.gson.annotations.SerializedName

class ErrorSubmitRequest {
    @SerializedName("message")
    var message: String? = ""
}