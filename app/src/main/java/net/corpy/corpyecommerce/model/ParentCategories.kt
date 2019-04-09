package net.corpy.corpyecommerce.model

import com.google.gson.annotations.SerializedName


data class ParentCategories (
        @SerializedName("id") val id : Int,
        @SerializedName("ar_name") val ar_name : String,
        @SerializedName("en_name") val en_name : String,
        @SerializedName("image") val image : String,
        @SerializedName("parent") val parent : Int,
        @SerializedName("created_at") val created_at : String,
        @SerializedName("updated_at") val updated_at : String)