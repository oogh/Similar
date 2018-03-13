package me.oogh.similar;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import cn.bmob.v3.Bmob;

/**
 * Created by oogh on 18-3-2.
 */

public class AppContext extends Application {
    private static final String TAG = AppContext.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = applicationInfo.metaData;
            String bmob_app_key = bundle.getString("Bmob_APP_KEY");
            Log.i(TAG, "onCreate: bmob_app_key = " + bmob_app_key);
            Bmob.initialize(this, bmob_app_key);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

//        BmobIM.init(this);
//            BmobIM.registerDefaultMessageHandler(new DemoMessageHandler());
    }


    /**
     * 获取当前运行的进程名
     *
     * @return
     */
    public static String getMyProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
