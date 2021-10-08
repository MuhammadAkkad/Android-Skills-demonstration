package com.example.a963103033239757ba10504dc3857ddc7.ui.station.favoriteStation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a963103033239757ba10504dc3857ddc7.data.db.AppDatabase
import com.example.a963103033239757ba10504dc3857ddc7.data.model.StationModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FavoriteViewModel(database: AppDatabase) : ViewModel() {

    private val db = database
    val favStationList = MutableLiveData<List<StationModel>>()
    val isEmptyList = MutableLiveData(false)


    fun deleteFromFavDbList(station: StationModel) {
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            station.isFav = false
            db.stationListDao().update(station)
            favStationList.apply { getAllFavs() }
        }
    }

    fun getAllFavs() {
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            favStationList.postValue(db.stationListDao().getFav(true))
        }
    }
}