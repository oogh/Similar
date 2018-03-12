package me.oogh.similar.murmur;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.oogh.similar.R;

/**
 * Created by oogh on 18-3-4.
 */

public class DailyFragmentSub extends Fragment {

    private Unbinder mUnbinder;


    private Toolbar mToolbar;
    private float mToolbarElevation;

    public DailyFragmentSub() {

    }

    public static DailyFragmentSub newInstance() {
        DailyFragmentSub fragment = new DailyFragmentSub();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.murmur_daily_fragment, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            mToolbar = getActivity().findViewById(R.id.tb_murmur_activity);
//            mToolbarElevation = mToolbar.getElevation();
//            mToolbar.setElevation(0);
//        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            mToolbar.setElevation(mToolbarElevation);
//        }
    }
}
