package com.example.a963103033239757ba10504dc3857ddc7.data.di

import android.content.Context
import androidx.room.Room
import com.example.a963103033239757ba10504dc3857ddc7.data.api.Constants
import com.example.a963103033239757ba10504dc3857ddc7.data.db.AppDatabase
import com.example.a963103033239757ba10504dc3857ddc7.data.db.ShipDao
import com.example.a963103033239757ba10504dc3857ddc7.data.db.StationListDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Muhammad AKKAD on 10/11/21.
 */
@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            Constants.DatabaseName
        ).build()
    }

    @Provides
    fun provideShipDao(database: AppDatabase): ShipDao {
        return database.shipDao()
    }

    @Provides
    fun provideStationListDao(database: AppDatabase): StationListDao {
        return database.stationListDao()
    }

}