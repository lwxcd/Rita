package net.logiase.rita.service.pixivc

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Pixivc Api 接口
 */
interface PixivicApiService {

    /**
     * suspend 搜索画作
     */
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