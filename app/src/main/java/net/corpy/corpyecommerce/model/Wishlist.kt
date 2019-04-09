package net.corpy.corpyecommerce.model

import com.google.gson.annotations.SerializedName

data class Wishlist (
        @SerializedName("id") val id : Int,
        @SerializedName("product_id") val product_id : Int,
        @SerializedName("user_id") val user_id : Int,
        @SerializedName("created_at") val created_at : String,
        @SerializedName("updated_at") val updated_at : String
)