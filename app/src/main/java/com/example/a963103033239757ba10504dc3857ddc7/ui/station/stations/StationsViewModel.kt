package com.example.a963103033239757ba10504dc3857ddc7.ui.station.stations

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a963103033239757ba10504dc3857ddc7.data.db.AppDatabase
import com.example.a963103033239757ba10504dc3857ddc7.data.model.*
import com.example.a963103033239757ba10504dc3857ddc7.util.TravelHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StationsViewModel(database: AppDatabase) : ViewModel() {

    private var db = database
    val stationList = MutableLiveData<List<StationModel>>()
    val shipLiveData = MutableLiveData<ShipModel>()
    var gameOverLive = MutableLiveData<Boolean>(false)
    var resStateLive = MutableLiveData<RecourseStatus>()
    lateinit var resState: RecourseStatus


    fun getData() {
        CoroutineScope(Dispatchers.IO).launch {
            shipLiveData.postValue(db.shipDao().getShip())
            stationList.postValue(db.stationListDao().getAll())
        }
    }

    fun isAlreadyFav(station: StationModel): Boolean {
        var s = false
        CoroutineScope(Dispatchers.IO).launch { s = db.stationListDao().isAlreadyFav(station.name) }
        return s
    }

    fun favoriteItem(s: StationModel) {
        CoroutineScope(Dispatchers.IO).launch {
            db.stationListDao().update(
                StationModel(
                    s.name,
                    s.coordinateX,
                    s.coordinateY,
                    s.capacity,
                    s.stock,
                    s.need,
                    s.distance,
                    s.isFav
                )
            )
        }
    }

    fun unFavoriateItem(s: StationModel) {
        CoroutineScope(Dispatchers.IO).launch {
            db.stationListDao().update(
                StationModel(
                    s.name,
                    s.coordinateX,
                    s.coordinateY,
                    s.capacity,
                    s.stock,
                    s.need,
                    s.distance,
                    s.isFav
                )
            )
        }
    }

    fun travel(station: StationModel) {
        // mesafe
        val distance = TravelHelper.distanceCalculator(
            Point(station.coordinateX, station.coordinateY),
            Point(shipLiveData.value!!.x, shipLiveData.value!!.y)
        )

        when {
            shipLiveData.value!!.remainingTime < station.distance -> resStateLive.value =
                RecourseStatus(
                    true,
                    ErrorType.REMAINING_TIME.errMsg
                )

            shipLiveData.value!!.spaceSuitCountUGS == 0 ->
                resStateLive.value =
                    RecourseStatus(
                        true,
                        ErrorType.NO_STOCK_LEFT.errMsg
                    )

            shipLiveData.value!!.spaceTimeDurationEUS < distance -> resStateLive.value =
                RecourseStatus(
                    true,
                    ErrorType.DISTANCE.errMsg
                )

            shipLiveData.value!!.damageCapacity == 0 -> resStateLive.value =
                RecourseStatus(
                    true,
                    ErrorType.DURABILITY.errMsg
                )

            else -> resStateLive.value = (RecourseStatus(false, ErrorType.SUCCESS.errMsg))
        }

        resState = resStateLive.value!!
        if (!resState.status) {
            // region do math then update db
            // Current location
            shipLiveData.value!!.currentLocation = station.name
            // ship kalan zaman
            shipLiveData.value!!.remainingTime -= distance
            // ship DS
            shipLiveData.value!!.durabilityTimeDS -= (distance * 1000)
            // ship kalan UGS
            val givenAmount =
                if (shipLiveData.value!!.spaceSuitCountUGS >= station.need) station.need else shipLiveData.value!!.spaceSuitCountUGS
            station.stock += givenAmount
            station.need -= givenAmount
            shipLiveData.value!!.spaceSuitCountUGS -= givenAmount
            // ship EUS
            shipLiveData.value!!.spaceTimeDurationEUS -= distance
            // ship te kalan malzeme
            shipLiveData.value!!.damageCapacity -= 10
            // ship kordinat guncelleme
            shipLiveData.value!!.x = station.coordinateX
            shipLiveData.value!!.y = station.coordinateY
            station.distance = TravelHelper.distanceCalculator(
                Point(station.coordinateX, station.coordinateY),
                Point(shipLiveData.value!!.x, shipLiveData.value!!.y)
            )
            CoroutineScope(Dispatchers.IO).launch {
                // update db with latest data.
                db.shipDao().update(shipLiveData.value!!)
                db.stationListDao().update(station)
                // trigger UI observers.
                shipLiveData.postValue(shipLiveData.value!!)
                stationList.postValue(db.stationListDao().getAll())
            }
            // endregion
        } else {
            if (resState.errorType != ErrorType.REMAINING_TIME.errMsg)
                gameOverLive.postValue(true)
        }
    }

    fun clearStationList() {
        CoroutineScope(Dispatchers.IO).launch {
            db.stationListDao().nukeTable()
        }
    }
}