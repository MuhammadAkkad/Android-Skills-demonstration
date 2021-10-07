package com.example.a963103033239757ba10504dc3857ddc7.ui.station.stations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a963103033239757ba10504dc3857ddc7.data.api.AppService
import com.example.a963103033239757ba10504dc3857ddc7.data.db.AppDatabase
import com.example.a963103033239757ba10504dc3857ddc7.data.model.FavStationModel
import com.example.a963103033239757ba10504dc3857ddc7.data.model.ShipModel
import com.example.a963103033239757ba10504dc3857ddc7.data.model.StationModel
import retrofit2.Call
import retrofit2.Response

class StationsViewModel : ViewModel() {

    val _isLoading = MutableLiveData(false)
    private lateinit var db: AppDatabase
    val stationList = MutableLiveData<List<StationModel>>()

    /*private var _text = MutableLiveData<String>().apply {
        value = "This is Stations Fragment"
    }
    val text: LiveData<String> = _text*/

    fun getStationListFromApi() {
        _isLoading.value = true
        AppService.create().getStations().enqueue(object : retrofit2.Callback<List<StationModel>> {
            override fun onResponse(
                call: Call<List<StationModel>>,
                response: Response<List<StationModel>>
            ) {
                _isLoading.value = false
                stationList.value = response.body()
                saveStationListToDb(response.body())
            }

            override fun onFailure(call: Call<List<StationModel>>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }

    fun setDb(db: AppDatabase) {
        this.db = db
    }

    fun saveStationListToDb(list: List<StationModel>?) {
        db.stationListDao().insertAll(list)
    }

    fun getStationListFromDb(): List<StationModel> {
        return db.stationListDao().getAll()
    }

    fun addToFavDbList(s: StationModel) {
        db.favStationDao().insert(
            FavStationModel(
                s.name,
                s.coordinateX,
                s.coordinateY,
                s.capacity,
                s.stock,
                s.need,
                s.isFav
            )
        ) // TODO find a better solution
    }

    fun isAlreadyFav(station: StationModel): Boolean {
        return db.favStationDao().isAlreadyFav(station.name)
    }

    fun deleteFromFavDbList(s: StationModel) {
        db.favStationDao().delete(
            FavStationModel(
                s.name,
                s.coordinateX,
                s.coordinateY,
                s.capacity,
                s.stock,
                s.need,
                s.isFav
            )
        ) // TODO find a better solution
    }

    fun getShip(): ShipModel {
        return db.shipDao().getShip()
    }

}