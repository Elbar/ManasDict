package kg.manasdict.android.app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import kg.manasdict.android.R;
import kg.manasdict.android.app.boot.App;
import kg.manasdict.android.app.db.HelperFactory;
import kg.manasdict.android.app.ui.drawer.DrawerActivityDrawerBuilder;
import kg.manasdict.android.app.ui.fragment.drawer.DictionaryFragment;
import kg.manasdict.android.app.ui.fragment.drawer.MainFragment;

/**
 * Created by root on 3/30/16.
 */
public class DrawerActivity extends AbstractActivity implements Drawer.OnDrawerItemClickListener {

    private Toolbar mToolbar;
    private Drawer mDrawer;
    private Fragment[] mDrawerFragments;
    private FragmentManager mFragmentManager;
    private String[] mToolbarTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HelperFactory.setHelper(getApplicationContext());
        if (!((App) getApplicationContext()).isLocaleChanged()) {
            setLocale();
        }
        setContentView(R.layout.activity_drawer);
        initDrawerFragments();
        initActivityElements();

//        try {
//            Seeds.install();
//        } catch (SQLException e) {
//            Log.d(DrawerActivity.class.getName(), "cannot install or load seeds");
//        }
    }

    @Override
    protected void onDestroy() {
        HelperFactory.releaseHelper();
        ((App) getApplicationContext()).setLocaleChanged(false);
        super.onDestroy();
    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        int id = drawerItem.getIdentifier();

        switch (id) {
            case 0:
            case 1:
                mFragmentManager.beginTransaction().replace(R.id.fragmentsLayout, mDrawerFragments[id]).commit();
                mToolbar.setTitle(mToolbarTitles[id]);
                break;
            case 2:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen()) {
            mDrawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    protected void initActivityElements() {
        mFragmentManager = getSupportFragmentManager();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbarTitles = getResources().getStringArray(R.array.toolbarTitles);
        setSupportActionBar(mToolbar);
        mDrawer = DrawerActivityDrawerBuilder.build(this, mToolbar, this);

        mFragmentManager.beginTransaction().replace(R.id.fragmentsLayout, mDrawerFragments[0]).commit();
        mToolbar.setTitle(mToolbarTitles[0]);
    }

    protected void initDrawerFragments() {
        mDrawerFragments = new Fragment[] {
            new MainFragment(),
            new DictionaryFragment()
        };
    }
}
