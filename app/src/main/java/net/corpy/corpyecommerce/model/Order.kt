package net.corpy.corpyecommerce.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Order (
        @SerializedName("id") val id : Int,
        @SerializedName("code") val code : String,
        @SerializedName("user_id") val user_id : Int,
        @SerializedName("country_id") val country_id : Int,
        @SerializedName("name") val name : String,
        @SerializedName("address") val address : String,
        @SerializedName("phone") val phone : Int,
        @SerializedName("email") val email : String,
        @SerializedName("price") val price : Int,
        @SerializedName("level") val level : String,
        @SerializedName("seen") val seen : Int,
        @SerializedName("shipping") val shipping : String,
        @SerializedName("created_at") val created_at : String,
        @SerializedName("updated_at") val updated_at : String
):Serializable