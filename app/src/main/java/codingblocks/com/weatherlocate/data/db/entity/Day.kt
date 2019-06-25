package codingblocks.com.weatherlocate.data.db.entity

import androidx.room.Embedded
import codingblocks.com.weatherlocate.data.db.entity.Condition

data class Day(
	val avgvisKm: Double? = null,
	val uv: Double? = null,
	val avgtempF: Double? = null,
	val avgtempC: Double? = null,
	val maxtempC: Double? = null,
	val maxtempF: Double? = null,
	val mintempC: Double? = null,
	val avgvisMiles: Double? = null,
	val mintempF: Double? = null,
	val totalprecipIn: Double? = null,
	val avghumidity: Double? = null,
	@Embedded(prefix = "condition_")
	val condition: Condition? = null,
	val maxwindKph: Double? = null,
	val maxwindMph: Double? = null,
	val totalprecipMm: Double? = null
)
