package com.example.a963103033239757ba10504dc3857ddc7.ui.ship

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.a963103033239757ba10504dc3857ddc7.R
import com.example.a963103033239757ba10504dc3857ddc7.data.api.RetrofitRepository
import com.example.a963103033239757ba10504dc3857ddc7.data.db.AppDatabase
import com.example.a963103033239757ba10504dc3857ddc7.data.model.ShipModel
import com.example.a963103033239757ba10504dc3857ddc7.data.model.StationModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call

class ShipViewModel(database: AppDatabase) : ViewModel() {

    val _isLoading = MutableLiveData(false)
    private var db = database
    var firstSliderValue = 1
    var secondSliderValue = 1 // default initial values
    var thirdSliderValue = 1
    var sum = MutableLiveData(3)
    lateinit var list : List<StationModel>

    fun checkForDataAvailability() {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
             list = db.stationListDao().getAll()
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
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch { db.stationListDao().insertAll(list) }
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
        // TODO add missing properties values here.
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            shipModel.spaceSuitCountUGS = shipModel.capacity * 10000
            shipModel.spaceTimeDurationEUS = shipModel.speed * 20
            shipModel.durabilityTimeDS = shipModel.durability * 10000
            shipModel.damageCapacity = 100
            shipModel.remainingTime = shipModel.durability * 10
            shipModel.currentLocation = list[0].name
            db.shipDao().nukeTable() // start with refreshed table
            db.shipDao().insert(shipModel)
        }
    }


}