package codingblocks.com.weatherlocate.data.db

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import codingblocks.com.weatherlocate.data.db.entity.CURRENT_WEATHER_ID
import codingblocks.com.weatherlocate.data.db.entity.Current
import codingblocks.com.weatherlocate.data.db.unitlocal.ImperialCurrentWeatherEntry
import codingblocks.com.weatherlocate.data.db.unitlocal.MetricCurrentWeatherEntry

interface CurrentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(current: Current)

    @Query(value = "select * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeatherMetric():LiveData<MetricCurrentWeatherEntry>

    @Query(value = "select * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeatherImperial():LiveData<ImperialCurrentWeatherEntry>
}


