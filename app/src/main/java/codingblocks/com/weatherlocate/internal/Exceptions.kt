package codingblocks.com.weatherlocate.internal

import android.os.Parcel
import android.os.Parcelable
import java.io.IOException

class NoConnectivityException() : IOException()
class LocationPermissionNotGrantedException: Exception()
class DateNotFoundException: Exception()