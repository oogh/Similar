package me.oogh.similar.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

/**
 * Created by oogh on 18-3-16.
 */

public class ApplicationInfoUtils {
    /**
     * 获取 <application> 节点下的 <meta-data> 数据
     *
     * @param context
     * @param key
     * @return
     * @throws PackageManager.NameNotFoundException
     */
    public static String getMetaData(Context context, String key) throws PackageManager.NameNotFoundException {
        ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        Bundle bundle = applicationInfo.metaData;
        return bundle.getString(key);
    }
}
