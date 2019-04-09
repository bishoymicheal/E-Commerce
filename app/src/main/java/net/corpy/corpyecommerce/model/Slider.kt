package net.corpy.corpyecommerce.model

import com.google.gson.annotations.SerializedName


data class Slider (

        @SerializedName("id") val id : Int,
        @SerializedName("en_title") val en_title : String,
        @SerializedName("ar_title") val ar_title : String,
        @SerializedName("en_content") val en_content : String,
        @SerializedName("ar_content") val ar_content : String,
        @SerializedName("image") val image : String,
        @SerializedName("created_at") val created_at : String,
        @SerializedName("updated_at") val updated_at : String
)