package kg.manasdict.android.app.ui.drawer;

import android.app.Activity;
import android.support.v7.widget.Toolbar;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import kg.manasdict.android.R;
import kg.manasdict.android.lib.ui.drawer.DrawerBuilder;

/**
 * Created by root on 1/9/16.
 */
public class DrawerActivityDrawerBuilder {

    public static Drawer build(final Activity activity, Toolbar toolbar, Drawer.OnDrawerItemClickListener listener) {
        IDrawerItem[] drawerItems = new IDrawerItem[] {
            new PrimaryDrawerItem().withName(R.string.drawer_toolbar_mainPage).withIcon(FontAwesome.Icon.faw_home).withIdentifier(0),
            new PrimaryDrawerItem().withName(R.string.drawer_toolbar_dictionary).withIcon(FontAwesome.Icon.faw_book).withIdentifier(1),
            new DividerDrawerItem(),
            new PrimaryDrawerItem().withName(R.string.drawer_toolbar_settings).withIcon(FontAwesome.Icon.faw_cog).withIdentifier(2).withSelectable(false),
        };

        return DrawerBuilder.build(activity, toolbar, R.dimen.drawer_width, drawerItems, listener);
    }
}
