package codingblocks.com.weatherlocate.ui.settings

import android.os.Bundle

import androidx.fragment.app.Fragment
import androidx.preference.PreferenceFragmentCompat
import codingblocks.com.weatherlocate.R

class SettingsFragment:PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Settings"
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = null
    }





}