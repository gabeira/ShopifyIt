package com.shopifyit.data.retrofit

import com.shopifyit.data.model.Repository
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by gabeira@gmail.com on 26/11/18.
 */
interface RepositoriesRetrofitService {

    @GET("repos")
    fun requestRepositories(@Query("page") page: Int,
                            @Query("per_page") itemsPerPage: Int): Deferred<List<Repository>>
}