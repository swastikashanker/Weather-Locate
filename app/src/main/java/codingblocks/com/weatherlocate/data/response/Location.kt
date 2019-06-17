package codingblocks.com.weatherlocate.data.response

import com.google.gson.annotations.SerializedName

data class Location(
	val localtime: String? = null,
	val country: String? = null,

	val name: String? = null,
	val lon: Double? = null,
	val region: String? = null,
	val lat: Double? = null,
	@SerializedName("tz_id")
     val tzId: String,
    @SerializedName("localtime_epoch")
    val localtimeEpoch: Long
)