package kg.manasdict.android.app.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import kg.manasdict.android.R;
import kg.manasdict.android.app.ui.fragment.preference.SettingsFragment;

/**
 * Created by root on 3/31/16.
 */
public class SettingsActivity extends AbstractActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLocale();
        setContentView(R.layout.activity_settings);

        initActivityElements();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return true;
    }

    protected void initActivityElements() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        getFragmentManager().beginTransaction()
                            .replace(R.id.settingsFragmentLayout, new SettingsFragment()).commit();
    }
}
