package me.oogh.similar.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by oogh on 18-3-2.
 */

public class ActivityUtils {

    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, int containerId) {
        fragmentManager.beginTransaction().add(containerId, fragment).commit();
    }

    public static void navigateTo(Activity from, Class<? extends Activity> to) {
        from.startActivity(new Intent(from, to));
        from.overridePendingTransition(0, 0);
        from.finish();
    }
}
