package codingblocks.com.weatherlocate.data.network.response


import codingblocks.com.weatherlocate.data.db.entity.Location
import com.google.gson.annotations.SerializedName

data class FutureWeatherResponse(

	val location: Location? = null,

    @SerializedName("forecast")
	val futureWeatherEntries: ForecastDaysContainer? = null
)
