package com.example.a963103033239757ba10504dc3857ddc7.main.ui.ship

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a963103033239757ba10504dc3857ddc7.main.data.api.AppService
import com.example.a963103033239757ba10504dc3857ddc7.main.data.db.AppDatabase
import com.example.a963103033239757ba10504dc3857ddc7.main.data.model.ShipModel
import com.example.a963103033239757ba10504dc3857ddc7.main.data.model.StationModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class ShipViewModel : ViewModel() {

    val _isLoading = MutableLiveData(false)
    private lateinit var db: AppDatabase
    var firstSliderValue = 1
    var secondSliderValue = 1 // default initial values
    var thirdSliderValue = 1
    var sum = MutableLiveData(3)

    fun setDb(db: AppDatabase) {
        this.db = db
    }

    fun getData(){
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            val list = db.stationListDao().getAll()
            if (list.isNotEmpty())
                _isLoading.postValue(false)
            else getStationListFromApi()
        }
    }
   private fun getStationListFromApi() {
        //CoroutineScope(Dispatchers.IO).launch {
        _isLoading.postValue(true)
        _root_ide_package_.com.example.a963103033239757ba10504dc3857ddc7.main.data.api.AppService.create().getStations()
            .enqueue(object : retrofit2.Callback<List<StationModel>> {
                override fun onResponse(
                    call: Call<List<StationModel>>,
                    response: Response<List<StationModel>>
                ) {
                    _isLoading.postValue(false)
                    saveStationListToDb(response.body())
                }

                override fun onFailure(call: Call<List<StationModel>>, t: Throwable) {
                    _isLoading.postValue(false)
                }
            })
        // }
    }


    fun saveStationListToDb(list: List<StationModel>?) {
        val scope = CoroutineScope(Dispatchers.Default)
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
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            db.shipDao().nukeTable() // start with refreshed table
            db.shipDao().insert(shipModel)
        }
    }


}