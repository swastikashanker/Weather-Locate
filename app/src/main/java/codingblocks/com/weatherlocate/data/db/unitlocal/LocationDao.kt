package codingblocks.com.weatherlocate.data.db.unitlocal

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import codingblocks.com.weatherlocate.data.db.entity.Location
import codingblocks.com.weatherlocate.data.db.entity.WEATHER_LOCATION_ID


@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(Location: Location)

    @Query("select * from weather_location where id = $WEATHER_LOCATION_ID")
    fun getLocation(): LiveData<Location>

    @Query("select * from weather_location where id = $WEATHER_LOCATION_ID")
    fun getLocationNonLive(): Location?
}