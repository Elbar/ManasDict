package kg.manasdict.android.app.ui.fragment.preference;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import kg.manasdict.android.R;
import kg.manasdict.android.app.boot.App;

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
        App app = (App) getActivity().getApplicationContext();

        if (!locale.equals("")) {
            Toast.makeText(getActivity(), R.string.info_changeLocale, Toast.LENGTH_LONG).show();
            app.setLocaleChanged(true);
        }
    }
}
