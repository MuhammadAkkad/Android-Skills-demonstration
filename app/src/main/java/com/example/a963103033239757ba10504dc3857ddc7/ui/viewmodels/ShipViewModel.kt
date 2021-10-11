package com.example.a963103033239757ba10504dc3857ddc7.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.a963103033239757ba10504dc3857ddc7.data.api.RetrofitRepository
import com.example.a963103033239757ba10504dc3857ddc7.data.db.Repository
import com.example.a963103033239757ba10504dc3857ddc7.data.model.ShipModel
import com.example.a963103033239757ba10504dc3857ddc7.data.model.StationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import javax.inject.Inject

@HiltViewModel
class ShipViewModel @Inject constructor(private var db: Repository) : ViewModel() {

    val _isLoading = MutableLiveData(false)


    var firstSliderValue = 1
    var secondSliderValue = 1 // default initial values
    var thirdSliderValue = 1
    var sum = MutableLiveData(3)
    lateinit var list: List<StationModel>

    fun checkForDataAvailability() {
        CoroutineScope(Dispatchers.IO).launch {
            list = db.getAllStations()
            if (list.isNotEmpty())
                _isLoading.postValue(false)
            else getStationListFromApi()
        }
    }


    fun getStationListFromApi(): LiveData<Call<List<StationModel>>> {
        _isLoading.postValue(true)
        return liveData(Dispatchers.IO) {
            val result = RetrofitRepository().getStations()
            emit(result)
            _isLoading.postValue(false)
        }
    }


    fun saveStationListToDb(list: List<StationModel>?) {
        CoroutineScope(Dispatchers.IO).launch { db.insertAllStation(list) }
    }


    fun listenToValue(sliderNumber: Int, value: Int) {
        when (sliderNumber) {
            1 -> firstSliderValue = value
            2 -> secondSliderValue = value
            3 -> thirdSliderValue = value
        }
        sum.value = firstSliderValue + secondSliderValue + thirdSliderValue
    }

    fun saveShipData(shipModel: ShipModel) {
        shipModel.spaceSuitCountUGS = shipModel.capacity * 10000
        shipModel.spaceTimeDurationEUS = shipModel.speed * 20
        shipModel.durabilityTimeDS = shipModel.durability * 10000
        shipModel.damageCapacity = 100
        shipModel.remainingTime = shipModel.durability * 10
        shipModel.currentLocation = list[0].name
        shipModel.x = list[0].coordinateX
        shipModel.y = list[0].coordinateY
        CoroutineScope(Dispatchers.IO).launch {
            db.nukeShip() // start with refreshed table
            db.insertShip(shipModel)
        }
    }


}