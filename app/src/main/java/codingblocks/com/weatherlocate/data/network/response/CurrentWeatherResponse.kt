package codingblocks.com.weatherlocate.data.network.response

import codingblocks.com.weatherlocate.data.db.entity.Current
import codingblocks.com.weatherlocate.data.db.entity.Location

data class CurrentWeatherResponse(
	val current: Current,
	val location: Location
)
