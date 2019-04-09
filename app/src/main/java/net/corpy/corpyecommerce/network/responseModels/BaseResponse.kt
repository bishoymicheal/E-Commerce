import com.google.gson.annotations.SerializedName
import net.corpy.corpyecommerce.model.Data
import net.corpy.corpyecommerce.model.Messages

class BaseResponse {

    data class BaseResponse(@SerializedName("status") val status: Boolean?,
                            @SerializedName("messages") val messages: Messages?,
                            @SerializedName("data") val data: Data?)

}
