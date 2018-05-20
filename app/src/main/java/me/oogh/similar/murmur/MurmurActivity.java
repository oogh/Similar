package me.oogh.similar.murmur;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.CalendarContract;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import me.oogh.similar.R;
import me.oogh.similar.base.BaseActivity;
import me.oogh.similar.data.entry.Murmur;
import me.oogh.similar.data.entry.User;
import me.oogh.similar.data.source.murmur.MurmurLocalDataSource;
import me.oogh.similar.data.source.murmur.MurmurRemoteDataSource;
import me.oogh.similar.data.source.murmur.MurmurRepository;
import me.oogh.similar.message.MessageActivity;
import me.oogh.similar.my.MyActivity;
import me.oogh.similar.utils.ActivityUtils;
import me.oogh.similar.whisper.WhisperActivity;

/**
 * @author oogh <oogh216@163.com>
 * @date 2018-03-04
 * @description
 */
public class MurmurActivity extends BaseActivity {
    private static final String TAG = MurmurActivity.class.getSimpleName();

    @BindView(R.id.tb_murmur_activity)
    Toolbar mToolbar;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    private MurmurPresenter mPresenter;
    private List<Murmur> mDataList;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            Murmur murmur = (Murmur) message.obj;
            mDataList.add(murmur);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        for (Murmur murmur1 : mDataList) {
                            //调用系统日历的uri的参数
                            String calanderRemiderURL = "";
                            if (Build.VERSION.SDK_INT >= 8) {
                                calanderRemiderURL = "content://com.android.calendar/reminders";
                            } else {
                                calanderRemiderURL = "content://calendar/reminders";
                            }

                            long calID = 3;
                            long startMillis = 0;
                            long endMillis = 0;
                            Calendar beginTime = Calendar.getInstance();
                            long time = BmobDate.getTimeStamp(murmur1.getDate().getDate());
                            Date date = new Date(time);
                            int year = date.getYear();
                            int month = date.getMonth();
                            int day = date.getDay();
                            int hour = date.getHours();
                            int minute = date.getMinutes();
                            beginTime.set(year, month, day, hour, minute);    //注意，月份的下标是从0开始的
                            startMillis = beginTime.getTimeInMillis();    //插入日历时要取毫秒计时
                            Calendar endTime = Calendar.getInstance();
                            endTime.set(year, month, day, hour, minute);
                            endMillis = endTime.getTimeInMillis();

                            ContentValues eValues = new ContentValues();  //插入事件
                            ContentValues rValues = new ContentValues();  //插入提醒，与事件配合起来才有效
                            TimeZone tz = TimeZone.getDefault();//获取默认时区

                            //插入日程
                            eValues.put(CalendarContract.Events.DTSTART, startMillis);
                            eValues.put(CalendarContract.Events.DTEND, endMillis);
                            eValues.put(CalendarContract.Events.TITLE, "斯米聊提醒");
                            eValues.put(CalendarContract.Events.DESCRIPTION, murmur1.getContent());
                            eValues.put(CalendarContract.Events.CALENDAR_ID, calID);
                            eValues.put(CalendarContract.Events.EVENT_LOCATION, "");
                            eValues.put(CalendarContract.Events.EVENT_TIMEZONE, tz.getID());

                            if (ActivityCompat.checkSelfPermission(MurmurActivity.this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }
                            Uri uri = getContentResolver().insert(CalendarContract.Events.CONTENT_URI, eValues);

                            //插完日程之后必须再插入以下代码段才能实现提醒功能
                            String myEventsId = uri.getLastPathSegment(); // 得到当前表的_id
                            rValues.put("event_id", myEventsId);
                            rValues.put("minutes", 0);	//提前10分钟提醒
                            rValues.put("method", 1);	//如果需要有提醒,必须要有这一行
                            getContentResolver().insert(Uri.parse(calanderRemiderURL),rValues);

                        }
                    }
                }
            }).start();
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.murmur_activity);
        ButterKnife.bind(this);
        setupToolbar();
        setupDrawerLayout();

        mDataList = new ArrayList<>();
        // 创建 Murmur Fragment
        MurmurFragment murmurFragment = (MurmurFragment) getSupportFragmentManager().findFragmentById(R.id.fl_murmur_container);
        if (murmurFragment == null) {
            murmurFragment = MurmurFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), murmurFragment, R.id.fl_murmur_container);
        }

        // 创建 Murmur Presenter
        MurmurLocalDataSource local = new MurmurLocalDataSource(this);
        MurmurRemoteDataSource remote = new MurmurRemoteDataSource();
        mPresenter = new MurmurPresenter(
                MurmurRepository.getInstance(local, remote),
                murmurFragment);

        BmobQuery<Murmur> query = new BmobQuery<>();
        query.addWhereEqualTo("user", BmobUser.getCurrentUser(User.class).getObjectId());
        query.addWhereEqualTo("tag","future");
        query.findObjects(new FindListener<Murmur>() {
            @Override
            public void done(List<Murmur> list, BmobException e) {
                if (e == null) {
                    for (Murmur murmur : list) {
                        Message msg = Message.obtain();
                        msg.obj = murmur;
                        mHandler.sendMessage(msg);
                    }
                }
            }
        });


    }

    private void setupToolbar() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_menu);
        mToolbar.setNavigationOnClickListener(view -> mDrawerLayout.openDrawer(GravityCompat.START));
    }

    private void setupDrawerLayout() {
        mNavigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_message:
                    ActivityUtils.navigateTo(MurmurActivity.this, MessageActivity.class);
                    break;
                case R.id.action_murmur:
                    break;
                case R.id.action_whisper:
                    ActivityUtils.navigateTo(MurmurActivity.this, WhisperActivity.class);
                    break;
                case R.id.action_my:
                    ActivityUtils.navigateTo(MurmurActivity.this, MyActivity.class);
                    break;
                default:
                    break;
            }
            item.setChecked(true);
            mDrawerLayout.closeDrawers();
            return true;
        });
    }
}
