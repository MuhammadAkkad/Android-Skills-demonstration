package com.example.a963103033239757ba10504dc3857ddc7.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a963103033239757ba10504dc3857ddc7.data.db.Repository
import com.example.a963103033239757ba10504dc3857ddc7.data.model.StationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private var db: Repository) : ViewModel() {

    val favStationList = MutableLiveData<List<StationModel>>()
    val isEmptyList = MutableLiveData(false)


    fun deleteFromFavDbList(station: StationModel) {
        CoroutineScope(Dispatchers.IO).launch {
            station.isFav = false
            db.updateStation(station)
            favStationList.apply { getAllFavs() }
        }
    }

    fun getAllFavs() {
        CoroutineScope(Dispatchers.IO).launch {
            favStationList.postValue(db.getFavStation(true))
        }
    }
}