package codingblocks.com.weatherlocate.data.repository

import androidx.lifecycle.LiveData
import codingblocks.com.weatherlocate.data.db.entity.Location
import codingblocks.com.weatherlocate.data.db.unitlocal.current.UnitSpecificCurrentWeatherEntry
import codingblocks.com.weatherlocate.data.db.unitlocal.future.UnitSpecificSimpleFutureWeatherEntry
import org.threeten.bp.LocalDate

interface ForecastRepository {

        suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry>
        suspend fun getLocation():LiveData<Location>
        suspend fun getFutureWeatherList(startDate: LocalDate, metric: Boolean): LiveData<out List<UnitSpecificSimpleFutureWeatherEntry>>

    //suspend fun getFutureWeatherByDate(date: LocalDate, metric: Boolean): LiveData<out UnitSpecificDetailFutureWeatherEntry>




}