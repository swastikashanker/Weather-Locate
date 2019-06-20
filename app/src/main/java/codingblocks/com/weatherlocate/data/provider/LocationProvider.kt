package codingblocks.com.weatherlocate.data.provider
import codingblocks.com.weatherlocate.data.db.entity.Location


interface LocationProvider {
    suspend fun hasLocationChanged(lastWeatherLocation: Location): Boolean
    suspend fun getPreferredLocationString(): String
}