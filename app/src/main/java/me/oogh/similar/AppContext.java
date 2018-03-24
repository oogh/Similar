package me.oogh.similar;

import android.app.Application;
import android.content.pm.PackageManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import cn.bmob.newim.BmobIM;
import cn.bmob.v3.Bmob;
import me.oogh.similar.message.SimilarMessageHandler;
import me.oogh.similar.utils.ApplicationInfoUtils;

/**
 * Created by oogh on 18-3-2.
 */

public class AppContext extends Application {
    private static final String TAG = AppContext.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            Bmob.initialize(this, ApplicationInfoUtils.getMetaData(this, "Bmob_APP_KEY"));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (getApplicationInfo().packageName.equals(getMyProcessName())) {
            BmobIM.init(this);
            BmobIM.registerDefaultMessageHandler(new SimilarMessageHandler());
        }

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
