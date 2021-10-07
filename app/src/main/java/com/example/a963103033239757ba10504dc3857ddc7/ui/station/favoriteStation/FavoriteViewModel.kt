package com.example.a963103033239757ba10504dc3857ddc7.ui.station.favoriteStation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a963103033239757ba10504dc3857ddc7.data.db.AppDatabase
import com.example.a963103033239757ba10504dc3857ddc7.data.model.FavStationModel


class FavoriteViewModel : ViewModel() {

    private lateinit var db: AppDatabase
    val favStationList = MutableLiveData<List<FavStationModel>>()
    val isEmptyList = MutableLiveData(false)

    fun setDb(db: AppDatabase) {
        this.db = db
    }

    fun deleteFromFavDbList(station: FavStationModel) {
        db.favStationDao().delete(station)
        favStationList.apply { getAllFavs() }
    }

    fun getAllFavs() {
        favStationList.value = db.favStationDao().getAll()
        isEmptyList.value = favStationList.value!!.isEmpty()
    }
}