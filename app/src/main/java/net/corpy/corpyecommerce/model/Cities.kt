package net.corpy.corpyecommerce.model

import com.google.gson.annotations.SerializedName

data class Cities (

        @SerializedName("id") val id : Int,
        @SerializedName("country_name_ar") val country_name_ar : String,
        @SerializedName("country_name_en") val country_name_en : String,
        @SerializedName("mob") val mob : Int,
        @SerializedName("code") val code : String,
        @SerializedName("logo") val logo : String,
        @SerializedName("parent") val parent : Int,
        @SerializedName("shipping") val shipping : Int,
        @SerializedName("created_at") val created_at : String,
        @SerializedName("updated_at") val updated_at : String
)