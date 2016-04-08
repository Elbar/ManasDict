package kg.manasdict.android.lib.ui.drawer;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

/**
 * Created by root on 1/9/16.
 */
public class DrawerBuilder {

    public static Drawer build(@NonNull Activity activity, @NonNull Toolbar toolbar, int drawerWidthRes,
                                      @NonNull IDrawerItem[] drawerItems, @NonNull Drawer.OnDrawerItemClickListener itemClickListener) {
        return new com.mikepenz.materialdrawer.DrawerBuilder()
                   .withActivity(activity)
                   .withToolbar(toolbar)
                   .withDrawerWidthRes(drawerWidthRes)
                   .withOnDrawerItemClickListener(itemClickListener)
                   .addDrawerItems(drawerItems)
                   .build();
    }
}
