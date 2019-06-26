package codingblocks.com.weatherlocate.data.network.response

import codingblocks.com.weatherlocate.data.db.entity.FutureWeatherEntry
import com.google.gson.annotations.SerializedName

data class ForecastDaysContainer(
	@SerializedName("forecastday")
	val entries: List<FutureWeatherEntry>
)
