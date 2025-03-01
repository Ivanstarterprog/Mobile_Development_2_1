package com.example.mobile_development_2_1
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface WeatherApiService {
    @GET("forecast?appid=${BuildConfig.apiKey}")
    fun GetWeather(@Query("q") cityName: String,
                      @Query("units") units: String) : Call<DataResponce>
}

object RetrofitClient {
    private const val BASE_URL = "http://api.openweathermap.org/data/2.5/"
    val instance: WeatherApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(WeatherApiService::class.java)
    }
}