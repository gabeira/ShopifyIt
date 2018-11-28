package com.shopifyit.data.retrofit

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RepositoriesNetwork {
    companion object {

        private const val URL_RETROFIT = "https://api.github.com/orgs/shopify/"
        private var gsonConverterFactory: Converter.Factory = GsonConverterFactory.create()

        fun makeRetrofitService(): RepositoriesRetrofitService {
            return Retrofit.Builder()
                .baseUrl(URL_RETROFIT)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build().create(RepositoriesRetrofitService::class.java)
        }
    }
}