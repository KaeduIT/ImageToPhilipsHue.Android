package co.it.kaedu.imagetophilipshue;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import co.it.kaedu.imagetophilipshue.R;

/**
 * Created with IntelliJ IDEA.
 * User: KeaduIT
 * Date: 9/1/13
 * Time: 4:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener  {

    // public static final String KEY_PREF_USER_NAME   = "KEY_PREF_USER_NAME";
    // public static final String KEY_PREF_DEVICE_NAME = "KEY_PREF_DEVICE_NAME";
    // public static final String KEY_PREF_BRIDGE_IP   = "KEY_PREF_BRIDGE_IP";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.settings_preferences);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("KEY_PREF_USER_NAME")) {
            Preference connectionPref = findPreference(key);
            connectionPref.setSummary(sharedPreferences.getString(key, ""));
        }
        if (key.equals("KEY_PREF_DEVICE_NAME")) {
            Preference connectionPref = findPreference(key);
            connectionPref.setSummary(sharedPreferences.getString(key, ""));
        }
        if (key.equals("KEY_PREF_BRIDGE_IP")) {
            Preference connectionPref = findPreference(key);
           connectionPref.setSummary(sharedPreferences.getString(key, ""));
        }

        // Sets refreshDisplay to true so that when the user returns to the main
        // activity, the display refreshes to reflect the new settings.
        // NetworkActivity.refreshDisplay = true;
        // check setting for downloading data, how often is data downloaded
    }
}
