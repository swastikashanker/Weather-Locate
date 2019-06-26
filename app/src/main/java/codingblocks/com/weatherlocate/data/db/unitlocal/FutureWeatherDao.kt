package codingblocks.com.weatherlocate.data.db.unitlocal

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import codingblocks.com.weatherlocate.data.db.entity.FutureWeatherEntry
import codingblocks.com.weatherlocate.data.db.unitlocal.future.ImperialSimpleFutureWeatherEntry
import codingblocks.com.weatherlocate.data.db.unitlocal.future.MetricSimpleFutureWeatherEntry
import org.threeten.bp.LocalDate

@Dao
interface FutureWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(futureWeatherEntries:List<FutureWeatherEntry>)


    @Query("select * from future_weather where date(date) >= date(:startDate)")
    fun getSimpleFutureWeatherMetric(startDate:LocalDate): LiveData<List<MetricSimpleFutureWeatherEntry>>

    @Query("select * from future_weather where date(date) >= date(:startDate)")
    fun getSimpleFutureWeatherImperial(startDate:LocalDate): LiveData<List<ImperialSimpleFutureWeatherEntry>>

    @Query("select count(id) from future_weather where date(date) >= date(:startDate)")
    fun countFutureWeather(startDate: LocalDate): Int

    @Query("delete from future_weather where date(date) < date(:firstDateToKeep)")
    fun deleteOldEntries(firstDateToKeep: LocalDate)





}