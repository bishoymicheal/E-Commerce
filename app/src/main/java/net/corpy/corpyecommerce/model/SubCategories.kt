package net.corpy.corpyecommerce.model

import com.google.gson.annotations.SerializedName


data class SubCategories(
        @SerializedName("id") val id: Int,
        @SerializedName("ar_name") val ar_name: String,
        @SerializedName("en_name") val en_name: String,
        @SerializedName("image") val image: String,
        @SerializedName("parent") val parent: Int,
        @SerializedName("created_at") val created_at: String,
        @SerializedName("updated_at") val updated_at: String,
        var ar_parentName: String = "",
        var en_parentName: String = ""
)