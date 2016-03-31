package kg.manasdict.android.app.ui.fragment.preference;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import java.util.Locale;

import kg.manasdict.android.R;

/**
 * Created by root on 3/31/16.
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        String locale = sharedPreferences.getString("pref_language", "");

        if (!locale.equals("")) {
            changeLocale(locale);
            getActivity().recreate();
        }
    }

    protected void changeLocale(String localization) {
        Locale locale = new Locale(localization);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        Locale.setDefault(locale);

        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
    }
}
