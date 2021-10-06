package com.example.a963103033239757ba10504dc3857ddc7.ui.favoriteStation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a963103033239757ba10504dc3857ddc7.data.db.StationDao
import com.example.a963103033239757ba10504dc3857ddc7.data.db.StationDatabase
import com.example.a963103033239757ba10504dc3857ddc7.data.model.StationModel


class FavoriteViewModel : ViewModel() {

    private lateinit var db: StationDatabase
    private lateinit var stationDao: StationDao
    val stationList = MutableLiveData<List<StationModel>>()

    fun setDb(db: StationDatabase) {
        this.db = db
        stationDao = db.stationDao()
    }

    fun deleteFromFavDbList(station: StationModel) {
        db.stationDao().delete(station)
        stationList.apply { getAllFavs() }
    }

    fun getAllFavs() {
        stationList.value = stationDao.getAll()
    }
}