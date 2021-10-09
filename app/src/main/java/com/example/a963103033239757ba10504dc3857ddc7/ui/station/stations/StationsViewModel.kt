package com.example.a963103033239757ba10504dc3857ddc7.ui.station.stations

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a963103033239757ba10504dc3857ddc7.data.db.AppDatabase
import com.example.a963103033239757ba10504dc3857ddc7.data.model.ShipModel
import com.example.a963103033239757ba10504dc3857ddc7.data.model.StationModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StationsViewModel(database: AppDatabase) : ViewModel() {

    private var db = database
    val stationList = MutableLiveData<List<StationModel>>()
    val shipLiveData = MutableLiveData<ShipModel>()

    fun getStationListFromDb() {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            stationList.postValue(db.stationListDao().getAll())
        }
    }

    fun saveStationListToDb(list: List<StationModel>?) {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch { db.stationListDao().insertAll(list) }
    }


    fun isAlreadyFav(station: StationModel): Boolean {
        var s = false
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch { s = db.stationListDao().isAlreadyFav(station.name) }
        return s
    }

    fun favoriteItem(s: StationModel) {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            db.stationListDao().update(
                StationModel(
                    s.name,
                    s.coordinateX,
                    s.coordinateY,
                    s.capacity,
                    s.stock,
                    s.need,
                    s.isFav
                )
            )
        }
        // TODO find a better solution
    }

    fun unFavoriateItem(s: StationModel) {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            db.stationListDao().update(
                StationModel(
                    s.name,
                    s.coordinateX,
                    s.coordinateY,
                    s.capacity,
                    s.stock,
                    s.need,
                    s.isFav
                )
            )
        }
        // TODO find a better solution
    }

    fun getShip() {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch { shipLiveData.postValue(db.shipDao().getShip()) }
    }

    fun saveShipData(shipModel: ShipModel) {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            db.shipDao().insert(shipModel)
        }
    }

    fun travel(shipObject: ShipModel, station: StationModel) {
        viewModelScope.launch {
            val shipModel = db.shipDao().getShip() // make sure all operations are done on db data.
            val stationModel = db.stationListDao().get(station.name)
            // do math then update db
            shipModel.capacity /= 2
            shipModel.currentLocation = station.name
            shipModel.damageCapacity /= 2
            shipModel.durability /= 2  //replace with needed algorithm
            shipModel.speed /= 2
            stationModel.capacity /= 2
            stationModel.need /= 2
            stationModel.stock /= 2

            db.shipDao().update(shipModel) // update db with latest data.
            db.stationListDao().update(stationModel)

            stationList.postValue(db.stationListDao().getAll())  // trigger UI observers.
            shipLiveData.postValue(shipModel)
        }
    }
}