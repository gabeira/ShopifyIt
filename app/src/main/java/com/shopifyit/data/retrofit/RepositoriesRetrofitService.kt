package com.shopifyit.data.retrofit

import com.shopifyit.data.model.Repository
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

/**
 * Created by gabeira@gmail.com on 26/11/18.
 */
interface RepositoriesRetrofitService {

    @GET("repos")
    fun requestRepositories(): Deferred<List<Repository>>
}