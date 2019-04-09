package net.corpy.corpyecommerce.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.Nullable
import com.google.gson.annotations.SerializedName

@Entity
data class User(@SerializedName("id") val id: Int?,
                @SerializedName("name") val name: String?,
                @Nullable
                @PrimaryKey()
                @SerializedName("email") val email: String,
                @SerializedName("level") val level: String?,
                @SerializedName("status") val status: Int?,
                @SerializedName("provider") val provider: String?,
                @SerializedName("created_at") val created_at: String?,
                @SerializedName("updated_at") val updated_at: String?,
                @SerializedName("api_token") var api_token: String?)
