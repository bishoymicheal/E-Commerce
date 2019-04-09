package net.corpy.corpyecommerce.model

import com.google.gson.annotations.SerializedName


data class Messages(@SerializedName("success") val success: List<String>?,
                    @SerializedName("name") val name: List<String>?,
                    @SerializedName("password") val password: List<String>?,
                    @SerializedName("error") val error: List<String>?,
                    @SerializedName("email") val email: List<String>?,
                    @SerializedName("username") val username: List<String>?,
                    @SerializedName("current_email") val current_email: List<String>?,
                    @SerializedName("current_password") val current_password: List<String>?,
                    @SerializedName("provider") val provider: List<String>?)