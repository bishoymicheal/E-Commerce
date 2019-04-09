package net.corpy.corpyecommerce.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Product  (
        @SerializedName("id") val id : Int,
        @SerializedName("user_id") val user_id : Int,
        @SerializedName("user_type") val user_type : String,
        @SerializedName("main_dep_id") val main_dep_id : Int,
        @SerializedName("dep_id") val dep_id : Int,
        @SerializedName("ar_title") val ar_title : String,
        @SerializedName("en_title") val en_title : String,
        @SerializedName("en_content") val en_content : String,
        @SerializedName("ar_content") val ar_content : String,
        @SerializedName("stock") val stock : Int,
        @SerializedName("price") val price : Int,
        @SerializedName("size") val size : String,
        @SerializedName("color") val color : String,
        @SerializedName("photo") val photo : String,
        @SerializedName("weight") val weight : String,
        @SerializedName("created_at") val created_at : String,
        @SerializedName("updated_at") val updated_at : String,
        var arParentTitle : String="",
        var enParentTitle : String=""
):Serializable{
    companion object {
        const val PRODUCT_KEY = "product_key"
    }
}