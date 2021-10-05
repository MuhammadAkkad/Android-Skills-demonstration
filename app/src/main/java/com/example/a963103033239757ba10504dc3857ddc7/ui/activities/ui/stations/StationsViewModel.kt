package com.example.a963103033239757ba10504dc3857ddc7.ui.activities.ui.stations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a963103033239757ba10504dc3857ddc7.data.api.AppService
import com.example.a963103033239757ba10504dc3857ddc7.data.model.Station
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Response

class StationsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Stations Fragment"
    }
    val text: LiveData<String> = _text

    val stationList = MutableLiveData<List<Station>>()
    fun getStationList(){
        AppService.create().getStations().enqueue(object : retrofit2.Callback<List<Station>> {
            override fun onResponse(
                call: Call<List<Station>>,
                response: Response<List<Station>>
            ) {
                stationList.value = response.body()
            }

            override fun onFailure(call: Call<List<Station>>, t: Throwable) {

            }

        })
    }
}