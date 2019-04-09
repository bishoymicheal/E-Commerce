package net.corpy.corpyecommerce.network

import net.corpy.corpyecommerce.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RestClient {
    private var apiServices: ApiServices

    val client=OkHttpClient()

    init {
        val retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        apiServices = retrofit.create(ApiServices::class.java)
    }

    fun getApiService() = apiServices

}