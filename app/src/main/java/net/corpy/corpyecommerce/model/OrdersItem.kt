package net.corpy.corpyecommerce.model

import com.google.gson.annotations.SerializedName


data class OrdersItem (
        @SerializedName("id") val id : Int,
        @SerializedName("product_id") val product_id : Int,
        @SerializedName("order_id") val order_id : Int,
        @SerializedName("vendor_id") val vendor_id : Int,
        @SerializedName("vendor_type") val vendor_type : String,
        @SerializedName("item_price") val item_price : Int,
        @SerializedName("weight") val weight : String,
        @SerializedName("created_at") val created_at : String,
        @SerializedName("updated_at") val updated_at : String
)