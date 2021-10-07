package com.example.a963103033239757ba10504dc3857ddc7.ui.station.stations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a963103033239757ba10504dc3857ddc7.data.api.AppService
import com.example.a963103033239757ba10504dc3857ddc7.data.db.FavStationDao
import com.example.a963103033239757ba10504dc3857ddc7.data.db.FavStationDatabase
import com.example.a963103033239757ba10504dc3857ddc7.data.model.StationModel
import retrofit2.Call
import retrofit2.Response

class StationsViewModel : ViewModel() {

    val _isLoading = MutableLiveData(false)
    private lateinit var db: FavStationDatabase
    val stationList = MutableLiveData<List<StationModel>>()

    private var _text = MutableLiveData<String>().apply {
        value = "This is Stations Fragment"
    }
    val text: LiveData<String> = _text

    fun getStationList() {
        _isLoading.value = true
        AppService.create().getStations().enqueue(object : retrofit2.Callback<List<StationModel>> {
            override fun onResponse(
                call: Call<List<StationModel>>,
                response: Response<List<StationModel>>
            ) {
                _isLoading.value = false
                stationList.value = response.body()
            }

            override fun onFailure(call: Call<List<StationModel>>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }

    fun setDb(db: FavStationDatabase) {
        this.db = db
    }

    fun addToFavDbList(station: StationModel) {
        db.stationDao().insert(station)
    }

    fun isAlreadyFav(station: StationModel): Boolean {
        return db.stationDao().isAlreadyFav(station.name)
    }

    fun deleteFromFavDbList(station: StationModel) {
        db.stationDao().delete(station)
    }

    fun getAllFavs() {
        stationList.value = db.stationDao().getAll()
    }
}