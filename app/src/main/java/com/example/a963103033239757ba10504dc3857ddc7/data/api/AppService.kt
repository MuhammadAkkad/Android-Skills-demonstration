package com.example.a963103033239757ba10504dc3857ddc7.data.api

import com.example.a963103033239757ba10504dc3857ddc7.data.model.Station
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * Created by Muhammad AKKAD on 10/5/21.
 */
interface AppService {
    @GET(Constants.STATIONS)
    fun getStations(): Call<List<Station>>

    companion object {

        fun create(): AppService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build()
            return retrofit.create(AppService::class.java)
        }
    }
}