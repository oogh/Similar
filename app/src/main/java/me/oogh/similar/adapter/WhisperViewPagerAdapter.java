package me.oogh.similar.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import me.oogh.similar.whisper.FoundFragment;
import me.oogh.similar.whisper.HotFragment;
import me.oogh.similar.whisper.MomentsFragment;

/**
 * Created by oogh on 18-3-6.
 */

public class WhisperViewPagerAdapter extends FragmentPagerAdapter {
    private static final int ACCOUNT_WHISPER_ACTIONS = 3;

    public WhisperViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = MomentsFragment.newInstance();
                break;
            case 1:
                fragment = FoundFragment.newInstance();
                break;
            case 2:
                fragment = HotFragment.newInstance();
                break;
            default:
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return ACCOUNT_WHISPER_ACTIONS;
    }
}
