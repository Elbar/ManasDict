package kg.manasdict.android.app.ui.activity;

import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import java.util.Locale;

/**
 * Created by root on 3/31/16.
 */
public class AbstractActivity extends AppCompatActivity {

    private final String LOG_TAG = "DADrawerBuilder";

    protected void setLocale() {
        String localization = PreferenceManager.getDefaultSharedPreferences(this).getString("pref_language", "");

        if (!localization.equals("")) {
            Locale locale = new Locale(localization);
            Configuration configuration = new Configuration();
            configuration.locale = locale;
            Locale.setDefault(locale);

            getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
        }
    }
}
