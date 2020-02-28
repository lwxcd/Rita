package net.logiase.rita.service.pixivc

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface PixivcApiService {

    @GET("illustrations")
    suspend fun search(
        @Query("keyword") keyword: String,
        @Query("pageSize") pageSize: Int = 30,
        @Query("page") page: Int = 1,
        @Query("searchType") searchType: String? = null,
        @Query("illustType") illustType: String? = null,
        @Query("xRestrict") xRestrict: Int = 0
    ): Response<Illust>

    @GET()
    suspend fun getSuggestion()

}