package codingblocks.com.weatherlocate.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import codingblocks.com.weatherlocate.data.db.entity.Day

@Entity(tableName = "future_weather", indices = [Index(value = ["date"], unique = true)])

data class FutureWeatherEntry(
	@PrimaryKey(autoGenerate = true)
	val id:Int,
	val date: String,
	@Embedded
	val day: Day
)
