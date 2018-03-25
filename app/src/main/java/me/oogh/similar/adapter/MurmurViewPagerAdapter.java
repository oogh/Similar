package me.oogh.similar.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import me.oogh.similar.R;
import me.oogh.similar.murmur.TabDailyFragment;
import me.oogh.similar.murmur.TabFutureFragment;

/**
 * Created by oogh on 18-3-4.
 */

public class MurmurViewPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private static final int ACCOUNT_MURMUR_ACTIONS = 2;

    public MurmurViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = TabDailyFragment.newInstance();
                break;
            case 1:
                fragment = TabFutureFragment.newInstance();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return ACCOUNT_MURMUR_ACTIONS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getStringArray(R.array.murmur_titles)[position];
    }
}
