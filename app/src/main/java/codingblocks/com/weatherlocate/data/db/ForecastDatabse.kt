package codingblocks.com.weatherlocate.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import codingblocks.com.weatherlocate.data.db.entity.Current
import codingblocks.com.weatherlocate.data.db.unitlocal.LocationDao


@Database(
    entities = [Current::class],
    version = 1
)

abstract class ForecastDatabase : RoomDatabase() {
    abstract fun currentWeatherDao(): CurrentDao
//    abstract fun futureWeatherDao(): FutureWeatherDao
   abstract fun LocationDao(): LocationDao

    companion object {
        @Volatile private var instance: ForecastDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                ForecastDatabase::class.java, "forecastdb")
                .build()
    }
}