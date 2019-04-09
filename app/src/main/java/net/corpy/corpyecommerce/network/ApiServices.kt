package net.corpy.corpyecommerce.network

import BaseResponse
import io.reactivex.Single
import net.corpy.corpyecommerce.network.requestModel.*
import retrofit2.http.*

interface ApiServices {
    @FormUrlEncoded
    @POST("login")
    fun loginUser(@Field("email") email: String,
                  @Field("password") password: String,
                  @Field("lang") language: String): Single<BaseResponse.BaseResponse>

    @POST("sociallogin")
    fun socialLogin(@Body socialRequest: SocialRequest)
            : Single<BaseResponse.BaseResponse>

    @POST("register")
    fun register(@Body registerRequest: RegisterRequest)
            : Single<BaseResponse.BaseResponse>

    @POST("updateuser/{id}")
    fun updateUser(@Path("id") id: String,
                   @Body updateUserRequest: UpdateUserRequest)
            : Single<BaseResponse.BaseResponse>


    @GET("parentcategories")
    fun getParentCategories(): Single<BaseResponse.BaseResponse>

    @GET("bestseller")
    fun getBestSeller(): Single<BaseResponse.BaseResponse>

    @GET("toprated")
    fun getTopRated(): Single<BaseResponse.BaseResponse>

    @GET("mostliked")
    fun getMostLiked(): Single<BaseResponse.BaseResponse>

    @GET("recommendedproduct")
    fun getRecommendedProduct(): Single<BaseResponse.BaseResponse>

    @GET("featuredproduct")
    fun getFeaturedProduct(): Single<BaseResponse.BaseResponse>

    @GET("onsale")
    fun getOnSale(): Single<BaseResponse.BaseResponse>

    @GET("latestproduct")
    fun getLatestProduct(): Single<BaseResponse.BaseResponse>

    @GET("slider")
    fun getSlider(): Single<BaseResponse.BaseResponse>

    @GET("products/{id}")
    fun getProducts(@Path("id") id: String): Single<BaseResponse.BaseResponse>

    @GET("singleproducts/{id}")
    fun getSingleProducts(@Path("id") id: String): Single<BaseResponse.BaseResponse>

    @GET("subcategories/{id}")
    fun getSubCategories(@Path("id") id: String): Single<BaseResponse.BaseResponse>

    @GET("productcategory/{id}")
    fun loadProductCategory(@Path("id") id: String): Single<BaseResponse.BaseResponse>

    @GET("brand")
    fun getBrands(): Single<BaseResponse.BaseResponse>

    @FormUrlEncoded
    @POST("logout")
    fun logout(@Field("id") id: Int): Single<BaseResponse.BaseResponse>

    @FormUrlEncoded
    @POST("search")
    fun search(@Field("nameSearch") nameSearch: String): Single<BaseResponse.BaseResponse>

    @GET("getcart/{id}")
    fun getCart(@Path("id") id: String): Single<BaseResponse.BaseResponse>

    @POST("addtocart")
    fun addItemToCart(@Body addToCartRequest: AddToCartRequest)
            : Single<BaseResponse.BaseResponse>

    @POST("destroyitemcart/{id}")
    fun destroyitemcart(@Path("id") id: String): Single<BaseResponse.BaseResponse>

    ////////
    @GET("getwishlist/{id}")
    fun getWishlist(@Path("id") id: String): Single<BaseResponse.BaseResponse>

    @POST("addtowishlist")
    fun addItemTowishlist(@Body addToCartRequest: AddToCartRequest)
            : Single<BaseResponse.BaseResponse>

    @POST("destroyitemwishlist/{id}")
    fun destroyitemwishlist(@Path("id") id: String): Single<BaseResponse.BaseResponse>


    @POST("placeorder")
    fun placeOrder(@Body placeOrderRequest: PlaceOrderRequest): Single<BaseResponse.BaseResponse>

    @GET("cities")
    fun getCities(): Single<BaseResponse.BaseResponse>

    @GET("myorders/{id}")
    fun getOrders(@Path("id") id: String): Single<BaseResponse.BaseResponse>

    @GET("myordersitems/{id}")
    fun getOrderItem(@Path("id") id: String): Single<BaseResponse.BaseResponse>


    @POST("error")
    fun submitError(@Body errorSubmitRequest: ErrorSubmitRequest): Single<BaseResponse.BaseResponse>

}