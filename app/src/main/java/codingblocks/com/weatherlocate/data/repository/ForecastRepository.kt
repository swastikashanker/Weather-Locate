package codingblocks.com.weatherlocate.data.repository

import androidx.lifecycle.LiveData
import codingblocks.com.weatherlocate.data.db.entity.Location
import codingblocks.com.weatherlocate.data.db.unitlocal.UnitSpecificCurrentWeatherEntry

interface ForecastRepository {

        suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry>
        suspend fun getLocation():LiveData<Location>



    }