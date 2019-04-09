package net.corpy.corpyecommerce.model

import com.google.gson.annotations.SerializedName

data class Cart (

        @SerializedName("id") val id : Int,
        @SerializedName("user_id") val user_id : Int,
        @SerializedName("product_id") val product_id : Int,
        @SerializedName("vendor_id") val vendor_id : Int,
        @SerializedName("vendor_type") val vendor_type : String,
        @SerializedName("price") val price : Int,
        @SerializedName("weight") val weight : Double,
        @SerializedName("created_at") val created_at : String,
        @SerializedName("updated_at") val updated_at : String
)