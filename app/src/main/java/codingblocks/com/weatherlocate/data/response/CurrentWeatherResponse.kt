package codingblocks.com.weatherlocate.data.response

data class CurrentWeatherResponse(
	val current: Current? = null,
	val location: Location? = null
)
