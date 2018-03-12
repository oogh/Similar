package me.oogh.similar.murmur;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.oogh.similar.R;
import me.oogh.similar.adapter.MurmurViewPagerAdapter;
import me.oogh.similar.message.MessageFragment;

/**
 * Created by oogh on 18-3-4.
 */

public class MurmurFragment extends Fragment implements MurmurContract.View {

    private static final String TAG = MessageFragment.class.getSimpleName();
    @BindView(R.id.vp_murmur)
    ViewPager mViewPager;
    private Unbinder mUnbinder;

    private MurmurContract.Presenter mPresenter;
    private MurmurViewPagerAdapter mAdapter;


    public MurmurFragment() {

    }

    public static MurmurFragment newInstance() {
        MurmurFragment fragment = new MurmurFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new MurmurViewPagerAdapter(getContext(), getChildFragmentManager());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.murmur_fragment, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        mViewPager.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((TabLayout) getActivity().findViewById(R.id.tl_murmur)).setupWithViewPager(mViewPager);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }


    @Override
    public void setPresenter(@NonNull MurmurContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
