package kg.manasdict.android.app.boot;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import kg.manasdict.android.app.data.PreferencesConst;

/**
 * Created by root on 3/31/16.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        setDefaultLocale();
    }

    protected void setDefaultLocale() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String locale = preferences.getString("pref_language", "");

        if (locale.equals("")) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("pref_language", PreferencesConst.LOCALE_EN);
            editor.apply();
        }
    }
}
