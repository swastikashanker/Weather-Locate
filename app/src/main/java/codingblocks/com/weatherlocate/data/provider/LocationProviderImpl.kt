package codingblocks.com.weatherlocate.data.provider

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager

import androidx.core.content.ContextCompat
import codingblocks.com.weatherlocate.data.db.entity.Location
import codingblocks.com.weatherlocate.internal.LocationPermissionNotGrantedException
import codingblocks.com.weatherlocate.internal.asDeferred
import codingblocks.com.weatherlocate.internal.asDeferredAsync
import com.google.android.gms.location.FusedLocationProviderClient

import kotlinx.coroutines.Deferred

const val USE_DEVICE_LOCATION = "USE_DEVICE_LOCATION"
const val CUSTOM_LOCATION = "CUSTOM_LOCATION"

class LocationProviderImpl(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    context: Context
) : PreferenceProvider(context), LocationProvider {

    private val appContext = context.applicationContext

    override suspend fun hasLocationChanged(lastWeatherLocation: codingblocks.com.weatherlocate.data.db.entity.Location): Boolean {
        val deviceLocationChanged = try {
            hasDeviceLocationChanged(lastWeatherLocation)
        } catch (e: LocationPermissionNotGrantedException) {
            false
        }

        return deviceLocationChanged || hasCustomLocationChanged(lastWeatherLocation)
    }

    override suspend fun getPreferredLocationString(): String {
        if (isUsingDeviceLocation()) {
            try {
                val deviceLocation = getLastDeviceLocationAsync().await()
                    ?: return "${getCustomLocationName()}"
                return "${deviceLocation.lat},${deviceLocation.lon}"
            } catch (e: LocationPermissionNotGrantedException){
                return "${getCustomLocationName()}"
            }
        }
        else
            return "${getCustomLocationName()}"
    }

    private suspend fun hasDeviceLocationChanged(lastWeatherLocation: Location): Boolean {
        if (!isUsingDeviceLocation())
            return false

        val deviceLocation = getLastDeviceLocationAsync().await()
            ?: return false


        val comparisonThreshold = 0.03
        return Math.abs(deviceLocation.lat - lastWeatherLocation.lat) > comparisonThreshold &&
                Math.abs(deviceLocation.lon - lastWeatherLocation.lon) > comparisonThreshold
    }

    private fun hasCustomLocationChanged(lastWeatherLocation: Location): Boolean {
        if (!isUsingDeviceLocation()) {
            val customLocationName = getCustomLocationName()
            return customLocationName != lastWeatherLocation.name
        }
        return false
    }

    private fun isUsingDeviceLocation(): Boolean {
        return preferences.getBoolean(USE_DEVICE_LOCATION, true)
    }

    private fun getCustomLocationName(): String? {
        return preferences.getString(CUSTOM_LOCATION, null)
    }

    @SuppressLint("MissingPermission")
    private fun getLastDeviceLocationAsync(): Deferred<codingblocks.com.weatherlocate.data.db.entity.Location> {
         if (hasLocationPermission())
        return fusedLocationProviderClient.lastLocation.asDeferredAsync()


        else
            throw LocationPermissionNotGrantedException()
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(appContext,
            android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }
}