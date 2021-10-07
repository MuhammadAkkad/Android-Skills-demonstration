package com.example.a963103033239757ba10504dc3857ddc7.ui.favoriteStation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a963103033239757ba10504dc3857ddc7.data.db.FavStationDao
import com.example.a963103033239757ba10504dc3857ddc7.data.db.FavStationDatabase
import com.example.a963103033239757ba10504dc3857ddc7.data.model.StationModel


class FavoriteViewModel : ViewModel() {

    private lateinit var db: FavStationDatabase
    private lateinit var favStationDao: FavStationDao
    val stationList = MutableLiveData<List<StationModel>>()
    val isEmptyList = MutableLiveData(false)

    fun setDb(db: FavStationDatabase) {
        this.db = db
        favStationDao = db.stationDao()
    }

    fun deleteFromFavDbList(station: StationModel) {
        db.stationDao().delete(station)
        stationList.apply { getAllFavs() }
    }

    fun getAllFavs() {
        stationList.value = favStationDao.getAll()
        isEmptyList.value = stationList.value!!.isEmpty()
    }
}