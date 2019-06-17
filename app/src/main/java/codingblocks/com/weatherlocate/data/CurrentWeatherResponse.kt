package codingblocks.com.weatherlocate.data

data class CurrentWeatherResponse(
	val current: Current? = null,
	val location: Location? = null
)
