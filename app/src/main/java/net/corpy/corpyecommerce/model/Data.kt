package net.corpy.corpyecommerce.model


import com.google.gson.annotations.SerializedName

data class Data(@SerializedName("user") var user: User?,
                @SerializedName("api_token") val api_token: String?,
                @SerializedName("parent_categories") var ParentCategories: List<ParentCategories> = ArrayList(),
                @SerializedName("sub_categories") var subCategories : List<SubCategories> = ArrayList(),
                @SerializedName("products") var products : List<Product> = ArrayList(),
                @SerializedName("singleProduct") val singleProduct : SingleProduct? = null,
                @SerializedName("department_en_name") val department_en_name : String = "",
                @SerializedName("department_ar_name") val department_ar_name : String= "",
                @SerializedName("relatedProduct") val relatedProduct : List<RelatedProduct> = ArrayList(),
                @SerializedName("slider") val slider : List<Slider> = ArrayList(),
                @SerializedName("gallary") val gallary : ArrayList<String> = ArrayList(),
                @SerializedName("size") val size : ArrayList<String> = ArrayList(),
                @SerializedName("color") val color : ArrayList<String> = ArrayList(),
                @SerializedName("product_categories") val product_categories : ParentCategories? = null,
                @SerializedName("cart") val cart : ArrayList<Cart> = ArrayList(),
                @SerializedName("totalprice") val totalprice : Double? = 0.0,
                @SerializedName("totalweight") val totalweight : Double? = 0.0,
                @SerializedName("count") val cartCount : Int? = 0,
                @SerializedName("wishlist") val wishlist : ArrayList<Wishlist> = ArrayList(),
                @SerializedName("cities") val cities : List<Cities> = ArrayList(),
                @SerializedName("Order") val order : List<Order> = ArrayList(),
                @SerializedName("orders_item") val orders_item : List<OrdersItem> = ArrayList()




)
