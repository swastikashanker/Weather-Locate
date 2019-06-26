package codingblocks.com.weatherlocate.data.db

import android.content.Context
import androidx.room.*
import codingblocks.com.weatherlocate.data.db.entity.Current
import codingblocks.com.weatherlocate.data.db.entity.FutureWeatherEntry
import codingblocks.com.weatherlocate.data.db.entity.Location
import codingblocks.com.weatherlocate.data.db.unitlocal.FutureWeatherDao
import codingblocks.com.weatherlocate.data.db.unitlocal.LocationDao


@Database(
    entities = [Current::class,FutureWeatherEntry::class,Location::class],
    version = 1,
            exportSchema = false
)


@TypeConverters(LocalDateConverter::class)

abstract class ForecastDatabase : RoomDatabase() {
    abstract fun currentWeatherDao(): CurrentDao
   abstract fun futureWeatherDao(): FutureWeatherDao
   abstract fun locationDao(): LocationDao

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