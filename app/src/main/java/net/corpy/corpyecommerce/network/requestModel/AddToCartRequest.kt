package net.corpy.corpyecommerce.network.requestModel

import com.google.gson.annotations.SerializedName


class AddToCartRequest {
    @SerializedName("product_id")
    var productId: String? = ""
    @SerializedName("user_id")
    var userId: String? = ""
    @SerializedName("lang")
    var lang: String? = "en"
}