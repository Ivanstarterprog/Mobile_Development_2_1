package com.example.mobile_development_2_1
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers



interface WeatherApiService {
    @Headers("X-API-KEY: ")
    @GET("v2.2/films")
    fun getAllFilms() :
}

object RetrofitClient {
    private const val BASE_URL = ""

    val instance: WeatherApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(WeatherApiService::class.java)
    }
}