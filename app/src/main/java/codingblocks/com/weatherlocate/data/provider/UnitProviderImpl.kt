
package codingblocks.com.weatherlocate.data.provider
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import codingblocks.com.weatherlocate.data.provider.UnitProvider
import codingblocks.com.weatherlocate.internal.Unit


const val UNIT_SYSTEM = "UNIT_SYSTEM"

class UnitProviderImpl(context: Context) :  UnitProvider {

    private val appContext = context.applicationContext
    private val preferences :SharedPreferences
    get() = PreferenceManager.getDefaultSharedPreferences(appContext)
    override fun getUnitSystem(): Unit{
        val selectedName = preferences.getString(UNIT_SYSTEM, Unit.METRIC.name)
        return Unit.valueOf(selectedName!!)
    }
}
